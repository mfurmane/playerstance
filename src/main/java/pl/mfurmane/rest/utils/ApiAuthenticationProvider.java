package pl.mfurmane.rest.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pl.mfurmane.db.dao.PlayerDAO;
import pl.mfurmane.db.dto.PlayerDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApiAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PlayerDAO dao;

        @Override
        public Authentication authenticate(Authentication authentication)
                throws AuthenticationException {
            String email = authentication.getName();
            String password = (String) authentication.getCredentials();
            List<GrantedAuthority> grantedAuths = new ArrayList<>();

            PlayerDTO player = dao.byEmail(email);
            if (player == null) {
                throw new IllegalArgumentException("player don't exist");
            }
            System.out.println("Compare passwords:/n" +password + "/n" + player.getPassword());
            boolean passwordIncorrect = !player.getPassword().equals(password);
            if (passwordIncorrect) {
                throw new IllegalArgumentException("incorrect password");
            }
//            player

            //validate and do your additionl logic and set the role type after your validation. in this code i am simply adding admin role type
            grantedAuths.add(new SimpleGrantedAuthority(player.getRole() ));

            return new UsernamePasswordAuthenticationToken(email, password, grantedAuths);
        }

        @Override
        public boolean supports(Class<?> arg0) {
            return true;
        }

    }

