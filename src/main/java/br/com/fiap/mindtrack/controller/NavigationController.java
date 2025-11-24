package br.com.fiap.mindtrack.controller;

import br.com.fiap.mindtrack.user.User;
import br.com.fiap.mindtrack.user.UserRepository;
import br.com.fiap.mindtrack.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Controller
public class NavigationController {

    private final UserService userService;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public NavigationController(UserService userService, MessageSource messageSource, LocaleResolver localeResolver) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    @GetMapping({"/index", "/", "/home"})
    public String showHome(Model model,
                           @AuthenticationPrincipal UserDetails userDetails,
                           @AuthenticationPrincipal OAuth2User oauth2User,
                           HttpServletRequest request,   // <--- 2. Necessário para mudar o idioma
                           HttpServletResponse response) { // <--- 3. Necessário para mudar o idioma

        try {
            User user = this.getLoggedUser(userDetails, oauth2User);
            model.addAttribute("loggedUser", user);
            if (user.getLang() != null && !user.getLang().isEmpty()) {
                // O Spring tem um utilitário ótimo para converter "pt_BR" em objeto Locale
                Locale locale = StringUtils.parseLocaleString(user.getLang());
                localeResolver.setLocale(request, response, locale);
            }

        } catch (RuntimeException e) {
            return "redirect:/login";
        }

        return "index";
    }

    private User getLoggedUser(UserDetails userDetails, OAuth2User oauth2User) {
        String email = null;
        if (userDetails != null) {
            email = userDetails.getUsername();
        } else if (oauth2User != null) {
            email = oauth2User.getAttribute("email");
        } else {
            throw new RuntimeException("Usuário não autenticado na sessão.");
        }
        return userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));
    }
}
