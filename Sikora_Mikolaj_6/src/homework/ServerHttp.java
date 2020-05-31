package homework;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.server.*;
import akka.pattern.Patterns;
import akka.stream.Materializer;
import homework.model.PriceResponse;
import homework.model.ProductQuery;
import org.jsoup.Jsoup;
import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static akka.http.javadsl.server.PathMatchers.segment;
import static java.time.temporal.ChronoUnit.SECONDS;

public class ServerHttp extends AllDirectives {

    private ActorRef server;
    private ActorSystem system;
    private Materializer materializer;
    private Duration timeout = Duration.of(1, SECONDS);

    public ServerHttp(ActorRef server, ActorSystem system, Materializer materializer) {
        this.server = server;
        this.system = system;
        this.materializer = materializer;
    }

    public Route route() {
        return concat(
                path(segment("price").slash(segment()), product ->
                        get(() -> {
                            CompletionStage<Object> reply = Patterns.askWithReplyTo(
                                    this.server,
                                    actorRef -> new ProductQuery(product, actorRef),
                                    Duration.ofSeconds(1));

                            return onSuccess(reply, (resp) -> {
                                PriceResponse response = (PriceResponse) resp;
                                return complete(response.toString());
                            });
                        })
                ),
                path(segment("review").slash(segment()), name ->
                        get(() -> {
                            final CompletionStage<Object> query = Http.get(system)
                                    .singleRequest(HttpRequest.create("https://www.opineo.pl/?szukaj=" + name + "&s=2"))
                                    .thenCompose(response -> response.entity().toStrict(timeout.get(SECONDS), materializer))
                                    .thenApply(entity -> Jsoup.parse(entity.getData().utf8String())
                                            .body()
                                            .getElementById("page")
                                            .getElementById("content")
                                            .getElementById("screen")
                                            .getElementsByClass("pls")
                                            .get(0)
                                            .getElementsByClass("shl_i pl_i")
                                            .get(0)
                                            .getElementsByClass("pl_attr")
                                            .get(0)
                                            .getElementsByTag("li")
                                            .eachText()
                                            .stream()
                                            .filter(s -> !s.equals("..."))
                                            .collect(Collectors.joining("\n"))
                                    );
                            return onSuccess(query, result -> complete(result.toString()));
                        })
                )
        );
    }
}