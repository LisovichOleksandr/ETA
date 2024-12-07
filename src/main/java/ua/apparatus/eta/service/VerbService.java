package ua.apparatus.eta.service;

import ua.apparatus.eta.model.Verb;

import java.util.List;
import java.util.Optional;

public interface VerbService {
    List<Verb> findAllVerbs();

    Verb createVerb(Verb verb);

    Optional<Verb> findVerb(Long id);

    void updateVerb(Verb verb);

    void deleteVerb(Long id);
}
