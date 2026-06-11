package com.example.lab7.sdn;

import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.ActorSelection;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class Switch1 extends AbstractActor {

    private final String switchId;

    public Switch1(String switchId) {
        this.switchId = switchId;
    }

    @Override
    public void preStart() {

        getContext().system().scheduler().scheduleWithFixedDelay(
                Duration.Zero(),
                Duration.create(3, TimeUnit.SECONDS),
                getSelf(),
                "SEND",
                getContext().system().dispatcher(),
                getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("SEND", msg -> {

                    ActorSelection controller = getContext().actorSelection("pekko://SDNSystem@controller:2551/user/controller");

                    controller.tell(new Mensajes(switchId, "LINK_STATUS", System.currentTimeMillis()),
                            getSelf());
                }).build();
    }
}