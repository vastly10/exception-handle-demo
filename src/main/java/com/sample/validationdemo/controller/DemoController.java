package com.sample.validationdemo.controller;

import com.sample.validationdemo.domain.Domain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @GetMapping("normal")
    public @ResponseBody ResponseEntity<Domain> getDomain(Domain domain) {
        return new ResponseEntity<>(domain, HttpStatus.OK);
    }
}
