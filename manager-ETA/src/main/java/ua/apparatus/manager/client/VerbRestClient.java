package ua.apparatus.manager.client;

import ua.apparatus.manager.entity.Verb;

import java.util.List;
import java.util.Optional;

public interface VerbRestClient {

    List<Verb> findAllVerbs();

    Verb createVerb(Verb verb);

    Optional<Verb> findVerb(int verbId);

    void updateVerb(Verb verb);

    void deleteVerb(int verbId);
}
