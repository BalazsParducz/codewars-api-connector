package com.codewarsapi.service;

import com.codewarsapi.model.Mentor;
import com.codewarsapi.repository.MentorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    private MentorRepo mentorRepo;
//    @Autowired
//    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Mentor mentor) {
        mentor.setPassword(bCryptPasswordEncoder.encode(mentor.getPassword()));
//        mentor.setMatchingPassword(bCryptPasswordEncoder.encode(mentor.getMatchingPassword()));
//        user.setRoles(new HashSet<>(roleRepository.findAll()));
        mentorRepo.save(mentor);
    }

    @Override
    public Mentor findByEmail(String email) {
        return mentorRepo.findMentorByEmail(email);
    }
}