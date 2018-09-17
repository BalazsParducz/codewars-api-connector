package com.codewarsapi.controller;

import com.codewarsapi.model.Kata;
import com.codewarsapi.model.Mentor;
import com.codewarsapi.service.ApiService;
import com.codewarsapi.service.KataService;
import com.codewarsapi.service.RequestService;
import com.codewarsapi.service.SessionService;
import org.jboss.logging.Param;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class ApiConnectorController {

    public static final Logger LOGGER = LoggerFactory.getLogger(ApiConnectorController.class);

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
        model.addAttribute("user", new Mentor());
        return "registration";
    }

}
