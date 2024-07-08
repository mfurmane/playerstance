package pl.mfurmane.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pl.mfurmane.db.dao.UserDAO;
import pl.mfurmane.rest.model.LoginRequest;
import pl.mfurmane.rest.model.LoginResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrentUserService {

    @Autowired
    private UserDAO dao;

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





}
