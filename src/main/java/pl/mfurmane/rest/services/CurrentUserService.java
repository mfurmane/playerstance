package pl.mfurmane.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pl.mfurmane.db.dao.PlayerDAO;
import pl.mfurmane.db.dto.PlayerDTO;
import pl.mfurmane.rest.model.LoginRequest;
import pl.mfurmane.rest.model.LoginResponse;
import pl.mfurmane.rest.model.RegisterRequest;
import pl.mfurmane.rest.model.RegisterResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CurrentUserService {

    @Autowired
    private PlayerDAO dao;

    public LoginResponse login(LoginRequest request) {
        //Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword(), new ArrayList<>());

        dao.byId(Long.parseLong(request.getUsername()));

        List<GrantedAuthority> grantedAuths = new ArrayList<>();

        //validate and do your additionl logic and set the role type after your validation. in this code i am simply adding admin role type
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN" ));


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword(), grantedAuths);

        System.out.println(usernamePasswordAuthenticationToken.getName());
        System.out.println(usernamePasswordAuthenticationToken.isAuthenticated());

        LoginResponse response = new LoginResponse(usernamePasswordAuthenticationToken);
        return response;
    }

    public PlayerDTO registerPlayer(RegisterRequest request) {
        Objects.requireNonNull(request, "request can't be null");
        String email = validateEmail(request.getEmail());
        String password = validatePassword(request.getPassword());
        PlayerDTO player = new PlayerDTO(email, password);

        dao.registerPlayer(player);

        return dao.byEmail(email);
    }

    private static String validatePassword(String input) {
        String password = Objects.requireNonNull(input, "password can't be null");
        return password;
    }

    private static String validateEmail(String input) {
        String email = Objects.requireNonNull(input, "email can't be null");
        return email;
    }


}
