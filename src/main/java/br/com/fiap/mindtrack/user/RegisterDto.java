package br.com.fiap.mindtrack.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {

    @NotBlank(message = "{register.nome.notblank}")
    @Size(min = 2, max = 50, message = "{register.nome.size}")
    private String nome;

    @NotBlank(message = "{register.sobrenome.notblank}")
    @Size(min = 2, max = 100, message = "{register.sobrenome.size}")
    private String sobrenome;

    @NotBlank(message = "{register.email.notblank}")
    @Email(message = "{register.email.invalid}")
    private String email;

    @NotBlank(message = "{register.senha.notblank}")
    @Size(min = 6, message = "{register.senha.min}")
    private String senha;

    @NotBlank(message = "{register.confirmacaoSenha.notblank}")
    private String confirmacaoSenha;

    @Size(max = 20, message = "{register.telefone.size}")
    private String telefone;

    private String tipoTelefone;

}
