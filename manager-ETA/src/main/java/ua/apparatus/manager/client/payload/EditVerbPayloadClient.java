package ua.apparatus.manager.client.payload;

public record EditVerbPayloadClient(
        String infinitive,
        String verb_v2,
        String verb_v3,
        String ing,
        String translate_ua) {
}
