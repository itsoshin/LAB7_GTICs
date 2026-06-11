package com.example.lab7.sdn;

import org.apache.pekko.actor.AbstractActor;
import java.util.HashMap;
import java.util.Map;

public class SdnController extends AbstractActor {

    private final Map<String, Integer> counters = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Mensajes.class, this::onEvent).build();
    }

    private void onEvent(Mensajes event) {
        counters.merge(event.switchId(), 1, Integer::sum);

        System.out.println("=== El CONTROLADOR a recivido un mensaje de " + event.switchId() + " -> Total acumulado: " + counters.get(event.switchId()));

        if (event.switchId().equals("switch1")) {
            String rutaSwitch2 = "pekko://SDNSystem@switch2:2551/user/switch";
            System.out.println("=== El CONTROLADOR envia el mensaje a SWITCH2 en la red...");

            getContext().getSystem()
                    .actorSelection(rutaSwitch2)
                    .tell(event, getSelf());
        }

        else if (event.switchId().equals("switch2")) {
            String rutaSwitch1 = "pekko://SDNSystem@switch1:2551/user/switch";
            getContext().getSystem()
                    .actorSelection(rutaSwitch1)
                    .tell(event, getSelf());
        }
    }
}
