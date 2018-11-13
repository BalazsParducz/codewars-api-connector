package com.codewarsapi.controller;

import com.codewarsapi.model.Mentor;
import com.codewarsapi.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private final MentorService mentorService;

    @Autowired
    public AdminController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model){
        List<Mentor> mentors = mentorService.getAllMentors();
        System.out.println(mentors);
        model.addAttribute("mentorList", mentors);
        return "admin";
    }
}
