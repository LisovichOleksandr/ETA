package ua.apparatus.manager.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ua.apparatus.manager.client.BadRequestException;
import ua.apparatus.manager.client.VerbRestClient;
import ua.apparatus.manager.controller.payload.EditVerbPayload;
import ua.apparatus.manager.entity.Verb;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("home/verbs/{verbId:\\d+}")
@RequiredArgsConstructor
public class VerbController {

    private final VerbRestClient verbRestClient;

    private final MessageSource messageSource;

    @ModelAttribute("verb")
    public Verb verb(@PathVariable int verbId){
        return this.verbRestClient.findVerb(verbId).orElseThrow(() -> new NoSuchElementException("catalog.errors.verb.not_found"));
    }

    @GetMapping()
    public String refactorVerb(){
        return "home/verbs/verb";
    }

    @GetMapping("edit")
    public String getVerbEditPage(){
        return "home/verbs/edit";
    }

    @PostMapping("edit")
    public String updateVerb(@ModelAttribute(value = "verb", binding = false) Verb verb,
                             EditVerbPayload payload,
                             Model model){
        try {
            verb.setId(payload.id());
            verb.setInfinitive(payload.infinitive());
            verb.setVerb_v2(payload.verb_v2());
            verb.setVerb_v3(payload.verb_v3());
            verb.setIng(payload.ing());
            verb.setTranslate_ua(payload.translate_ua());
            this.verbRestClient.updateVerb(verb);
            return "redirect:/home/verbs/verbs_list";
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getMessage());
            return "home/verbs/edit";
        }
    }

    @PostMapping("delete")
    public String deleteVerb(@ModelAttribute("verb") Verb verb){
        this.verbRestClient.deleteVerb(Math.toIntExact(verb.getId()));
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
