package client;

import gen.event.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.*;

public class Client {
    private final ManagedChannel channel;
    private final EventNotifyGrpc.EventNotifyBlockingStub eventNotifyBlockingStub;

    public Client(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        eventNotifyBlockingStub = EventNotifyGrpc.newBlockingStub(channel);
    }

    public static void main(String[] args) throws InterruptedException {
        Client client = new Client("localhost", 50051);
        client.startSubscription();
    }

    private void startSubscription() throws InterruptedException {
        Set<City> cities = new HashSet<>();
        EventType event;
        Scanner scanner = new Scanner(System.in);
        String line;
        System.out.println("Provide Cities' numbers in one line separated by spaces ");
        for(int i = 0; i < City.values().length - 1; i++){
            System.out.println(i+" ("+City.forNumber(i)+")");
        }
        System.out.print(">> ");
        line = scanner.nextLine();
        for(String cityNumber: line.split(" ")){
            int city = Integer.parseInt(cityNumber);
            if(city < City.values().length - 1){
                cities.add(City.forNumber(city));
            }
        }
        System.out.println("Provide Event Type number: ");
        for(int i = 0; i < EventType.values().length - 1; i++){
            System.out.println(i+" ("+EventType.forNumber(i)+")");
        }
        System.out.print(">> ");
        line = scanner.nextLine();
        event = EventType.forNumber(Integer.parseInt(line.trim()));

        Subscription request = Subscription.newBuilder()
                .addAllCities(cities)
                .setEvent(event)
                .build();
        while(true) {
            subscribe(request);
            Thread.sleep(4000);
            System.out.println("Trying to renew subscription...");
        }
    }
    public void subscribe(Subscription request)
    {
        System.out.println("Chosen:");
        System.out.println(request.getCitiesList());
        System.out.println(request.getEvent());
        System.out.println("-----------");
        Iterator<Notification> notifications;
        try {
            notifications = eventNotifyBlockingStub.getInterestingEvents(request);
            while(notifications.hasNext())
            {
                Notification notification = notifications.next();
                System.out.println("Notification!");
                System.out.println(notification.getEvent().toString()+" in "+notification.getCity().toString()+" ("+notification.getDescription()+")");
                System.out.println("Organizers:");
                List<Notification.Person> people = notification.getOrganizersList();
                for(Notification.Person p: people){
                    System.out.println(p.getName()+" ("+p.getRole()+")");
                }
                System.out.println("-----------");
            }
        } catch (StatusRuntimeException ex) {
            return;
        }
    }
}
