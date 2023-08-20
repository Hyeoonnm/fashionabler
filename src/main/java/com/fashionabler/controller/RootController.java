package com.fashionabler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/")
public class RootController {

    public String index() {
        return "index";
    }


}
