package com.codewarsapi.service;

import com.codewarsapi.model.Mentor;
import com.codewarsapi.repository.MentorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class MentorDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private MentorRepo mentorRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Mentor mentor = mentorRepo.findMentorByEmail(username);

        return new org.springframework.security.core.userdetails.User(mentor.getEmail(),
                mentor.getPassword(),
                isAdmin(username)? Collections.singletonList(new SimpleGrantedAuthority("USER_LIST")) : Collections.emptyList());
    }

    private boolean isAdmin(String username) {
        if(username.equals("ADMIN")) {
            return true;
        }
        return false;
    }
}