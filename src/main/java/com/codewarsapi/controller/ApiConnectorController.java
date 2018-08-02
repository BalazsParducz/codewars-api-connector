package com.codewarsapi.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class ApiConnectorController {

    private final String mainAPI = "https://www.codewars.com/api/v1/users/Balazs_Parducz";
    private final String katas = "https://www.codewars.com/api/v1/users/Balazs_Parducz/code-challenges/completed?page=0";

    @GetMapping(value = "/")
    public String indexPage(Model model) {
        try {
            JSONObject jsonFromMainAPI = getJSON(mainAPI);// 10) JSON-ná alakítás
            String userName = jsonFromMainAPI.getString("username");         // 11) a username érték kiemelése változóba
            String clan = jsonFromMainAPI.getString("clan");
            int completedChallenges = jsonFromMainAPI.getJSONObject("codeChallenges").getInt("totalCompleted");

            JSONObject jsonFromDetails = getJSON(katas);
            String nameOfKata = jsonFromDetails.getJSONArray("data").getJSONObject(6).get("name").toString();

            model.addAttribute("username", userName);
            model.addAttribute("clan", clan);
            model.addAttribute("completedChallenges", completedChallenges);

            model.addAttribute("nameOfKata", nameOfKata);
        } catch (IOException e) {
            model.addAttribute("error", "Unable to connect to Codewars' API.");        // log error
        }
        return "index";
    }


    private JSONObject getJSON(String urlString) throws IOException {
        URL url = new URL(urlString);                                           // 1) URL megadása
        HttpURLConnection con = (HttpURLConnection) url.openConnection();       // 2) kapcsolódás
        con.setRequestMethod("GET");                                        // 3) Request method megadás
        BufferedReader in = new BufferedReader(                         // 4) Tartalom beolvasása
                new InputStreamReader(con.getInputStream()));
        String inputLine;                                               // 5) Segéd változó
        StringBuffer content = new StringBuffer();                      // 6) Tartalom változó létrehozása
        while ((inputLine = in.readLine()) != null) {                   // 7) A Tartalom változó "feltöltése" while-lal
            content.append(inputLine);
        }
        in.close();                                                     // 8)  a BufferedReader bezárása - szükséges
        String returnedFromCodewarsAPI = content.toString();            // 9) Segédváltozó: a vissatérő String egésze
        return new JSONObject(returnedFromCodewarsAPI);           // 10) JSON-ná alakítás
    }
}
