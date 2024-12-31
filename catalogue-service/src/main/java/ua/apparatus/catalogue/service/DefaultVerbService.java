package ua.apparatus.catalogue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.apparatus.catalogue.entity.Verb;
import ua.apparatus.catalogue.repository.VerbRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultVerbService implements VerbService{

    private final VerbRepository verbRepository;

    @Override
    public Iterable<Verb> findAllVerbs() {
        return this.verbRepository.findAll();
    }

    @Override
    @Transactional
    public Verb createVerb(Verb verb) {
        return this.verbRepository.save(verb);

    }

    @Override
    public Optional<Verb> findVerb(Long id) {
        return verbRepository.findById(id);
    }

    @Override
    @Transactional
    public void updateVerb(Verb verb) {
        this.verbRepository.findById(verb.getId())
                .ifPresentOrElse(v -> {
                    v.setAllFormsVerbWithoutId(verb);
                }, () -> {
                    throw new NoSuchElementException();
                });
    }

    @Override
    @Transactional
    public void deleteVerb(Long id) {
        this.verbRepository.deleteById(id);
    }
}
