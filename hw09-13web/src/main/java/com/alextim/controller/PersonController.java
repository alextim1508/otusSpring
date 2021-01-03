package com.alextim.controller;

import com.alextim.domain.Person;
import com.alextim.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;

@Controller
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public String getAll(Model model) {
        List<Person> persons = personService.getAll(0, Integer.MAX_VALUE);
        model.addAttribute("persons", persons);
        return "list";
    }

    @RequestMapping(value = "/person/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("action", "insert");
        return "edit";
    }

    @RequestMapping(value = "/person/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value="id") int id,
                       Model model)  {
        model.addAttribute("person", personService.findById(id));
        model.addAttribute("action", "save");
        return "edit";
    }

    @RequestMapping(value = "/person/insert", method = RequestMethod.POST)
    public String insert(@RequestParam(value="name") String name,
                         Model model, HttpServletResponse  response) {
        model.addAttribute("person", personService.add(name));
        model.addAttribute("action", "insert");
        response.setStatus(SC_CREATED );
        return "edited";
    }

    @RequestMapping(value= "/person/save", method = RequestMethod.POST)
    public String save(@RequestParam("id") int id,
                       @RequestParam("name") String name,
                       Model model, HttpServletResponse  response) {
        model.addAttribute("person", personService.update(id, name));
        model.addAttribute("action", "save");
        response.setStatus(SC_ACCEPTED );
        return "edited";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", ex.getMessage());
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }
}