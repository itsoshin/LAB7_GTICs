package com.example.lab7;

import com.example.lab7.sdn.SdnController;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.actor.Props;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab7Application {

    public static void main(String[] args) {

        SpringApplication.run(
                Lab7Application.class,
                args
        );

        ActorSystem system =
                ActorSystem.create("SDNSystem");

        system.actorOf(
                Props.create(SdnController.class),
                "controller"
        );

        System.out.println("Controlador iniciado");
    }
}
