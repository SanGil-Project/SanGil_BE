package com.project.sangil_be.CICD;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class cicdController {

    @GetMapping("/")
    public String version() {

        return "Test";
    }
}
