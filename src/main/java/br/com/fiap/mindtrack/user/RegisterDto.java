package br.com.fiap.mindtrack.user;

import lombok.Data;

@Data
public class RegisterDto {

    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String confirmacaoSenha;
    private String telefone;
    private String tipoTelefone;

    // Etapa 2
    private String cep;
    private String numero;
    private String rua;
    private String bairro;
    private String cidade;
    private String complemento;
    private String estado;
}
