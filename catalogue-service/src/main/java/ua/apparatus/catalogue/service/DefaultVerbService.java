package ua.apparatus.catalogue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.apparatus.catalogue.entity.Verb;
import ua.apparatus.catalogue.repository.VerbRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultVerbService implements VerbService{

    private final VerbRepository verbRepository;

    @Override
    public List<Verb> findAllVerbs() {
        return this.verbRepository.findAll();
    }

    @Override
    public Verb createVerb(Verb verb) {
        return this.verbRepository.save(verb);

    }

    @Override
    public Optional<Verb> findVerb(Long id) {
        return verbRepository.findVerbById(id);
    }

    @Override
    public void updateVerb(Verb verb) {
        this.verbRepository.updateVerb(verb);
    }

    @Override
    public void deleteVerb(Long id) {
        this.verbRepository.deleteById(id);
    }
}
