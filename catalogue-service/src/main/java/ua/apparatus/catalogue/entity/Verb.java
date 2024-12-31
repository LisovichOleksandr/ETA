package ua.apparatus.catalogue.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "words", name = "t_verbs")
public class Verb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String infinitive;
    private String verb_v2;
    private String verb_v3;
    private String ing;
    private String translate_ua;

    public void setAllFormsVerbWithoutId(Verb verb){
        this.infinitive = verb.getInfinitive();
        this.verb_v2 = verb.getVerb_v2();
        this.verb_v3 = verb.getVerb_v3();
        this.ing = verb.getIng();
        this.translate_ua = verb.getTranslate_ua();
    }
}
