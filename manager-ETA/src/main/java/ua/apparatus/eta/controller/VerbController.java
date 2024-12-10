package ua.apparatus.eta.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ua.apparatus.eta.controller.payload.EditVerbPayload;
import ua.apparatus.eta.model.Verb;
import ua.apparatus.eta.service.VerbService;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("home/verbs/{verbId:\\d+}")
@RequiredArgsConstructor
public class VerbController {

    private final VerbService verbService;

    private final MessageSource messageSource;

    @ModelAttribute("verb")
    public Verb verb(@PathVariable Long verbId){
        return this.verbService.findVerb(verbId).orElseThrow(() -> new NoSuchElementException("catalog.errors.verb.not_found"));
    }

    @GetMapping()
    public String refactorVerb(){
        return "home/verbs/verb";
    }

    @GetMapping("edit")
    public String getVerbEditPage(){
//        System.out.println("Errors: >>" + errors);
        return "home/verbs/edit";
    }

    @PostMapping("edit")
    public String updateVerb(@ModelAttribute(value = "verb", binding = false) Verb verb,
                             @Valid EditVerbPayload payload,
                             BindingResult bindingResult,
                             Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return "home/verbs/edit";
        } else {
            verb.setId(payload.id());
            verb.setInfinitive(payload.infinitive());
            verb.setVerb_v2(payload.verb_v2());
            verb.setVerb_v3(payload.verb_v3());
            verb.setIng(payload.ing());
            verb.setTranslate_ua(payload.translate_ua());
            this.verbService.updateVerb(verb);
            return "redirect:/home/verbs/verbs_list";
        }
    }

    @PostMapping("delete")
    public String deleteVerb(@ModelAttribute("verb") Verb verb){
        this.verbService.deleteVerb(verb.getId());
        return "redirect:/home/verbs/verbs_list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception,
                                               Model model,
                                               HttpServletResponse response,
                                               Locale locale){
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0], exception.getMessage(), locale));
        return "errors/404";
    }

}