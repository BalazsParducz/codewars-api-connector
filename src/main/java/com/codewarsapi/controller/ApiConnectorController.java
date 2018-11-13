package com.codewarsapi.controller;

import com.codewarsapi.Validator.MentorValidator;
import com.codewarsapi.model.Kata;
import com.codewarsapi.model.Mentor;
import com.codewarsapi.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class ApiConnectorController {

    private final Logger localLOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApiService apiService;

    @Autowired
    private KataService kataService;

    // TODO
//    @Autowired
//    private SessionService sessionService;
//
//    @Autowired
//    private RequestService requestService;

    @Autowired
    private MentorService mentorService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MentorValidator mentorValidator;

//    @Autowired
//    private EmailService emailService;

//    @Secured("ROLE_USER")
    @GetMapping("/")
    public String first(Model model) {
        model.addAttribute("codewars_user");
        return "provide_user";
    }

    @GetMapping(value = "/getAPI") // GET
    public String indexPage(@RequestParam("codewars_user") String codewars_user,
                            @RequestParam("from") String from,
                            @RequestParam("to") String to,
                            Model model) {
        System.out.println(from);
        LocalDate ldFrom = LocalDate.parse(from);
        LocalDate ldTo = LocalDate.parse(to);
        try {
                List<Kata> allKatas = kataService.allKatasResolvedByUser(codewars_user);
                List<Kata> katasForAGivenPeriod = kataService.getKatasForAGivenPeriod(allKatas, ldFrom, ldTo);
                int cherries = kataService.getTotalCherriesForKatasForAGivenPeriod(katasForAGivenPeriod);
                int points = cherries/15;

                model.addAttribute("latest", katasForAGivenPeriod);
                model.addAttribute("username", codewars_user);
//                model.addAttribute("clan", apiService.getUsersClan(codewars_user));
                model.addAttribute("completedChallenges", apiService.getNrOfCompletedChallengesOf(codewars_user));
                model.addAttribute("allKatas", allKatas);
                model.addAttribute("cherries", cherries);
                model.addAttribute("points", points);
                model.addAttribute("from", from);
                model.addAttribute("to", to);
//                String emailText = emailService.generateEmailText(codewars_user, points, ldFrom, ldTo, katasForAGivenPeriod);
//            emailService.sendMessage(mentorService.);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return "index";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("mentor", new Mentor());
        return "registration";
    }

    @PostMapping("/registration")
    public String greetingSubmit(@Valid @ModelAttribute Mentor mentor, BindingResult bindingResult) {
        System.out.println("New mentor");
        localLOGGER.info("Új mentor");

//        mentorValidator.validate(mentor, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        mentorService.save(mentor);

        securityService.autologin(mentor.getEmail(), mentor.getMatchingPassword());

        return "auth/login";
    }

    @PostMapping("/email")
    public String emailResults(@ModelAttribute("name") String name, String emailText) {
        System.out.println(name);
        System.out.println(emailText);
//        emailService.sendMessage(name, emailText);
        return "index";
    }

}
