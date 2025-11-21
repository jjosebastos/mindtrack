package br.com.fiap.mindtrack.auth;

import br.com.fiap.mindtrack.user.User;
import br.com.fiap.mindtrack.user.UserRepository;
import br.com.fiap.mindtrack.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OAuth2LoginService {

    private final UserRepository userRepository;

    public OAuth2LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Set<GrantedAuthority> processOAuth2User(String email,
                                                   String name,
                                                   String lastName,
                                                   String provider) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name()));
            boolean needsSave = false;
        } else {

            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(email);
            newUser.setFirstName(name);
            newUser.setLastName(lastName);
            newUser.setUserRole(UserRole.BASIC);
            newUser.setAuthProvider(provider);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + newUser.getUserRole().name()));
            userRepository.save(newUser);
        }
        return authorities;
    }

}
