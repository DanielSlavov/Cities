package com.dreamix.Cities.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/"})
public class WebController {

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView index() {
            return new ModelAndView("index.html");
    }
}

