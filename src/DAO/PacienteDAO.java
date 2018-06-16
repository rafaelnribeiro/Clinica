package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entidades.Paciente;
import entidades.Prontuario;

public class PacienteDAO extends DAO {

  public PacienteDAO(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Paciente paciente) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO pessoa (");
      buffer.append("cpf, nome, dataNascimento, telefone, email, endereco, sexo, senha");
      buffer.append(") VALUES (");
      buffer.append(retornarValorBDPessoa(paciente));
      buffer.append(");");
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      buffer.setLength(0);
      buffer.append("INSERT INTO paciente (");
      buffer.append("cpfPaciente, tipoSanguineo, peso, altura");
      buffer.append(") VALUES (");
      buffer.append(retornarValorBDPaciente(paciente));
      buffer.append(");");
      sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void remove(Paciente paciente) {
    try {
      conectar();

      String sql = ("DELETE FROM PESSOA WHERE cpf= " + retornarValorStringBD(paciente.getCpf()));
      comando.executeUpdate(sql);

      fechar();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void updatePaciente(Paciente paciente) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE PESSOA SET ");
      buffer.append(retornarCamposBDPessoa(paciente));
      buffer.append(" WHERE cpf=" + retornarValorStringBD(paciente.getCpf()));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      buffer.setLength(0);
      buffer.append("UPDATE PACIENTE SET ");
      buffer.append(retornarCamposBDPaciente(paciente));
      buffer.append(" WHERE cpfPaciente=" + retornarValorStringBD(paciente.getCpf()));
      sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public Paciente search(String cpf) {
    try {
      conectar();
      
      String sql = "SELECT * FROM PESSOA WHERE cpf=" + retornarValorStringBD(cpf);
      ResultSet rs = comando.executeQuery(sql);
      
      Paciente paciente = new Paciente();
      if (rs.next()) {        
        paciente.setCpf(rs.getString("cpf"));
        paciente.setNome(rs.getString("nome"));
        paciente.setData(rs.getDate("dataNascimento"));
        paciente.setTelefone(rs.getString("telefone"));
        paciente.setEmail(rs.getString("email"));
        paciente.setEndereco(rs.getString("endereco"));
        paciente.setSexo(rs.getString("sexo"));
        paciente.setSenha(rs.getString("senha"));
      }
      
      sql = "SELECT * FROM PACIENTE WHERE cpfPaciente=" + retornarValorStringBD(cpf);
      rs = comando.executeQuery(sql);
      
      if (rs.next()) {        
        paciente.setTipoSanguineo(rs.getString("tipoSanguineo"));
        paciente.setPeso(rs.getDouble("peso"));
        paciente.setAltura(rs.getDouble("altura"));
      }
      
      sql = "SELECT medicamento FROM ALERGIA_MEDICAMENTOS WHERE cpfPacienteAlergia=" + retornarValorStringBD(cpf);
      rs = comando.executeQuery(sql);
      while(rs.next()) {
        paciente.getMedicamentos().add(rs.getString("medicamento"));
      }
      
      sql = "SELECT doenca FROM DOENÇAS_CRONICAS WHERE cpfPacienteDoenca=" + retornarValorStringBD(cpf);
      rs = comando.executeQuery(sql);
      while(rs.next()) {
        paciente.getDoencasCronicas().add(rs.getString("doenca"));
      }
      
      sql = "SELECT data, hora, ficha FROM PAC_PRONTUARIO WHERE cpfPacienteProntuario=" + retornarValorStringBD(cpf);
      rs = comando.executeQuery(sql);
      while(rs.next()) {
        Prontuario prontuario = new Prontuario();
        prontuario.setData(rs.getDate("data"));
        prontuario.setHora(rs.getTime("hora"));
        prontuario.setFicha(rs.getString("ficha"));       
        paciente.getProntuario().add(prontuario);
      }
      
      fechar();
      
      return paciente;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e){
      e.printStackTrace();
    }
    return null;
  }
  
  public List<Paciente> retrievePacientes() {
    try {
      conectar();
      
      String sql = "SELECT * FROM PACIENTE";
      ResultSet rs = comando.executeQuery(sql);
      
      List<Paciente> pacientes = new ArrayList<Paciente>();
      while (rs.next()) {      
        Paciente paciente = new Paciente();
        paciente.setCpf(rs.getString("cpfPaciente"));
        paciente.setTipoSanguineo(rs.getString("tipoSanguineo"));
        paciente.setPeso(rs.getDouble("peso"));
        paciente.setAltura(rs.getDouble("altura"));

        
        pacientes.add(paciente);
      }
      
      for (Iterator<Paciente> iterator = pacientes.iterator(); iterator.hasNext();) {
        Paciente paciente = (Paciente) iterator.next();
        
        
        sql = "SELECT * FROM PESSOA WHERE cpf="
            + retornarValorStringBD(paciente.getCpf());
        rs = comando.executeQuery(sql);
        if (rs.next()) {
          paciente.setNome(rs.getString("nome"));
          paciente.setData(rs.getDate("dataNascimento"));
          paciente.setTelefone(rs.getString("telefone"));
          paciente.setEmail(rs.getString("email"));
          paciente.setEndereco(rs.getString("endereco"));
          paciente.setSexo(rs.getString("sexo"));
          paciente.setSenha(rs.getString("senha"));
        }        
        
        sql = "SELECT medicamento FROM ALERGIA_MEDICAMENTOS WHERE cpfPacienteAlergia="
            + retornarValorStringBD(paciente.getCpf());
        rs = comando.executeQuery(sql);
        while(rs.next()) {
          paciente.getMedicamentos().add(rs.getString("medicamento"));
        }
        
        sql = "SELECT doenca FROM DOENÇAS_CRONICAS WHERE cpfPacienteDoenca="
            + retornarValorStringBD(paciente.getCpf());
        rs = comando.executeQuery(sql);
        while(rs.next()) {
          paciente.getDoencasCronicas().add(rs.getString("doenca"));
        }
        
        sql = "SELECT data, hora, ficha FROM PAC_PRONTUARIO WHERE cpfPacienteProntuario="
            + retornarValorStringBD(paciente.getCpf());
        rs = comando.executeQuery(sql);
        
        if(rs.next()) {
          Prontuario prontuario = new Prontuario();
          prontuario.setData(rs.getDate("data"));
          prontuario.setHora(rs.getTime("hora"));
          prontuario.setFicha(rs.getString("ficha"));       
          paciente.getProntuario().add(prontuario);
        } 
        
      }
     
      fechar();
      
      return pacientes;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e){
      e.printStackTrace();
    }
    return null;
  }
  
  private String retornarValorBDPaciente(Paciente paciente) {
    return retornarValorStringBD(paciente.getCpf()) + ", " + retornarValorStringBD(paciente.getTipoSanguineo()) + ", "
        + retornarValorStringBD(Double.toString(paciente.getPeso())) + ", "
        + retornarValorStringBD(Double.toString(paciente.getAltura()));
  }

  private String retornarValorBDPessoa(Paciente paciente) {
    return retornarValorStringBD(paciente.getCpf()) + ", " + retornarValorStringBD(paciente.getNome()) + ", "
        + retornarValorStringBD(paciente.getData().toString()) + ", " + retornarValorStringBD(paciente.getTelefone())
        + ", " + retornarValorStringBD(paciente.getEmail()) + ", " + retornarValorStringBD(paciente.getEndereco())
        + ", " + retornarValorStringBD(paciente.getSexo()) + ", " + retornarValorStringBD(paciente.getSenha());
  }

  private String retornarCamposBDPessoa(Paciente paciente) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("cpf= ");
    buffer.append(retornarValorStringBD(paciente.getCpf()));
    buffer.append(", nome= ");
    buffer.append(retornarValorStringBD(paciente.getNome()));
    buffer.append(", dataNascimento= ");
    buffer.append(retornarValorStringBD(paciente.getData().toString()));
    buffer.append(", telefone= ");
    buffer.append(retornarValorStringBD(paciente.getTelefone()));
    buffer.append(", email= ");
    buffer.append(retornarValorStringBD(paciente.getEmail()));
    buffer.append(", endereco= ");
    buffer.append(retornarValorStringBD(paciente.getEndereco()));
    buffer.append(", sexo= ");
    buffer.append(retornarValorStringBD(paciente.getSexo()));
    buffer.append(", senha= ");
    buffer.append(retornarValorStringBD(paciente.getSenha()));

    return buffer.toString();

  }

  private String retornarCamposBDPaciente(Paciente paciente) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("cpfPaciente= ");
    buffer.append(retornarValorStringBD(paciente.getCpf()));
    buffer.append(", tipoSanguineo= ");
    buffer.append(retornarValorStringBD(paciente.getTipoSanguineo()));
    buffer.append(", peso= ");
    buffer.append(Double.toString(paciente.getPeso()));
    buffer.append(", altura= ");
    buffer.append(Double.toString(paciente.getAltura()));

    return buffer.toString();

  }

}
