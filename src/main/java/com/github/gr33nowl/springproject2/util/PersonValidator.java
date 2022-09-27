package com.github.gr33nowl.springproject2.util;

import com.github.gr33nowl.springproject2.models.Person;
import com.github.gr33nowl.springproject2.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Person person = (Person) o;

        if (peopleService.getPersonByFullName(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Person with such full name already exists");
        }
    }
}
