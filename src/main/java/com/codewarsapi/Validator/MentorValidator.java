package com.codewarsapi.Validator;

import com.codewarsapi.model.Mentor;
import com.codewarsapi.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MentorValidator implements Validator {
    @Autowired
    private MentorService mentorService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Mentor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Mentor mentor = (Mentor) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (mentor.getEmail().length() < 6 || mentor.getEmail().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (mentorService.findByEmail(mentor.getEmail()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (mentor.getPassword().length() < 3 || mentor.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!mentor.getMatchingPassword().equals(mentor.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
