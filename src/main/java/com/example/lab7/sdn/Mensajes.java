package com.example.lab7.sdn;

public record Mensajes(
        String switchId,
        String eventType,
        long timestamp
) implements MySerializable {

}