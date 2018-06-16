package entidades;

import java.sql.Date;
import java.sql.Time;

public class Horario {
  private Time horaInicio;
  private Time horaFim;
  private Date dataInicio;
  private Date dataFim;
  
  public Horario() {
    super();
  }

  public Horario(Time horaInicio, Time horaFim, Date dataInicio, Date dataFim) {
    this.horaInicio = horaInicio;
    this.horaFim = horaFim;
    this.dataInicio = dataInicio;
    this.dataFim = dataFim;
  }

  public Time getHoraInicio() {
    return horaInicio;
  }

  public void setHoraInicio(Time horaInicio) {
    this.horaInicio = horaInicio;
  }

  public Time getHoraFim() {
    return horaFim;
  }

  public void setHoraFim(Time horaFim) {
    this.horaFim = horaFim;
  }

  public Date getDataInicio() {
    return dataInicio;
  }

  public void setDataInicio(Date dataInicio) {
    this.dataInicio = dataInicio;
  }

  public Date getDataFim() {
    return dataFim;
  }

  public void setDataFim(Date dataFim) {
    this.dataFim = dataFim;
  }
  
}
