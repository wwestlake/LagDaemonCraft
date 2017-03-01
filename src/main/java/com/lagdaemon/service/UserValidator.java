package com.lagdaemon.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.lagdaemon.domain.User;

@Component
public class UserValidator implements Validator {

	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private Pattern pattern;
	private Matcher matcher;
	
	public UserValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
	
	
	
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (userService.findByUsername(user.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }

        if (! validateEmail(user.getEmail())) {
        	errors.rejectValue("email", "Format.userForm.email");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordHash", "NotEmpty");
        if (user.getPasswordHash().length() < 8 || user.getPasswordHash().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getPasswordConfirm().equals(user.getPasswordHash())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
    
    
	public boolean validateEmail(final String email) {
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
