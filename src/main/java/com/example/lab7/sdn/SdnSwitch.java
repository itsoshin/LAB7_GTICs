package com.example.lab7.sdn;

import org.apache.pekko.actor.AbstractActor;

public class SdnSwitch extends AbstractActor{
    private final String miNombre;

    public SdnSwitch(String miNombre) {
        this.miNombre = miNombre;
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder().match(Mensajes.class, this::onEventReceived).build();
    }

    private void onEventReceived(Mensajes event) {
        if (event.switchId().equals(miNombre)) {
            System.out.println("[" + miNombre.toUpperCase() + "] Detectado mensaje local: '"
                    + event.eventType() + "'. Reportando al controlador central...");
        }

        else {
            System.out.println("[" + miNombre.toUpperCase() + "] Mensaje recibido del CONTROLADOOOOR");
            System.out.println("[" + miNombre.toUpperCase() + "] El switch vecino '" + event.switchId()
                    + "' reportó el estado '" + event.eventType()
                    + "' en el timestamp: " + event.timestamp());
        }
    }
}
