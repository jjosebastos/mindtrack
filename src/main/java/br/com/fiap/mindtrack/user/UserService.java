package br.com.fiap.mindtrack.user;

import br.com.fiap.mindtrack.exception.UserAlreadyExistsException;
import br.com.fiap.mindtrack.form.settings.SettingsDto;
import br.com.fiap.mindtrack.form.telefone.Telefone;
import br.com.fiap.mindtrack.form.telefone.TelefoneRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TelefoneRepository telefoneRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, TelefoneRepository telefoneRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.telefoneRepository = telefoneRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(RegisterDto dto){
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("O e-mail informado já está cadastrado.");
        }
        var user = userMapper(dto);
        var telefone = telefoneMapper(dto);
        userRepository.save(user);
        telefone.setUser(user);
        telefoneRepository.save(telefone);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void updateUser(Long userId, SettingsDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (StringUtils.hasText(dto.getNome())) {
            user.setFirstName(dto.getNome());
        }
        if (StringUtils.hasText(dto.getSobrenome())) {
            user.setLastName(dto.getSobrenome());
        }
        if (StringUtils.hasText(dto.getLang())) {
            user.setLang(dto.getLang());
        }
        if (StringUtils.hasText(dto.getNewEmail())) {
            user.setEmail(dto.getNewEmail());
            user.setUsername(dto.getNewEmail());
        }

        if (StringUtils.hasText(dto.getNewPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        }
        userRepository.save(user);
    }
    private User userMapper(RegisterDto dto){
        return User.builder()
                .firstName(dto.getNome())
                .lastName(dto.getSobrenome())
                .userRole(UserRole.BASIC)
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getSenha()))
                .authProvider("WEB_FORM")
                .dateCreation(LocalDateTime.now())
                .build();
    }

    private Telefone telefoneMapper(RegisterDto dto){
        return Telefone.builder()
                .numero(dto.getTelefone())
                .tipo(dto.getTipoTelefone())
                .build();
    }
}
