package entidades;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Agendamento {
  private int idAgendamento;
  private String cpfPaciente;
  private String cpfMedico;
  private Date data;
  private Time hora;
  private String status;
  private String comentario;
  private List<Servico> servicos;
  
  public Agendamento() {
    this.servicos = new ArrayList<Servico>();
  }

  public Agendamento(int idAgendamento, String cpfPaciente, String cpfMedico, Date data, Time hora, String status,
      String comentario) {
    this.setIdAgendamento(idAgendamento);
    this.setCpfPaciente(cpfPaciente);
    this.setCpfMedico(cpfMedico);
    this.data = data;
    this.hora = hora;
    this.status = status;
    this.comentario = comentario;
    this.servicos = new ArrayList<Servico>();
  }

  public int getIdAgendamento() {
    return idAgendamento;
  }

  public void setIdAgendamento(int idAgendamento) {
    this.idAgendamento = idAgendamento;
  }

  public String getCpfPaciente() {
    return cpfPaciente;
  }

  public void setCpfPaciente(String cpfPaciente) {
    this.cpfPaciente = cpfPaciente;
  }

  public String getCpfMedico() {
    return cpfMedico;
  }

  public void setCpfMedico(String cpfMedico) {
    this.cpfMedico = cpfMedico;
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

  public List<Servico> getServicos() {
    return servicos;
  }

  public void setServicos(List<Servico> servicos) {
    this.servicos = servicos;
  }  
  
}
