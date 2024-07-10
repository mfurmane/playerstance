package pl.mfurmane.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.mfurmane.db.dto.PlayerDTO;
import pl.mfurmane.rest.model.LoginRequest;
import pl.mfurmane.rest.model.LoginResponse;
import pl.mfurmane.rest.model.RegisterRequest;
import pl.mfurmane.rest.model.RegisterResponse;
import pl.mfurmane.rest.services.CurrentUserService;

@RestController
@RequestMapping("")
public class CurrentUserController {

    @Autowired
    private CurrentUserService service;

    @GetMapping(value = "/api/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Hello " + name + "!";
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.OK)
    public PlayerDTO register(@RequestBody RegisterRequest request) {
        return service.registerPlayer(request);
    }




}
