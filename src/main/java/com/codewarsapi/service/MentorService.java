package com.codewarsapi.service;

import com.codewarsapi.model.Mentor;
import com.codewarsapi.repository.MentorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MentorService {

        void save(Mentor user);

        Mentor findByEmail(String email);
}
