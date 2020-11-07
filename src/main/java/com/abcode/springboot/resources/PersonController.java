package com.abcode.springboot.resources;

import com.abcode.springboot.domain.Person;
import com.abcode.springboot.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(value = "/registerperson")
    public String init() {
        return "register/registerperson";
    }

    @PostMapping(value = "/saveperson")
    public String save(Person person) {
        personRepository.save(person);
        return "register/registerperson";
    }

}
