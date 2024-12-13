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
import ua.apparatus.catalogue.controller.payload.EditVerbPayload;
import ua.apparatus.catalogue.entity.Verb;
import ua.apparatus.catalogue.service.VerbService;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalogue-api/verbs/{verbId:\\d++}")
public class VerbRestController {

    private final VerbService verbService;

    private final MessageSource messageSource;

    @ModelAttribute("verb")
    public Verb getVerb(@PathVariable("verbId") Long verbId) {
        return this.verbService.findVerb(verbId).orElseThrow(() -> new NoSuchElementException("catalog.errors.verb.not_found"));
    }

    @GetMapping
    public Verb findVerb(@ModelAttribute("verb") Verb verb) {
        return verb;
    }

    @PatchMapping
    public ResponseEntity<?> updateVerb(@ModelAttribute("verb") Verb verb,
                                           @Valid @RequestBody EditVerbPayload payload,
                                           BindingResult bindingResult)
            throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            verb.setInfinitive(payload.infinitive());
            verb.setVerb_v2(payload.verb_v2());
            verb.setVerb_v3(payload.verb_v3());
            verb.setIng(payload.ing());
            verb.setTranslate_ua(payload.translate_ua());
            this.verbService.updateVerb(verb);
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteVerb(@PathVariable("verbId") Long id) {
        this.verbService.deleteVerb(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handlerNoSuchElementException(NoSuchElementException exception, Locale locale) {
        return ResponseEntity.badRequest()
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                        this.messageSource.getMessage(exception.getMessage(),
                                new Object[0], exception.getMessage(), locale)));
    }

}
