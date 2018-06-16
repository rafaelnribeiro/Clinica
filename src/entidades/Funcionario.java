package entidades;

import java.sql.Date;

public class Funcionario extends Pessoa{
  
  private int codFuncionario;

  public Funcionario(String cpf, String nome, Date data, String telefone, String email, String endereco, String sexo,
      String senha, int codFuncionario) {
    super(cpf, nome, data, telefone, email, endereco, sexo, senha);
    this.codFuncionario = codFuncionario;
  }

  public int getCodFuncionario() {
    return codFuncionario;
  }

  public void setCodFuncionario(int codFuncionario) {
    this.codFuncionario = codFuncionario;
  }
  
}
