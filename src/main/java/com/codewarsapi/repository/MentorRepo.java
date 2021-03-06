package com.codewarsapi.repository;

import com.codewarsapi.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepo extends JpaRepository<Mentor, Long> {

    Mentor findMentorByEmail(String email);
}
