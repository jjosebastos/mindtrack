package br.com.fiap.mindtrack.user;

import br.com.fiap.mindtrack.exception.UserAlreadyExistsException;
import br.com.fiap.mindtrack.telefone.Telefone;
import br.com.fiap.mindtrack.telefone.TelefoneRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
