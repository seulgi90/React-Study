package com.project.mallapi.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api")
public class TestController {

    @PostMapping("/test")
    public String test() {
        log.info("-----TestController----");
        return "test";
    }
}
