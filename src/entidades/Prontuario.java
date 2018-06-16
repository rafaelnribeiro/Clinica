package entidades;

import java.sql.Date;
import java.sql.Time;

public class Prontuario {
  private Date data;
  private Time hora;
  private String ficha;
  
  public Prontuario() {
    super();
  }
  
  public Prontuario(Date data, Time hora, String ficha) {
    this.data = data;
    this.hora = hora;
    this.ficha = ficha;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public Time getHora() {
    return hora;
  }

  public void setHora(Time hora) {
    this.hora = hora;
  }

  public String getFicha() {
    return ficha;
  }

  public void setFicha(String ficha) {
    this.ficha = ficha;
  }
  
}
