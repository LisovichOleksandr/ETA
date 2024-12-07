package ua.apparatus.eta.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EditVerbPayload(
        Long id,

        @NotNull
        @Size( min = 2, max = 50)
        String infinitive,

        @NotNull
        @Size( min = 2, max = 50)
        String verb_v2,

        @NotNull
        @Size( min = 2, max = 50)
        String verb_v3,

        @NotNull
        @Size( min = 2, max = 50)
        String ing,

        @NotNull
        @Size( min = 2, max = 50)
        String translate_ua) {
}
