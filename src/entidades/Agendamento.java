package entidades;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Agendamento {
  private Paciente paciente;
  private Medico medico;
  private Date data;
  private Time hora;
  private String status;
  private String comentario;
  private ArrayList<Servico> servicos;
  
  public Agendamento() {
    this.servicos = new ArrayList<Servico>();
  }

  public Agendamento(Paciente paciente, Medico medico, Date data, Time hora,
      String status, String comentario, ArrayList<Servico> servicos) {
    this.paciente = paciente;
    this.medico = medico;
    this.data = data;
    this.hora = hora;
    this.status = status;
    this.comentario = comentario;
    this.servicos = servicos;
  }

  public Paciente getPaciente() {
    return paciente;
  }

  public void setPaciente(Paciente paciente) {
    this.paciente = paciente;
  }

  public Medico getMedico() {
    return medico;
  }

  public void setMedico(Medico medico) {
    this.medico = medico;
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getComentario() {
    return comentario;
  }

  public void setComentario(String comentario) {
    this.comentario = comentario;
  }

  public ArrayList<Servico> getServicos() {
    return servicos;
  }

  public void setServicos(ArrayList<Servico> servicos) {
    this.servicos = servicos;
  }  
  
}
