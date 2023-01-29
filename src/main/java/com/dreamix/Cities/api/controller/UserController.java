package com.dreamix.Cities.api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/roles")
    public List<String> getUserAuthorities(Authentication principal) {
        return principal.getAuthorities().stream().map(Object::toString).collect(Collectors.toList());
    }
}
