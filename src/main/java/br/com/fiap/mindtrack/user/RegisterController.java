package br.com.fiap.mindtrack.user;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registro")
@SessionAttributes("usuarioDTO")
public class RegisterController {

    @ModelAttribute("usuarioDTO")
    public RegisterDto usuarioDTO() {
        return new RegisterDto();
    }

    @GetMapping("/passo-um")
    public String showPassoUm(Model model){
        return "registro-passoum";
    }



}
