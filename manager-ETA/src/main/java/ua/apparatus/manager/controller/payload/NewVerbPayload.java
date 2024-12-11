package ua.apparatus.manager.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewVerbPayload(
        Long id,

        @NotNull(message = "{catalogue.verbs.create.errors.infinitive_is_null}")
        @Size( min = 2, max = 50, message = "{catalogue.verbs.create.errors.infinitive_is_invalid}")
        String infinitive,

        @NotNull(message = "{catalogue.verbs.create.errors.verb_v2_is_null}")
        @Size( min = 2, max = 50, message = "{catalogue.verbs.create.errors.verb_v2_is_invalid}")
        String verb_v2,

        @NotNull(message = "{catalogue.verbs.create.errors.verb_v3_is_null}")
        @Size( min = 2, max = 50, message = "{catalogue.verbs.create.errors.verb_v3_is_invalid}")
        String verb_v3,

        @NotNull(message = "{catalogue.verbs.create.errors.ing_is_null}")
        @Size( min = 2, max = 50, message = "{catalogue.verbs.create.errors.ing_is_invalid}")
        String ing,

        @NotNull(message = "{catalogue.verbs.create.errors.translate_ua_is_null}")
        @Size( min = 2, max = 50, message = "{catalogue.verbs.create.errors.translate_ua_is_invalid}")
        String translate_ua) {
}