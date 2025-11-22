package br.com.fiap.mindtrack.form.settings;

import br.com.fiap.mindtrack.user.RegisterDto;
import br.com.fiap.mindtrack.user.User;
import br.com.fiap.mindtrack.user.UserRepository;
import br.com.fiap.mindtrack.user.UserService;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SettingsController {

    private final UserService userService;
    private final MessageSource messageSource;

    public SettingsController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/settings")
    public String showSettings(Model model,
                               @AuthenticationPrincipal UserDetails userDetails,
                               @AuthenticationPrincipal OAuth2User oauth2User) {

        String email = null;
        if (userDetails != null) {
            email = userDetails.getUsername();
        }
        else if (oauth2User != null) {
            email = oauth2User.getAttribute("email");
        } else {
            throw new RuntimeException("Usuário não autenticado");
        }

        var user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));
        SettingsDto dto = new SettingsDto();
        dto.setNome(user.getFirstName());
        dto.setSobrenome(user.getLastName());
        dto.setNewEmail(user.getEmail());
        dto.setAuthProvider(user.getAuthProvider());
        dto.setLang(user.getLang());
        model.addAttribute("settingsDto", dto);

        return "fragments/settings";
    }

    @PostMapping("/settings/profile")
    public String updateProfile(SettingsDto dto,
                                @AuthenticationPrincipal UserDetails userDetails,
                                @AuthenticationPrincipal OAuth2User oauth2User,
                                RedirectAttributes redirect) {
        try {
            User user = getLoggedUser(userDetails, oauth2User);

            userService.updateUser(user.getIdUser(), dto);

            redirect.addFlashAttribute("message", "Perfil atualizado com sucesso!");
            if (dto.getLang() != null && !dto.getLang().isEmpty()) {
                return "redirect:/settings?lang=" + dto.getLang();
            }
        } catch (Exception e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/settings";
    }


    private User getLoggedUser(UserDetails userDetails, OAuth2User oauth2User) {
        String email = null;
        if (userDetails != null) {
            email = userDetails.getUsername();
        }
        else if (oauth2User != null) {
            email = oauth2User.getAttribute("email");
        } else {
            throw new RuntimeException("Usuário não autenticado na sessão.");
        }
        return userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));
    }
}
