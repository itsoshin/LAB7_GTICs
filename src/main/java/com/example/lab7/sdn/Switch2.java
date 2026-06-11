package com.example.lab7.sdn;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.actor.Props;

public class Switch2 {

    public static void main(String[] args) {

        Config config = ConfigFactory.load("application-switch.conf");

        ActorSystem system = ActorSystem.create("SDNSystem", config);

        system.actorOf(Props.create(Switch1.class, "SW1"), "switch1");

        System.out.println("switch iniciado");
    }
}