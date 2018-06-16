package entidades;

public class Servico {
  private String preco;
  private String estaPago;
  private String descricao;
  
  public Servico() {
    super();
  }

  public Servico(String preco, String estaPago, String descricao) {
    this.preco = preco;
    this.estaPago = estaPago;
    this.descricao = descricao;
  }

  public String getPreco() {
    return preco;
  }

  public void setPreco(String preco) {
    this.preco = preco;
  }

  public String getEstaPago() {
    return estaPago;
  }

  public void setEstaPago(String estaPago) {
    this.estaPago = estaPago;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
  
}
