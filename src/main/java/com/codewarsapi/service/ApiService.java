package com.codewarsapi.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class ApiService {

    private String apiOfCompletedChallengesFor(String username) {
        return "https://www.codewars.com/api/v1/users/"+ username + "/code-challenges/completed?page=0";
    }

    JSONObject getJSON(String urlString) throws IOException {
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
        String returnedFromCodewarsAPI = content.toString();            // 9) Segédváltozó: a visszatérő String egésze
        return new JSONObject(returnedFromCodewarsAPI);           // 10) JSON-ná alakítás
    }


    private String apiOf(String username) {
        return "https://www.codewars.com/api/v1/users/" + username;
    }

    public JSONArray getArrayOfKatas(String codewars_user) throws IOException {
        JSONObject jsonFromDetails = getJSON(apiOfCompletedChallengesFor(codewars_user));
        return jsonFromDetails.getJSONArray("data");
    }


    public int getNrOfCompletedChallengesOf(String codewars_user) throws IOException {
        return getJSON(apiOf(codewars_user)).getJSONObject("codeChallenges").getInt("totalCompleted");
    }

}