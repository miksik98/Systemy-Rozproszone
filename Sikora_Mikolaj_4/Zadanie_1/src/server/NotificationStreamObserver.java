package server;

import gen.event.City;
import gen.event.EventType;
import gen.event.Notification;
import gen.event.Subscription;
import io.grpc.stub.StreamObserver;

import java.util.List;

public class NotificationStreamObserver implements StreamObserver<Notification> {

    private StreamObserver<Notification> responseObserver;
    private List<City> cities;
    private EventType event;


    public NotificationStreamObserver(Subscription request, StreamObserver<Notification> responseObserver){
        this.cities = request.getCitiesList();
        this.event = request.getEvent();
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(Notification notification) {
        if(cities.contains(notification.getCity()) && event.equals(notification.getEvent())){
            responseObserver.onNext(notification);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("ON ERROR");
    }

    @Override
    public void onCompleted() {
        responseObserver.onCompleted();
        System.out.println("SUBSCRIPTION COMPLETED");
    }
}
