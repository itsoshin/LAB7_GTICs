package com.example.lab7.sdn;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.actor.Props;
import java.time.Duration;

public class HomeController {
    public static void main(String[] args) {

        Config config = ConfigFactory.load();
        ActorSystem system = ActorSystem.create("SDNSystem", config);

        String nodeName = system.settings().config().getString("pekko.remote.artery.canonical.hostname");

        if (nodeName.equals("controller")) {

            system.actorOf(Props.create(SdnController.class), "controller");
            System.out.println("EJECUTANDO HOMECONTROLLER");

        } else if (nodeName.equals("switch1") || nodeName.equals("switch2")) {
            System.out.println("EJECUTANDO HOMECONTROLLER - MODO " + nodeName.toUpperCase());
            ActorRef switchActor = system.actorOf(Props.create(SdnSwitch.class, nodeName), "switch");
            System.out.println("EJECUTANDO HOMECONTROLLER - MODO " + nodeName.toUpperCase());

            // Dentro de tu HomeController:
            if (nodeName.equals("switch1")) {
                System.out.println("SWITCH1 Iniciando envíos periódico cada 3 segundos...");
                system.scheduler().scheduleWithFixedDelay(Duration.ofSeconds(5), Duration.ofSeconds(3), () -> {
                            Mensajes reporte = new Mensajes("switch1", "PERIODIC_HEARTBEAT",
                                    System.currentTimeMillis());

                            switchActor.tell(reporte, ActorRef.noSender());

                            system.actorSelection("pekko://SDNSystem@controller:2551/user/controller")
                                    .tell(reporte, ActorRef.noSender());
                        },
                        system.dispatcher()
                );
            }
        }
    }
}
