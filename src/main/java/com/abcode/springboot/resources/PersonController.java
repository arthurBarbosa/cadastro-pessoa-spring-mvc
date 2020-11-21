package com.abcode.springboot.resources;

import com.abcode.springboot.domain.Person;
import com.abcode.springboot.domain.Phone;
import com.abcode.springboot.repository.PersonRepository;
import com.abcode.springboot.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PersonController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PhoneRepository phoneRepository;

    @GetMapping(value = "/registerperson")
    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView("register/registerperson");

        Iterable<Person> people = personRepository.findAll();
        modelAndView.addObject("people", people);

        clearForm(modelAndView);

        return modelAndView;
    }

    @PostMapping(value = "**/saveperson")
    public ModelAndView save(@Valid Person person, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("register/registerperson");
            Iterable<Person> people = personRepository.findAll();
            modelAndView.addObject("people", people);
            modelAndView.addObject("people", person);

            List<String> msg = new ArrayList<>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                msg.add(objectError.getDefaultMessage());
            }

            modelAndView.addObject("msg", msg);
            return modelAndView;
        }


        ModelAndView andView = new ModelAndView("register/registerperson");
        Iterable<Person> people = personRepository.findAll();
        andView.addObject("people", people);
        personRepository.save(person);
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

    @PostMapping("**/name")
    public ModelAndView searchByName(@RequestParam("name") String name) {
        ModelAndView modelAndView = new ModelAndView("register/registerperson");
        modelAndView.addObject("people", personRepository.findPersonByName(name));

        clearForm(modelAndView);
        return modelAndView;
    }

    @GetMapping(value = "/phones/{idperson}")
    public ModelAndView editPhone(@PathVariable("idperson") Long idperson) {

        Optional<Person> person = personRepository.findById(idperson);

        ModelAndView modelAndView = new ModelAndView("register/phones");
        modelAndView.addObject("person", person.get());
        modelAndView.addObject("phones", phoneRepository.getPhonePerson(idperson));

        return modelAndView;
    }

    @PostMapping("**/addPhone/{idperson}")
    public ModelAndView addPhone(Phone phone, @PathVariable("idperson") Long idperson) {
        Person person = personRepository.findById(idperson).get();
        phone.setPerson(person);
        phoneRepository.save(phone);

        ModelAndView modelAndView = new ModelAndView("register/phones");
        modelAndView.addObject("person", person);
        modelAndView.addObject("phones", phoneRepository.getPhonePerson(idperson));
        return modelAndView;
    }

    @GetMapping(value = "/remove-phone/{idphone}")
    public ModelAndView deletePhone(@PathVariable("idphone") Long idphone) {

        Person person = phoneRepository.findById(idphone).get().getPerson();

        phoneRepository.deleteById(idphone);

        ModelAndView modelAndView = new ModelAndView("register/phones");
        modelAndView.addObject("person", person);
        modelAndView.addObject("phones", phoneRepository.getPhonePerson(person.getId()));

        return modelAndView;
    }


    private void clearForm(ModelAndView modelAndView) {
        modelAndView.addObject("person", new Person());
    }


}
