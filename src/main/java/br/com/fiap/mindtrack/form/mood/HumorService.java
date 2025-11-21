package br.com.fiap.mindtrack.form.mood;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HumorService {

    private final HumorRepository humorRepository;

    public HumorService(HumorRepository humorRepository) {
        this.humorRepository = humorRepository;
    }

    @Transactional
    public void saveHumor(HumorDto dto){
        var humorMapped = humorMapper(dto);
        humorRepository.save(humorMapped);
    }



    private Humor humorMapper(HumorDto dto){
        return Humor.builder()
                .humorType(dto.getHumorType())
                .comentario(dto.getComentario())
                .build();
    }
}
