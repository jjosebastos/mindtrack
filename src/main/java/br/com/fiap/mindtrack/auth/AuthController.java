package br.com.fiap.mindtrack.auth;

import br.com.fiap.mindtrack.exception.UserAlreadyExistsException;
import br.com.fiap.mindtrack.user.RegisterDto;
import br.com.fiap.mindtrack.user.UserService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;
    private final MessageSource messageSource;


    public AuthController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/login")
    public String loginPage(){ return "login";}

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String createUser(@Valid RegisterDto dto, BindingResult br,
                             RedirectAttributes redirect, Model model){

        if(br.hasErrors())
            return "register";

        if (!dto.getSenha().equals(dto.getConfirmacaoSenha())) {
            br.rejectValue("confirmacaoSenha", "password.mismatch");
            return "register";
        }

        try {
            userService.registerUser(dto);
            var message = messageSource.getMessage("user.created.success", null, LocaleContextHolder.getLocale());
            redirect.addFlashAttribute("message", message);
            return "redirect:/login";
        } catch (UserAlreadyExistsException e) {
            var message = messageSource.getMessage("user.creation.fail", null, LocaleContextHolder.getLocale());
            model.addAttribute("error", message);
            return "register";
        }

    }

}
