package homework.model;

import akka.actor.ActorRef;

public class ProductQuery extends ProductRequest {

    private ActorRef sender;

    public ProductQuery(String name, ActorRef sender) {
        super(name);
        this.sender = sender;
    }

    public ActorRef getSender() {
        return sender;
    }

    public void setSender(ActorRef sender) {
        this.sender = sender;
    }
}
