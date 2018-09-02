package com.codewarsapi.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ApiService {

    private final int pageOfRecentKatas = 0;


    public String apiOfCompletedChallengesFor(String username) {
        return "https://www.codewars.com/api/v1/users/"+ username + "/code-challenges/completed?page=0";
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

    public String apiOf(String username) {
        return "https://www.codewars.com/api/v1/users/" + username;
    }

    public JSONArray getArrayOfKatas(String codewars_user) throws IOException {
        JSONObject jsonFromDetails = getJSON(apiOfCompletedChallengesFor(codewars_user));
        return jsonFromDetails.getJSONArray("data");
    }

    public Date getTimeOfCompletion(String codewars_user) throws IOException, ParseException {
        JSONObject jsonFromDetails = getJSON(apiOfCompletedChallengesFor(codewars_user));
        String date = jsonFromDetails.getJSONArray("data").getJSONObject(pageOfRecentKatas).get("completedAt").toString();
        SimpleDateFormat hungarianDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date timeOfCompletion = hungarianDateFormat.parse(date);
        return timeOfCompletion;
    }

    public String getNameOfKata(String codewars_user) throws IOException {
        String nameOfKata = getJSON(apiOfCompletedChallengesFor(codewars_user)).getJSONArray("data").getJSONObject(pageOfRecentKatas).get("name").toString();
        return nameOfKata;
    }

    public String getUserName(String codewars_user) throws IOException {
        String userName = getJSON(apiOf(codewars_user)).getString("username");
        return userName;
    }

    public String getUsersClan(String codewars_user) throws IOException {
        String clan = getJSON(apiOf(codewars_user)).getString("clan");
        return clan;
    }

    public int getNrOfCompletedChallengesOf(String codewars_user) throws IOException {
        int completedChallenges = getJSON(apiOf(codewars_user)).getJSONObject("codeChallenges").getInt("totalCompleted");
        return completedChallenges;
    }
}