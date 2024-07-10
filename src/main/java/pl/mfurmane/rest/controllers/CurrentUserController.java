package pl.mfurmane.rest.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.mfurmane.db.dto.PlayerDTO;
import pl.mfurmane.rest.model.LoginRequest;
import pl.mfurmane.rest.model.LoginResponse;
import pl.mfurmane.rest.model.RegisterRequest;
import pl.mfurmane.rest.model.RestPlayer;
import pl.mfurmane.rest.services.CurrentUserService;

@RestController
@RequestMapping("")
public class CurrentUserController {

    @Autowired
    private CurrentUserService service;

    @GetMapping(value = "/api/chuj")
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@RequestBody String name) {
        return "Hello " + name + "!";
    }

    @GetMapping(value = "/chuj/bezapi")
    @ResponseStatus(HttpStatus.OK)
    public String greetingText2(@RequestBody String name) {
        return "Hello " + name + "!";
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest request, HttpServletRequest servletRequest, HttpServletResponse response) {
        return service.login(request, servletRequest, response);
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.OK)
    public RestPlayer register(@RequestBody RegisterRequest request, HttpServletRequest servletRequest, HttpServletResponse response) {
        return service.registerPlayer(request);
    }




}
