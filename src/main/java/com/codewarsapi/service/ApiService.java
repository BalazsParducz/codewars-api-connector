package com.codewarsapi.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiService {

    public String apiOfCompletedChallengesFor(String username) {
        return "https://www.codewars.com/api/v1/users/"+ username + "/code-challenges/completed?page=0";
    }

    public String apiOf(String username) {
        return "https://www.codewars.com/api/v1/users/" + username;
    }
}