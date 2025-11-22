package br.com.fiap.mindtrack.form.mood;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HumorDto {

    private Long idHumor;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "humor.type.notblank")
    private HumorType humorType;
    @NotBlank(message = "humor.comment.notblank")
    private String comentario;


}
