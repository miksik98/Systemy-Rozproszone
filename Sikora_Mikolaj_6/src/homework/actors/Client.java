package homework.actors;

import akka.actor.*;
import homework.model.PriceResponse;
import homework.model.ProductRequest;
import homework.model.ProductQuery;

public class Client extends AbstractActor {
    private ActorRef server;

    public Client(ActorRef server){
        this.server = server;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ProductRequest.class, productRequest -> server.tell(new ProductQuery(productRequest.getName(), self()), getSelf()))
                .match(PriceResponse.class, System.out::println)
                .matchAny(unknown -> {
                    System.err.println("Warning: unrecognized message class \"" + unknown.getClass() + "\" received by Client.\n");
                })
                .build();
    }

    public static Props props(ActorRef server) {
        return Props.create(Client.class, server);
    }
}
