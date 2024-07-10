package pl.mfurmane.rest.services;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.mfurmane.db.dao.PlayerDAO;
import pl.mfurmane.db.dto.PlayerDTO;
import pl.mfurmane.rest.model.LoginRequest;
import pl.mfurmane.rest.model.LoginResponse;
import pl.mfurmane.rest.model.RegisterRequest;
import pl.mfurmane.rest.model.RestPlayer;
import pl.mfurmane.rest.utils.ApiAuthenticationProvider;
import pl.mfurmane.rest.utils.Decryptor;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class CurrentUserService {

    private final static String registerSession = "tmp";

    @Autowired
    private PlayerDAO dao;

    @Autowired
    private ApiAuthenticationProvider authManager;

    public LoginResponse login(LoginRequest request) {
        Objects.requireNonNull(request, "request can't be null");
        System.out.println(securityContext().getAuthentication());


        String email = decrypt(request.getEmail(), "email can't be null");
        String password = decrypt(request.getPassword(), "password can't be null");

        PlayerDTO player = dao.byEmail(email);
        if (player == null) {
            throw new IllegalArgumentException("player don't exist");
        }
        boolean passwordCorrect = player.getPassword().equals(password);
        if (passwordCorrect) {
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(email, password);
            Authentication auth = authManager.authenticate(authReq);
            securityContext().setAuthentication(auth);

            LoginResponse response = new LoginResponse(authReq);
            return response;
        } else {
            throw new IllegalArgumentException("incorrect password");
        }
    }

    private static SecurityContext securityContext() {
        return SecurityContextHolder.getContext();
    }

    public RestPlayer registerPlayer(RegisterRequest request) {
        Objects.requireNonNull(request, "request can't be null");
        String email = validateEmail(request.getEmail());
        String password = validatePassword(request.getPassword());
        PlayerDTO player = new PlayerDTO(email, password);

        dao.registerPlayer(player);

        System.out.println(player.getId());

//        PlayerDTO playerDTO = dao.byEmail(email);
        return encryptPlayer(player);
    }

    private RestPlayer encryptPlayer(PlayerDTO player) {
        RestPlayer output = new RestPlayer();
        String secret = sessionToken();
        output.setEmail(Decryptor.encrypt(player.getEmail(), secret));
        output.setPassword(Decryptor.encrypt(player.getPassword(), secret));
        output.setId(Decryptor.encrypt(String.valueOf(player.getId()), secret));
        return output;
    }

    private String validateEmail(String input) {
        String email = decrypt(input, "email can't be null");
        if (EmailValidator.getInstance().isValid(email)) {
            return email;
        }
        throw new IllegalArgumentException("invalid email");
    }

    private String validatePassword(String input) {
        String password = decrypt(input, "password can't be null");
        if (password.length() < 8) throw new IllegalArgumentException("password too short");
        if (password.length() > 32) throw new IllegalArgumentException("password too long");
        boolean hasSmallLetter = Pattern.compile("[a-z]").matcher(password).find();
        boolean hasBigLetter = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasDigit = Pattern.compile("[0-9]").matcher(password).find();
        boolean hasSpecial = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]").matcher(password).find();
        if (hasSmallLetter && hasBigLetter && hasDigit && hasSpecial) {
            return hashPassword(password);
        }
        throw new IllegalArgumentException("invalid password");
    }

    private String decrypt(String input, String message) {
        Objects.requireNonNull(input, message);
        return Decryptor.decrypt(input, sessionToken());
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            return DatatypeConverter
                    .printHexBinary(md.digest()).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("hashing problem", e);
        }
    }

    private String sessionToken() {
        String session = session();
        if (session == null) {
            return registerSession;
        }
        return session;
    }

    private String session() {
        return null;
    }


}
