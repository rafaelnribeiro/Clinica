package entidades;

import java.sql.Date;

public class Pessoa {
  private String cpf;
  private String nome;
  private Date dataNascimento;
  private String telefone;
  private String email;
  private String endereco;
  private String sexo;
  private String senha;
  
  public Pessoa() {
    super();
  }

  public Pessoa(String cpf, String nome, Date data, String telefone, 
      String email, String endereco, String sexo, String senha) {
    this.cpf = cpf;
    this.nome = nome;
    this.dataNascimento = data;
    this.telefone = telefone;
    this.email = email;
    this.endereco = endereco;
    this.sexo = sexo;
    this.senha = senha;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Date getData() {
    return dataNascimento;
  }

  public void setData(Date data) {
    this.dataNascimento = data;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEndereco() {
    return endereco;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }  
  
}
