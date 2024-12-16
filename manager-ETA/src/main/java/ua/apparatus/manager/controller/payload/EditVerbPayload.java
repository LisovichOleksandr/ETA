package ua.apparatus.manager.controller.payload;

public record EditVerbPayload(
        Long id,
        String infinitive,
        String verb_v2,
        String verb_v3,
        String ing,
        String translate_ua) {
}
