package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entidades.Paciente;
import entidades.Prontuario;

public class PacienteDao extends Dao {

  public PacienteDao(String server, String user, String password, int banco) {
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

      String sql = ("DELETE FROM PESSOA WHERE cpf= " + formatarParaStringSql(paciente.getCpf()));
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
      buffer.append(" WHERE cpf=" + formatarParaStringSql(paciente.getCpf()));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      buffer.setLength(0);
      buffer.append("UPDATE PACIENTE SET ");
      buffer.append(retornarCamposBDPaciente(paciente));
      buffer.append(" WHERE cpfPaciente=" + formatarParaStringSql(paciente.getCpf()));
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
      
      String sql = "SELECT * FROM PESSOA WHERE cpf=" + formatarParaStringSql(cpf);
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
      
      sql = "SELECT * FROM PACIENTE WHERE cpfPaciente=" + formatarParaStringSql(cpf);
      rs = comando.executeQuery(sql);
      
      if (rs.next()) {        
        paciente.setTipoSanguineo(rs.getString("tipoSanguineo"));
        paciente.setPeso(rs.getDouble("peso"));
        paciente.setAltura(rs.getDouble("altura"));
      }
      
      sql = "SELECT medicamento FROM ALERGIA_MEDICAMENTOS WHERE cpfPacienteAlergia=" + formatarParaStringSql(cpf);
      rs = comando.executeQuery(sql);
      while(rs.next()) {
        paciente.getMedicamentos().add(rs.getString("medicamento"));
      }
      
      sql = "SELECT doenca FROM DOENÇAS_CRONICAS WHERE cpfPacienteDoenca=" + formatarParaStringSql(cpf);
      rs = comando.executeQuery(sql);
      while(rs.next()) {
        paciente.getDoencasCronicas().add(rs.getString("doenca"));
      }
      
      sql = "SELECT id_prontuario, data, hora, ficha FROM PAC_PRONTUARIO WHERE cpfPacienteProntuario=" + formatarParaStringSql(cpf);
      rs = comando.executeQuery(sql);
      while(rs.next()) {
        Prontuario prontuario = new Prontuario();
        prontuario.setIdProntuario(rs.getInt("id_prontuario"));
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
            + formatarParaStringSql(paciente.getCpf());
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
            + formatarParaStringSql(paciente.getCpf());
        rs = comando.executeQuery(sql);
        while(rs.next()) {
          paciente.getMedicamentos().add(rs.getString("medicamento"));
        }
        
        sql = "SELECT doenca FROM DOENÇAS_CRONICAS WHERE cpfPacienteDoenca="
            + formatarParaStringSql(paciente.getCpf());
        rs = comando.executeQuery(sql);
        while(rs.next()) {
          paciente.getDoencasCronicas().add(rs.getString("doenca"));
        }
        
        sql = "SELECT id_prontuario data, hora, ficha FROM PAC_PRONTUARIO WHERE cpfPacienteProntuario="
            + formatarParaStringSql(paciente.getCpf());
        rs = comando.executeQuery(sql);
        
        while(rs.next()) {
          Prontuario prontuario = new Prontuario();
          prontuario.setIdProntuario(rs.getInt("id_prontuario"));
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
    return formatarParaStringSql(paciente.getCpf()) + ", " + formatarParaStringSql(paciente.getTipoSanguineo()) + ", "
        + formatarParaStringSql(Double.toString(paciente.getPeso())) + ", "
        + formatarParaStringSql(Double.toString(paciente.getAltura()));
  }

  private String retornarValorBDPessoa(Paciente paciente) {
    return formatarParaStringSql(paciente.getCpf()) + ", " + formatarParaStringSql(paciente.getNome()) + ", "
        + formatarParaStringSql(paciente.getData().toString()) + ", " + formatarParaStringSql(paciente.getTelefone())
        + ", " + formatarParaStringSql(paciente.getEmail()) + ", " + formatarParaStringSql(paciente.getEndereco())
        + ", " + formatarParaStringSql(paciente.getSexo()) + ", " + formatarParaStringSql(paciente.getSenha());
  }

  private String retornarCamposBDPessoa(Paciente paciente) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("cpf= ");
    buffer.append(formatarParaStringSql(paciente.getCpf()));
    buffer.append(", nome= ");
    buffer.append(formatarParaStringSql(paciente.getNome()));
    buffer.append(", dataNascimento= ");
    buffer.append(formatarParaStringSql(paciente.getData().toString()));
    buffer.append(", telefone= ");
    buffer.append(formatarParaStringSql(paciente.getTelefone()));
    buffer.append(", email= ");
    buffer.append(formatarParaStringSql(paciente.getEmail()));
    buffer.append(", endereco= ");
    buffer.append(formatarParaStringSql(paciente.getEndereco()));
    buffer.append(", sexo= ");
    buffer.append(formatarParaStringSql(paciente.getSexo()));
    buffer.append(", senha= ");
    buffer.append(formatarParaStringSql(paciente.getSenha()));

    return buffer.toString();

  }

  private String retornarCamposBDPaciente(Paciente paciente) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("cpfPaciente= ");
    buffer.append(formatarParaStringSql(paciente.getCpf()));
    buffer.append(", tipoSanguineo= ");
    buffer.append(formatarParaStringSql(paciente.getTipoSanguineo()));
    buffer.append(", peso= ");
    buffer.append(Double.toString(paciente.getPeso()));
    buffer.append(", altura= ");
    buffer.append(Double.toString(paciente.getAltura()));

    return buffer.toString();

  }

}
