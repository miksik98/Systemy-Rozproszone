package homework.actors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import homework.model.PriceRequest;
import homework.model.ProductQuery;

import java.util.Random;

public class PriceGenerator extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ProductQuery.class, productQuery -> {
                    Random random = new Random();
                    Thread.sleep(Math.abs(random.nextInt())%400+100);
                    int price = Math.abs(random.nextInt())%10+1;
                    log.info("Generated price ("+price+") for product: "+productQuery.getName());
                    getSender().tell(new PriceRequest(price), getSelf());
                    context().stop(self());
                })
                .matchAny(unknown -> {
                    System.err.println("Warning: unrecognized message class \"" + unknown.getClass() + "\" received by PriceGenerator.\n");
                })
                .build();
    }
}
