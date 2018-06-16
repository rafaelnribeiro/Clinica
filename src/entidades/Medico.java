package entidades;

import java.sql.Date;
import java.util.ArrayList;

public class Medico extends Pessoa{
  
  private String crm;
  private int numUnidade;
  private ArrayList<String> especialidades;
  private ArrayList<Horario> horarios;
  
  public Medico() {
    this.especialidades = new ArrayList<String>();
    this.horarios = new ArrayList<Horario>();
  }

  public Medico(String cpf, String nome, Date data, String telefone, String email, String endereco, String sexo,
      String senha, String crm, int numUnidade, ArrayList<String> especialidades, ArrayList<Horario> horarios) {
    super(cpf, nome, data, telefone, email, endereco, sexo, senha);
    this.crm = crm;
    this.numUnidade = numUnidade;
    this.especialidades = especialidades;
    this.horarios = horarios;
  }

  public String getCrm() {
    return crm;
  }

  public void setCrm(String crm) {
    this.crm = crm;
  }

  public int getNumUnidade() {
    return numUnidade;
  }

  public void setNumUnidade(int numUnidade) {
    this.numUnidade = numUnidade;
  }

  public ArrayList<String> getEspecialidades() {
    return especialidades;
  }

  public void setEspecialidades(ArrayList<String> especialidades) {
    this.especialidades = especialidades;
  }

  public ArrayList<Horario> getHorarios() {
    return horarios;
  }

  public void setHorarios(ArrayList<Horario> horarios) {
    this.horarios = horarios;
  }
    
}
