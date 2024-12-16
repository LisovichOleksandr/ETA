package ua.apparatus.manager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.apparatus.manager.client.BadRequestException;
import ua.apparatus.manager.client.VerbRestClient;
import ua.apparatus.manager.controller.payload.NewVerbPayload;
import ua.apparatus.manager.entity.Verb;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("home/verbs")
@RequiredArgsConstructor
public class VerbsController {
    private final VerbRestClient verbRestClient;

    @GetMapping()
    public String index(){
        return "home";
    }

    @GetMapping("verbs_list")
    public String getHome(Model model, @RequestParam(required = false, value = "name") String uriParam,
                          @RequestParam(required = false, value = "id") String id){
            log.info("URI Param is " + uriParam + " and id is " + id);
            model.addAttribute("verbs", verbRestClient.findAllVerbs());
        return "home/verbs/verbs_list";
    }


    @GetMapping("create")
    public String createNewVerb(){
        return "home/verbs/new_verb";
    }

    @PostMapping("create")
    public String createVerb(NewVerbPayload payload,
                             Model model){
       try {
            Verb verb = new Verb();
            verb.setInfinitive(payload.infinitive());
            verb.setVerb_v2(payload.verb_v2());
            verb.setVerb_v3(payload.verb_v3());
            verb.setIng(payload.ing());
            verb.setTranslate_ua(payload.translate_ua());
            this.verbRestClient.createVerb(verb);
            return "redirect:/home/verbs/verbs_list";
        } catch (BadRequestException exception) {
           model.addAttribute("payload", payload);
           model.addAttribute("errors", exception.getMessage());
           return "home/verbs/edit";
       }
    }
}
