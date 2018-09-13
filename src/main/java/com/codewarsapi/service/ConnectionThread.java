package com.codewarsapi.service;

import com.codewarsapi.model.Kata;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;


public class ConnectionThread implements Runnable {


    ApiService apiService;

    private Kata kata;

    public ConnectionThread(Kata kata, ApiService apiService) {
        this.kata = kata;
        this.apiService = apiService;
    }

    @Override
    public void run() {
        try {
            JSONObject cherryJSON = apiService.getJSON("https://www.codewars.com/api/v1/code-challenges/" + kata.getId());
            String kyu = cherryJSON.getJSONObject("rank").getString("name");
            kata.setKyu(kyu);
            kata.setCherries(kyu);
            System.out.println(kata.getName()+" "+kata.getCherries());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
