package br.com.fiap.mindtrack.form.mood;

import br.com.fiap.mindtrack.user.User;
import br.com.fiap.mindtrack.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HumorController {


    private final MessageSource messageSource;
    private final UserRepository userRepository;
    private final HumorService humorService;

    public HumorController(MessageSource messageSource, UserRepository userRepository, HumorService humorService) {
        this.messageSource = messageSource;
        this.userRepository = userRepository;
        this.humorService = humorService;
    }

    @GetMapping("/mood")
    public String showMood(Model model){
        model.addAttribute("humorDto", new HumorDto());
        return "fragments/mood";
    }

    @PostMapping("/mood")
    public String saveOrUpdateMood(@Valid HumorDto dto, BindingResult br,
                                   RedirectAttributes redirectAttributes,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   @AuthenticationPrincipal OAuth2User oauth2User) {
        if (br.hasErrors()) return "fragments/mood";
        User user = getLoggedUser(userDetails, oauth2User);
        try {
            if (dto.getIdHumor() == null) {
                humorService.saveHumor(user.getIdUser(), dto);
                var message = messageSource.getMessage("mood.register.success", null, LocaleContextHolder.getLocale());
                redirectAttributes.addFlashAttribute("message", message);
            } else {
                humorService.updateHumor(dto.getIdHumor(), dto);
                var message = messageSource.getMessage("mood.update.success", null, LocaleContextHolder.getLocale());
                redirectAttributes.addFlashAttribute("message", message);
            }
            return "redirect:/history-list";
        } catch (Exception e) {
            var message = messageSource.getMessage("mood.update.fail", null, LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/mood";
        }
    }


    @GetMapping("/mood/{id}/edit")
    public String showEditMood(@PathVariable Long id, Model model) {
        Humor humor = humorService.findById(id).orElseThrow();
        HumorDto dto = new HumorDto();
        dto.setIdHumor(humor.getIdHumor());
        dto.setHumorType(humor.getHumorType());
        dto.setComentario(humor.getComentario());

        model.addAttribute("humorDto", dto);
        return "fragments/mood";
    }


    @GetMapping("/history-list")
    public String showHistory(Model model,
                              @PageableDefault(size = 5, sort = "dataRegistro", // Usando o campo correto
                                      direction = Sort.Direction.DESC) Pageable pageable,
                              @AuthenticationPrincipal UserDetails userDetails,
                              @AuthenticationPrincipal OAuth2User oauth2User) {

        Page<Humor> humorPage = Page.empty(pageable);

        try {
            User user = this.getLoggedUser(userDetails, oauth2User);
            humorPage = humorService.findAll(user.getIdUser(), pageable);

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("humorPage", humorPage);

        return "fragments/history";
    }

    @PostMapping("/mood/{id}/delete")
    public String deleteMood(@PathVariable Long id,
                             RedirectAttributes redirect,
                             @PageableDefault(size = 5) Pageable pageable) {

        humorService.deleteById(id);
        redirect.addFlashAttribute("message", "Registro excluído com sucesso.");
        redirect.addAttribute("page", pageable.getPageNumber());
        redirect.addAttribute("size", pageable.getPageSize());

        return "redirect:/history-list";
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
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no banco de dados"));
    }

}
