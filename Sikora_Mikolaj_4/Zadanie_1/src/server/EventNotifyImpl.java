package server;

import gen.event.*;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.*;

public class EventNotifyImpl extends EventNotifyGrpc.EventNotifyImplBase implements Runnable {
    private final static HashSet<StreamObserver<Notification>> observers = new LinkedHashSet<>();

    public static HashSet<StreamObserver<Notification>> getObservers() {
        return observers;
    }

    @Override
    public void getInterestingEvents(Subscription request, StreamObserver<Notification> responseObserver) {
        observers.add(new NotificationStreamObserver(request, responseObserver));
    }

    private Notification generateNotification(){
        Random random = new Random();
        int eventID = Math.abs(random.nextInt())%(EventType.values().length-1);
        int cityID = Math.abs(random.nextInt())%(City.values().length-1);
        List<String> roles = new ArrayList<>(Arrays.asList("host", "sponsor", "organizer"));
        List<String> names = new ArrayList<>(Arrays.asList("Jan Kowalski", "Adam Nowak", "Andrzej Kowalczyk",
                "Lena Wójcik", "Anna Wiśniewska", "Stefan Marek", "Bogna Adamiak"));
        int organizerNumber = Math.abs(random.nextInt())%5 + 1;
        List<Notification.Person> people = new ArrayList<>();
        for(int i = 0; i < organizerNumber; i++){
            int roleID = Math.abs(random.nextInt())%roles.size();
            int nameID = Math.abs(random.nextInt())%names.size();
            people.add(Notification.Person.newBuilder().setRole(roles.get(roleID)).setName(names.get(nameID)).build());
            names.remove(nameID);
        }
        return Notification.newBuilder()
                .setEvent(EventType.forNumber(eventID))
                .setDescription("Event ID: "+Math.abs(random.nextInt()))
                .addAllOrganizers(people)
                .setCity(City.forNumber(cityID))
                .build();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Notification notification = generateNotification();
                System.out.println("New notification for: "+notification.getEvent().toString()+" in "+notification.getCity().toString());
                Thread.sleep(2000);
                List<StreamObserver<Notification>> notActive = new ArrayList<>();
                observers.forEach(o -> {
                    try {
                        o.onNext(notification);
                    }catch(StatusRuntimeException e){
                        notActive.add(o);
                    }
                });
                observers.removeAll(notActive);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
