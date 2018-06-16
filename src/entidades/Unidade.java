package entidades;

public class Unidade {
  
  private int numUnidade;
  private String nome;
  private String endereco;
  
  public Unidade(int numUnidade, String nome, String endereco) {
    super();
    this.numUnidade = numUnidade;
    this.nome = nome;
    this.endereco = endereco;
  }

  public int getNumUnidade() {
    return numUnidade;
  }

  public void setNumUnidade(int numUnidade) {
    this.numUnidade = numUnidade;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEndereco() {
    return endereco;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }
  
}
