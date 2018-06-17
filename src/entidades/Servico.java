package entidades;

import java.sql.Date;
import java.sql.Time;

public class Servico {
  private int idServico;
  private Date dataServico;
  private Time horaServico;
  private double preco;
  private String descricao;
  private int estaPago;

  
  public Servico() {
    super();
  }


  public Servico(int idServico, Date dataServico, Time horaServico, double preco, String descricao,
      int estaPago) {
    this.idServico = idServico;
    this.dataServico = dataServico;
    this.horaServico = horaServico;
    this.preco = preco;
    this.descricao = descricao;
    this.estaPago = estaPago;
  }


  public int getIdServico() {
    return idServico;
  }


  public void setIdServico(int idServico) {
    this.idServico = idServico;
  }

  public Date getDataServico() {
    return dataServico;
  }


  public void setDataServico(Date dataServico) {
    this.dataServico = dataServico;
  }


  public Time getHoraServico() {
    return horaServico;
  }


  public void setHoraServico(Time horaServico) {
    this.horaServico = horaServico;
  }


  public double getPreco() {
    return preco;
  }


  public void setPreco(double preco) {
    this.preco = preco;
  }


  public String getDescricao() {
    return descricao;
  }


  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }


  public int getEstaPago() {
    return estaPago;
  }


  public void setEstaPago(int estaPago) {
    this.estaPago = estaPago;
  }

  
}
