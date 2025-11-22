package br.com.fiap.mindtrack.form.mood;

import br.com.fiap.mindtrack.exception.UserNotFoundException;
import br.com.fiap.mindtrack.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HumorService {

    private final HumorRepository humorRepository;
    private final UserRepository userRepository;

    public HumorService(HumorRepository humorRepository, UserRepository userRepository) {
        this.humorRepository = humorRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void saveHumor(Long idUser, HumorDto dto){
        var humorMapped = humorMapper(dto);
        var user = this.userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundException(""));
        humorMapped.setDataRegistro(LocalDateTime.now());
        humorMapped.setUser(user);
        humorRepository.save(humorMapped);
    }

    @Transactional
    public void deleteById(Long id){
        this.humorRepository.deleteById(id);
    }


    @Transactional
    public void updateHumor(Long idHumor, HumorDto dto) {
        // 1. Busca a entidade existente pelo ID
        Humor humor = humorRepository.findById(idHumor)
                .orElseThrow(() -> new RuntimeException("Registro de humor não encontrado para atualização."));

        // 2. Aplica as mudanças do DTO na entidade
        humor.setHumorType(dto.getHumorType());
        humor.setComentario(dto.getComentario());

        // 3. Salva a entidade atualizada (Hibernate fará o UPDATE)
        humorRepository.save(humor);
    }

    public Optional<Humor> findById(Long id){
        return humorRepository.findById(id);
    }
    public Page<Humor> findAll(Long idUser, Pageable pageable){
        return this.humorRepository.findByUser_IdUser(idUser, pageable);
    }

    private Humor humorMapper(HumorDto dto){
        return Humor.builder()
                .humorType(dto.getHumorType())
                .comentario(dto.getComentario())
                .build();
    }
}
