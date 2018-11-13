package com.codewarsapi.service;

import com.codewarsapi.model.Mentor;
import com.codewarsapi.model.Role;
import com.codewarsapi.repository.MentorRepo;
import com.codewarsapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;


@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    private MentorRepo mentorRepo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Mentor mentor) {
        Role userRole =  createRoleIfNotFound("USER");
        mentor.setPassword(bCryptPasswordEncoder.encode(mentor.getPassword()));
        mentor.setRoles(Arrays.asList(userRole));
        mentorRepo.save(mentor);
    }

    private Role createRoleIfNotFound(String name) {

        Role role = roleRepository.findRoleByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }

    @Override
    public Mentor findByEmail(String email) {
        return mentorRepo.findMentorByEmail(email);
    }

    @Override
    public List<Mentor> getAllMentors() {
        return mentorRepo.findAll();
    }
}