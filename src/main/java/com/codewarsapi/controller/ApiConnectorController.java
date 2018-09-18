package com.codewarsapi.controller;

import com.codewarsapi.model.Kata;
import com.codewarsapi.model.Mentor;
import com.codewarsapi.service.ApiService;
import com.codewarsapi.service.KataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
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


    @Secured("ROLE_USER")
    @GetMapping("/")
    public String first(Model model) {
        model.addAttribute("codewars_user");
        return "provide_user";
    }

    @PostMapping(value = "/getAPI")
    public String indexPage(@RequestParam("codewars_user") String codewars_user, Model model) {
        try {
                List<Kata> allKatas = kataService.allKatasResolvedByUser(codewars_user);
                List<Kata> katasForAGivenPeriod = kataService.getKatasForAGivenPeriod(allKatas, LocalDate.now().minusDays(400));
                int cherries = kataService.getTotalCherriesForKatasForAGivenPeriod(katasForAGivenPeriod);
                int points = cherries/15;

                model.addAttribute("latest", katasForAGivenPeriod);
                model.addAttribute("username", codewars_user);
//                model.addAttribute("clan", apiService.getUsersClan(codewars_user));
                model.addAttribute("completedChallenges", apiService.getNrOfCompletedChallengesOf(codewars_user));
                model.addAttribute("allKatas", allKatas);
                model.addAttribute("cherries", cherries);
                model.addAttribute("points", points);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return "index";
    }

    @RequestMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("mentor", new Mentor());
        return "registration";
    }

    @PostMapping("/reg")
    public String greetingSubmit(@ModelAttribute Mentor mentor) {
        System.out.println("New mentor");
        localLOGGER.info("Új mentor");
        return "auth/login";
    }

}
