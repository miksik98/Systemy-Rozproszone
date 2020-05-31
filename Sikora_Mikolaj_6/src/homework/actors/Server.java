package homework.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import homework.model.ProductQuery;
import homework.model.ProductRequest;


public class Server extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ProductRequest.class, productRequest -> {
                    log.info("Received query for product: "+productRequest.getName());
                    context().actorOf(Props.create(ShopSearcher.class), "shopSearcher"+productRequest).tell(new ProductQuery(productRequest.getName(), getSender()), getSelf());
                })
                .matchAny(unknown -> {
                    System.err.println("Warning: unrecognized message class \"" + unknown.getClass() + "\" received by Server.\n");
                })
                .build();
    }
}
