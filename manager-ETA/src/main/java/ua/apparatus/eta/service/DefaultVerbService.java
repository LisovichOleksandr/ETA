package ua.apparatus.eta.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.apparatus.eta.model.Verb;
import ua.apparatus.eta.repository.VerbRepository;

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
        verbRepository.save(verb);
        return verb;
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
