package ua.apparatus.manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Verb {
    private Long id;
    private String infinitive;
    private String verb_v2;
    private String verb_v3;
    private String ing;
    private String translate_ua;
}
