package com.codewarsapi.model;

public class CodewarsUser {

    String username;

    String clan;

    int totalOfCompletedChallenges;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClan() {
        return clan;
    }

    public void setClan(String clan) {
        this.clan = clan;
    }

    public int getTotalOfCompletedChallenges() {
        return totalOfCompletedChallenges;
    }

    public void setTotalOfCompletedChallenges(int totalOfCompletedChallenges) {
        this.totalOfCompletedChallenges = totalOfCompletedChallenges;
    }
}
