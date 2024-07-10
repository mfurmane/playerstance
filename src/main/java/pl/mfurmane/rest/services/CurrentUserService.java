package pl.mfurmane.rest.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
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

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();


    public LoginResponse login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse responsee) {
        //, String email, String password
        Objects.requireNonNull(request, "request can't be null");
        System.out.println("SESSION " + RequestContextHolder.currentRequestAttributes().getSessionId());
        String email = decrypt(loginRequest.getEmail(), "email can't be null");
        String password = decrypt(loginRequest.getPassword(), "password can't be null");

        UsernamePasswordAuthenticationToken authReq = UsernamePasswordAuthenticationToken.unauthenticated(email, password);
            Authentication auth = authManager.authenticate(authReq);
//            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
//            context.setAuthentication(auth);
        securityContext().setAuthentication(auth);
            securityContextHolderStrategy.setContext(securityContext());
            securityContextRepository.saveContext(securityContext(), request, responsee);

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
//        System.out.println("SESSION " + sessionId);


//            request.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT", securityContext());

            LoginResponse response = new LoginResponse(sessionId);

            return response;
//        } else {
//            throw new IllegalArgumentException("incorrect password");
//        }
    }

    private static SecurityContext securityContext() {
        return SecurityContextHolder.getContext();
    }

    public RestPlayer registerPlayer(RegisterRequest request) {
        System.out.println("SESSION " + RequestContextHolder.currentRequestAttributes().getSessionId());
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
        Decryptor decryptor = new Decryptor();
        RestPlayer output = new RestPlayer();
        String secret = sessionToken();
        output.setEmail(decryptor.encrypt(player.getEmail()));
        output.setPassword(decryptor.encrypt(player.getPassword()));
        output.setId(decryptor.encrypt(String.valueOf(player.getId())));
        output.setSession(RequestContextHolder.currentRequestAttributes().getSessionId());
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
        Decryptor decryptor = new Decryptor();
        Objects.requireNonNull(input, message);
        return decryptor.decrypt(input);
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
