package com.codewarsapi.controller;

import com.codewarsapi.service.ApiService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ApiConnectorController {

    private ApiService apiService = new ApiService();

    @GetMapping("/")
    public String first(Model model) {
        model.addAttribute("codewars_user");
        return "provide_user";
    }

    @PostMapping(value = "/getAPI")
    public String indexPage(@RequestParam("codewars_user") String codewars_user, Model model) {
        try {

                Date timeOfCompletion = apiService.getTimeOfCompletion(codewars_user);

                List allKatas = apiService.getArrayOfKatas(codewars_user).toList();

                model.addAttribute("username", apiService.getUserName(codewars_user));
                model.addAttribute("clan", apiService.getUsersClan(codewars_user));
                model.addAttribute("completedChallenges", apiService.getNrOfCompletedChallengesOf(codewars_user));

                model.addAttribute("nameOfKata", apiService.getNameOfKata(codewars_user));
                model.addAttribute("timeOfCompletion", timeOfCompletion);
                model.addAttribute("allKatas", allKatas);
        } catch (IOException ioe) {
            model.addAttribute("error", "Unable to connect to Codewars' API.");        // log error
        } catch (ParseException pe) {
            // TODO log new error
            model.addAttribute("error", "Unable to parse date.");        // log error
        } catch (IllegalArgumentException iae) {
            model.addAttribute("errormsg", "Please, provide a Codewars username");
            return "provide_user";
        }

        return "index";
    }

//    private JSONArray getArrayOfKatas(String codewars_user) throws IOException {
//        JSONObject jsonFromDetails = getJSON(apiService.apiOfCompletedChallengesFor(codewars_user));
//        return jsonFromDetails.getJSONArray("data");
//    }


//    private JSONObject getJSON(String urlString) throws IOException {
//        URL url = new URL(urlString);                                           // 1) URL megadása
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();       // 2) kapcsolódás
//        con.setRequestMethod("GET");                                        // 3) Request method megadás
//        BufferedReader in = new BufferedReader(                         // 4) Tartalom beolvasása
//                new InputStreamReader(con.getInputStream()));
//        String inputLine;                                               // 5) Segéd változó
//        StringBuffer content = new StringBuffer();                      // 6) Tartalom változó létrehozása
//        while ((inputLine = in.readLine()) != null) {                   // 7) A Tartalom változó "feltöltése" while-lal
//            content.append(inputLine);
//        }
//        in.close();                                                     // 8)  a BufferedReader bezárása - szükséges
//        String returnedFromCodewarsAPI = content.toString();            // 9) Segédváltozó: a vissatérő String egésze
//        return new JSONObject(returnedFromCodewarsAPI);           // 10) JSON-ná alakítás
//    }

}
