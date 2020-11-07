package com.abcode.springboot.resources;

import com.abcode.springboot.domain.Person;
import com.abcode.springboot.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(value = "/registerperson")
    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView("register/registerperson");
        clearForm(modelAndView);
        return modelAndView;
    }

    @PostMapping(value = "**/saveperson")
    public ModelAndView save(Person person) {
        personRepository.save(person);

        ModelAndView andView = new ModelAndView("register/registerperson");
        Iterable<Person> people = personRepository.findAll();
        andView.addObject("people", people);
        clearForm(andView);

        return andView;
    }

    @GetMapping(value = "/listing-people")
    public ModelAndView people() {
        ModelAndView andView = new ModelAndView("register/registerperson");
        Iterable<Person> people = personRepository.findAll();
        andView.addObject("people", people);
        clearForm(andView);
        return andView;
    }

    @GetMapping(value = "/person-edit/{idperson}")
    public ModelAndView editPerson(@PathVariable("idperson") Long idperson) {

        Optional<Person> person = personRepository.findById(idperson);

        ModelAndView modelAndView = new ModelAndView("register/registerperson");
        modelAndView.addObject("person", person.get());
        clearForm(modelAndView);
        return modelAndView;
    }

    @GetMapping(value = "/person-delete/{idperson}")
    public ModelAndView deletePerson(@PathVariable("idperson") Long idperson) {

        personRepository.deleteById(idperson);

        ModelAndView modelAndView = new ModelAndView("register/registerperson");
        modelAndView.addObject("people", personRepository.findAll());

        clearForm(modelAndView);
        return modelAndView;
    }

    private void clearForm(ModelAndView modelAndView) {
        modelAndView.addObject("person", new Person());
    }

}
