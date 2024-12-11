package ua.apparatus.catalogue.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.apparatus.catalogue.controller.payload.NewVerbPayload;
import ua.apparatus.catalogue.entity.Verb;
import ua.apparatus.catalogue.repository.exception.VerbAlreadyExistException;
import ua.apparatus.catalogue.service.VerbService;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalogue-api/verbs")
public class VerbsRestController {

    private final VerbService verbService;

    private final MessageSource messageSource;

    @GetMapping
    public List<Verb> findVerbs(){
        return this.verbService.findAllVerbs();
    }

    @PostMapping
    public ResponseEntity<?> createVerb(@Valid @RequestBody NewVerbPayload payload,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder)
            throws BindException {
        if (bindingResult.hasErrors()){
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Verb verb = this.verbService.createVerb(new Verb(null, payload.infinitive(), payload.verb_v2(),
                    payload.verb_v3(), payload.ing(), payload.translate_ua()));
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/catalogue-api/verbs/{verbId}")
                            .build(Map.of("verbId", verb.getId())))
                    .body(verb);
        }
    }

    @ExceptionHandler(VerbAlreadyExistException.class)
    public ResponseEntity<ProblemDetail> handleVerbAlreadyExistException(VerbAlreadyExistException exception,
                                                                         Locale locale) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.ALREADY_REPORTED,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)));
    }

}
