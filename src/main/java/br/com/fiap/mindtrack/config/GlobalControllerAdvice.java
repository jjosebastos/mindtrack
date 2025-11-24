package br.com.fiap.mindtrack.config;

import br.com.fiap.mindtrack.user.User;
import br.com.fiap.mindtrack.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {


    @Autowired
    private UserRepository userRepository;

    @ModelAttribute("loggedUser")
    public User globalUser(@AuthenticationPrincipal UserDetails userDetails,
                           @AuthenticationPrincipal OAuth2User oauth2User) {

        if (userDetails == null && oauth2User == null) {
            return null;
        }

        String email = (userDetails != null) ? userDetails.getUsername() : oauth2User.getAttribute("email");

        return userRepository.findByEmail(email).orElse(null);
    }

}
