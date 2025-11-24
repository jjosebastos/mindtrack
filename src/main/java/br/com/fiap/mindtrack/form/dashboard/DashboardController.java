package br.com.fiap.mindtrack.form.dashboard;

import br.com.fiap.mindtrack.user.User;
import br.com.fiap.mindtrack.user.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final DashboardService dashboardService;
    private final UserRepository userRepository;

    public DashboardController(DashboardService dashboardService, UserRepository userRepository) {
        this.dashboardService = dashboardService;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model,
                                @AuthenticationPrincipal Object principal) {
        try {
            User user = getLoggedUser(principal);

            if (user != null) {
                DashboardDto dashboardData = dashboardService.getDashboardData(user.getIdUser());
                model.addAttribute("dashboardData", dashboardData);
            } else {
                model.addAttribute("dashboardData", new DashboardDto());
            }

        } catch (Exception e) {

            model.addAttribute("dashboardData", new DashboardDto());
        }

        return "fragments/dashboard";
    }

    private User getLoggedUser(Object principal) {
        String email = null;

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else if (principal instanceof OAuth2User) {
            email = ((OAuth2User) principal).getAttribute("email");
        }

        if (email != null) {
            return userRepository.findByEmail(email).orElse(null);
        }

        return null;
    }
}