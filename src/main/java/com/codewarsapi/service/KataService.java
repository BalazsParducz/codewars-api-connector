package com.codewarsapi.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class KataService {

    private ApiService apiService = new ApiService();

    private int calculateCherries(String kyu) {
        int cherry = 0;
        switch (kyu) {
            case "8 kyu": cherry = 1; break;
            case "7 kyu": cherry = 2; break;
            case "6 kyu": cherry = 4; break;
            case "5 kyu": cherry = 6; break;
            case "4 kyu": cherry = 8; break;
            case "3 kyu": cherry = 12; break;
            case "2 kyu": cherry = 16; break;
            case "1 kyu": cherry = 20; break;
        }
        return cherry;
    }

    public int getCherriesFor(String kataId) throws IOException {
        JSONObject cherryJSON = apiService.getJSON("https://www.codewars.com/api/v1/code-challenges/" + kataId);
        String kyu = cherryJSON.getJSONObject("rank").getString("name");
        System.out.println(kyu);
        System.out.println(calculateCherries(kyu));
        return calculateCherries(kyu);
    }

    public List<String> getKatasForAGivenPeriod(String codewarsUser, LocalDate beginningOfTwoWeeksPeriod) throws IOException {
        final List<String> katasOfGivenPeriod = new ArrayList<>();
        JSONArray array = apiService.getArrayOfKatas(codewarsUser);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        for(int i = 0; i<array.length(); i++) {
            String completionTimeAsString = array.getJSONObject(i).getString("completedAt");
            LocalDate submissionTime = LocalDate.parse(completionTimeAsString, formatter);
            if(submissionTime.isAfter(beginningOfTwoWeeksPeriod)) {
                katasOfGivenPeriod.add(array.getJSONObject(i).getString("id"));
            } else {
                break;
            }
        }

        System.out.println(katasOfGivenPeriod.size());
        System.out.println(katasOfGivenPeriod);
        return katasOfGivenPeriod;
    }

    public int getPoints(List<String> katasToCalculatePointsFor) throws IOException {
        int allCherries = 0;
        for (String kata: katasToCalculatePointsFor) {
            allCherries += getCherriesFor(kata);
        }
        return allCherries/15;
    }


    public static void main(String[] args) throws IOException {
        KataService kataService = new KataService();
        kataService.getCherriesFor("cut-the-cake");
//        kataService.getPoints(getKatas);
    }
}
