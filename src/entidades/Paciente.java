package entidades;

import java.sql.Date;
import java.util.ArrayList;

public class Paciente extends Pessoa{
  
  private String tipoSanguineo;
  private double peso;
  private double altura;
  private ArrayList<String> alergiaMedicamentos;
  private ArrayList<String> doencasCronicas;
  private ArrayList<Prontuario> prontuario;
  
  public Paciente() {
    this.alergiaMedicamentos = new ArrayList<String>();
    this.doencasCronicas = new ArrayList<String>();
    this.prontuario = new ArrayList<Prontuario>();
  }

  public Paciente(String cpf, String nome, Date data, String telefone, String email,
      String endereco, String sexo, String senha, String tipoSanguineo,
      double peso, double altura) {
    super(cpf, nome, data, telefone, email, endereco, sexo, senha);
    this.tipoSanguineo = tipoSanguineo;
    this.peso = peso;
    this.altura = altura;
    this.alergiaMedicamentos = new ArrayList<String>();
    this.doencasCronicas = new ArrayList<String>();
    this.prontuario = new ArrayList<Prontuario>();
  }

  public String getTipoSanguineo() {
    return tipoSanguineo;
  }

  public void setTipoSanguineo(String tipoSanguineo) {
    this.tipoSanguineo = tipoSanguineo;
  }

  public double getPeso() {
    return peso;
  }

  public void setPeso(double peso) {
    this.peso = peso;
  }

  public double getAltura() {
    return altura;
  }

  public void setAltura(double altura) {
    this.altura = altura;
  }

  public ArrayList<String> getMedicamentos() {
    return alergiaMedicamentos;
  }

  public void setMedicamentos(ArrayList<String> medicamentos) {
    this.alergiaMedicamentos = medicamentos;
  }

  public ArrayList<String> getDoencasCronicas() {
    return doencasCronicas;
  }

  public void setDoencasCronicas(ArrayList<String> doencasCronicas) {
    this.doencasCronicas = doencasCronicas;
  }

  public ArrayList<Prontuario> getProntuario() {
    return prontuario;
  }

  public void setProntuario(ArrayList<Prontuario> prontuario) {
    this.prontuario = prontuario;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Paciente)) {
      return false;
    }
    
    Paciente pac = (Paciente)obj;
    return
        this.getCpf().equals(pac.getCpf())
        && this.getNome().equals(pac.getNome())
        && this.getData().equals(pac.getData())
        && this.getTelefone().equals(pac.getTelefone())
        && this.getEmail().equals(pac.getEmail())
        && this.getEndereco().equals(pac.getEndereco())
        && this.getSexo().equals(pac.getSexo())
        && this.getSenha().equals(pac.getSenha())
        && this.getTipoSanguineo().equals(pac.getTipoSanguineo())
        && (this.getPeso() == pac.getPeso())
        && (this.getAltura() == pac.getAltura());
  }
  
  
  
}
