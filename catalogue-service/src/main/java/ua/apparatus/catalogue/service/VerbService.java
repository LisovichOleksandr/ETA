package ua.apparatus.catalogue.service;

import ua.apparatus.catalogue.entity.Verb;

import java.util.List;
import java.util.Optional;

public interface VerbService {
    Iterable<Verb> findAllVerbs();

    Verb createVerb(Verb verb);

    Optional<Verb> findVerb(Long id);

    void updateVerb(Verb verb);

    void deleteVerb(Long id);
}
