package ua.apparatus.catalogue.repository;

import ua.apparatus.catalogue.entity.Verb;

import java.util.List;
import java.util.Optional;

public interface VerbRepository {
    List<Verb> findAll();

    Verb save(Verb verb);

    Optional<Verb> findVerbById(Long id);

    void deleteById(Long id);

    void updateVerb(Verb verb);
}
