package homework.actors;

import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import homework.model.PriceRequest;
import homework.model.PriceResponse;
import homework.model.ProductQuery;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static akka.actor.SupervisorStrategy.resume;
import static akka.pattern.Patterns.ask;
import static java.time.temporal.ChronoUnit.MILLIS;

public class ShopSearcher extends AbstractActor {
    private final Duration timeout = Duration.of(300, MILLIS);
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public SupervisorStrategy supervisorStrategy()
    {
        return new OneForOneStrategy(
                10,
                Duration.ofSeconds(30),
                DeciderBuilder.matchAny(any -> resume())
                        .build()
        );
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ProductQuery.class, productQuery -> {
                    ProductQuery newProductQuery = new ProductQuery(productQuery.getName(), getSender());
                    CompletableFuture<Object> dbHandler = ask(context().actorOf(Props.create(DatabaseHandler.class), "dbHandler"+productQuery.getName()), newProductQuery, timeout).toCompletableFuture();
                    CompletableFuture<Object> shop1 = ask(context().actorOf(Props.create(PriceGenerator.class), "shop1"+productQuery.getName()), newProductQuery, timeout).toCompletableFuture();
                    CompletableFuture<Object> shop2 = ask(context().actorOf(Props.create(PriceGenerator.class), "shop2"+productQuery.getName()), newProductQuery, timeout).toCompletableFuture();
                    AtomicInteger finalPrice = new AtomicInteger(0);
                    AtomicInteger occurrences = new AtomicInteger(-1);

                    Stream.of(shop1, shop2).forEach(future -> future.whenComplete((res, err) -> {
                        if (err == null) {
                            PriceRequest result = (PriceRequest) res;
                            if (finalPrice.get() > 0) {
                                finalPrice.set(Integer.min(result.getValue(), finalPrice.get()));
                            } else {
                                finalPrice.set(result.getValue());
                            }
                        }
                    }));

                    dbHandler.whenComplete((res, err) -> {
                        if(err == null){
                            occurrences.set((int) res);
                        }
                    });

                    CompletableFuture.allOf(shop1, shop2).whenComplete((res, err) -> {
                        if(finalPrice.get() == 0) productQuery.getSender().tell(new PriceResponse(finalPrice.get(), PriceResponse.ResponseType.NO_PRICES, occurrences.get(), productQuery.getName()), getSelf());
                        else productQuery.getSender().tell(new PriceResponse(finalPrice.get(), PriceResponse.ResponseType.OK, occurrences.get(), productQuery.getName()), getSelf());
                    });
                    context().stop(self());
                })
                .matchAny(unknown ->
                        System.err.println("Warning: unrecognized message class \"" + unknown.getClass() + "\" received by ShopSearcher.\n"))
                .build();
    }
}
