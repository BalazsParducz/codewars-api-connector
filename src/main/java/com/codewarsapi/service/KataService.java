package com.codewarsapi.service;

import com.codewarsapi.model.Kata;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class KataService {

    @Autowired
    private ApiService apiService;

    private List<Kata> latestKatas;

    private List<Kata> setCherries(List<Kata> latestKatas) throws IOException {
        List<Thread> threads = new ArrayList<>();
        for (Kata kata: latestKatas) {
            Thread t = new Thread(new ConnectionThread(kata, apiService));
            t.start();
            threads.add(t);
        }
        while(threads.stream().anyMatch(Thread::isAlive)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return latestKatas;
    }

    public int getTotalCherriesForKatasForAGivenPeriod(List<Kata> latestKatas) {
        int cherries = 0;
        for(Kata kata: latestKatas) {
            cherries = cherries + kata.getCherries();
        }
        return cherries;
    }

    public List<Kata> getKatasForAGivenPeriod(List<Kata> allKatas, LocalDate beginningOfPeriod) throws IOException {
        latestKatas = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        for(Kata kata : allKatas) {
            String completionTimeAsString = kata.getCompletedAt();
            LocalDate submissionTime = LocalDate.parse(completionTimeAsString, formatter);
            if(submissionTime.isAfter(beginningOfPeriod)) {
                latestKatas.add(kata);
            } else {
                break;
            }
        }
        setCherries(latestKatas);
        System.out.println("Recent katas " + latestKatas.size());
        System.out.println(latestKatas);
        return latestKatas;
    }



    public List<Kata> allKatasResolvedByUser(String codewarsUsername) throws IOException {
        final List<Kata> allKatasResolvedByUser = new ArrayList<>();
        JSONArray array = apiService.getArrayOfKatas(codewarsUsername);
        Kata kata;
        for (int i =0; i<array.length(); i++) {
            kata = new Kata();
            kata.setId(array.getJSONObject(i).getString("id"));
            kata.setName(array.getJSONObject(i).getString("name"));
            kata.setCompletedAt(array.getJSONObject(i).getString("completedAt"));
            JSONArray jsonArray = array.getJSONObject(i).getJSONArray("completedLanguages");
            String[] languages = new String[jsonArray.length()];
            for (int j = 0; j < jsonArray.length(); j++) {
                languages[j] = jsonArray.getString(j);
            }
            kata.setProgrammingLanguages(languages);
            allKatasResolvedByUser.add(kata);
        }
        return allKatasResolvedByUser;
    }
}
