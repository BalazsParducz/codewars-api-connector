package com.codewarsapi.service;

import com.codewarsapi.model.Mentor;
import com.codewarsapi.repository.MentorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MentorService {

//    private MentorRepo mentorRepo;
//
//
//    @Autowired
//    public void setMentorRepo(MentorRepo mentorRepo) {
//        this.mentorRepo = mentorRepo;
//    }
//
//
//    public void deleteAll() {
//        mentorRepo.deleteAll();
//    }
//
//    public void saveMentor(Mentor entity) {
//        mentorRepo.save(entity);
//    }
//
//    public Mentor findById(Long id) {
//        return mentorRepo.findOne(id);
//    }
//
//    public Mentor findByUsername(String username ) {
//        return mentorRepo.findMentorByEmail(username);
//    }



        void save(Mentor user);

        Mentor findByEmail(String email);


}
