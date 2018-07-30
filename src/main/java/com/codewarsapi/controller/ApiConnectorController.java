package com.codewarsapi.controller;


import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class ApiConnectorController {


    @GetMapping(value = "/")
    public String indexPage(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        URL url;
        try {
            url = new URL("https://www.codewars.com/api/v1/users/Balazs_Parducz"); // 1) URL megadása
            HttpURLConnection con = (HttpURLConnection) url.openConnection();           // 2) kapcsolódás
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
            JSONObject jsonObject = new JSONObject(returnedFromCodewarsAPI);// 10) JSON-ná alakítás
            String userName = jsonObject.getString("username");         // 11) a username érték kiemelése változóba
            String clan = jsonObject.getString("clan");


            String response = restTemplate.getForObject("https://www.codewars.com/api/v1/users/Balazs_Parducz/code-challenges/completed?page=0", String.class);
//            JSONObject jsonObject1 = new JSONObject(response);
//            String lastKata = jsonObject1.getString("data");

            model.addAttribute("blabla", restTemplate.getForObject(url.toString(), String.class));
            model.addAttribute("username", userName);
            model.addAttribute("clan", clan);
//            model.addAttribute("lastKata", lastKata);
        } catch (IOException e) {
            model.addAttribute("Unable to connect to Codewars' API.");        // log error
        }
        return "index";
    }
}
