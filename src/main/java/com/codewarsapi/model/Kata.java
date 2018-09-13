package com.codewarsapi.model;

import org.json.JSONArray;

public class Kata {

    String id;

    String name;

    String completedAt;

    String kyu;

    String[] programmingLanguages;

    public String[] getProgrammingLanguages() {
        return programmingLanguages;
    }

    public void setProgrammingLanguages(String[] programmingLanguages) {
        this.programmingLanguages = programmingLanguages;
    }

    int cherries;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getKyu() {
        return kyu;
    }

    public void setKyu(String kyu) {
        this.kyu = kyu;
    }

    public int getCherries() {
        return cherries;
    }

    public void setCherries(String kyu) {
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
        this.cherries = cherry;
    }
}
