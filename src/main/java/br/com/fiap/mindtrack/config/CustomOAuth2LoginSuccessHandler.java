package br.com.fiap.mindtrack.config;

import br.com.fiap.mindtrack.auth.OAuth2LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Component
public class CustomOAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final OAuth2LoginService oAuth2LoginService;

    public CustomOAuth2LoginSuccessHandler(OAuth2LoginService oAuth2LoginService){
        super.setDefaultTargetUrl("/index");
        this.oAuth2LoginService = oAuth2LoginService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (!(authentication instanceof OAuth2AuthenticationToken authToken)) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        OAuth2User oauth2User = authToken.getPrincipal();
        String email = extractEmail(oauth2User);
        String name = extractName(oauth2User);
        String lastName = extractFamilyName(oauth2User);
        String provider = authToken.getAuthorizedClientRegistrationId().toUpperCase();

        Set<GrantedAuthority> authorities = oAuth2LoginService.processOAuth2User(
                email, name, lastName, provider
        );

        Authentication newAuth = new OAuth2AuthenticationToken(
                oauth2User,
                authorities,
                authToken.getAuthorizedClientRegistrationId()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        super.onAuthenticationSuccess(request, response, newAuth);
    }



    private String extractEmail(OAuth2User oauth2User) {
        Map<String, Object> attributes = oauth2User.getAttributes();
        if (oauth2User instanceof OidcUser) {
            return ((OidcUser) oauth2User).getEmail();
        }
        return (String) attributes.get("email");
    }

    private String extractName(OAuth2User oauth2User) {
        Map<String, Object> attributes = oauth2User.getAttributes();
        if (oauth2User instanceof OidcUser) {
            return ((OidcUser) oauth2User).getGivenName();
        }
        String name = (String) attributes.get("name");
        if (name == null) {
            name = (String) attributes.get("login");
        }
        if (name != null && name.contains(" ") && attributes.get("given_name") == null) {
            return name.split(" ")[0];
        }
        return name;
    }

    private String extractFamilyName(OAuth2User oauth2User) {
        Map<String, Object> attributes = oauth2User.getAttributes();
        if (oauth2User instanceof OidcUser) {
            return ((OidcUser) oauth2User).getFamilyName();
        }
        String name = (String) attributes.get("name");
        String familyName = (String) attributes.get("family_name");

        if (familyName != null) {
            return familyName;
        }
        if (name != null && name.contains(" ")) {
            String[] parts = name.split(" ");
            return parts[parts.length - 1];
        }
        return null;
    }

    private String extractPicture(OAuth2User oauth2User) {
        Map<String, Object> attributes = oauth2User.getAttributes();
        if (oauth2User instanceof OidcUser) {
            return ((OidcUser) oauth2User).getPicture();
        }
        return (String) attributes.get("avatar_url");
    }
}
