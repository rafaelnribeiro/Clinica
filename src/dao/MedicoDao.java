package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entidades.Horario;
import entidades.Medico;

public class MedicoDao extends Dao {

  public MedicoDao(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Medico medico) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO pessoa (");
      buffer.append("cpf, nome, dataNascimento, telefone, email, endereco, sexo, senha");
      buffer.append(") VALUES (");
      buffer.append(retornarValorBDPessoa(medico));
      buffer.append(");");
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      buffer.setLength(0);
      buffer.append("INSERT INTO medico (");
      buffer.append("cpfMedico, crm, numUnidadeMedico");
      buffer.append(") VALUES (");
      buffer.append(retornarValorBDMedico(medico));
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

  public void remove(Medico medico) {
    try {
      conectar();

      String sql = ("DELETE FROM PESSOA WHERE cpf= " + formatarParaStringSql(medico.getCpf()));
      comando.executeUpdate(sql);

      fechar();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void updateMedico(Medico medico) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE PESSOA SET ");
      buffer.append(retornarCamposBDPessoa(medico));
      buffer.append(" WHERE cpf=" + formatarParaStringSql(medico.getCpf()));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      buffer.setLength(0);
      buffer.append("UPDATE MEDICO SET ");
      buffer.append(retornarCamposBDMedico(medico));
      buffer.append(" WHERE cpfMedico=" + formatarParaStringSql(medico.getCpf()));
      sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public Medico search(String cpf) {
    try {
      conectar();
      
      String sql = "SELECT * FROM PESSOA WHERE cpf=" + formatarParaStringSql(cpf);
      ResultSet rs = comando.executeQuery(sql);
      
      Medico medico = new Medico();
      if (rs.next()) {        
        medico.setCpf(rs.getString("cpf"));
        medico.setNome(rs.getString("nome"));
        medico.setData(rs.getDate("dataNascimento"));
        medico.setTelefone(rs.getString("telefone"));
        medico.setEmail(rs.getString("email"));
        medico.setEndereco(rs.getString("endereco"));
        medico.setSexo(rs.getString("sexo"));
        medico.setSenha(rs.getString("senha"));
      }
      
      sql = "SELECT * FROM MEDICO WHERE cpfMedico=" + formatarParaStringSql(cpf);
      rs = comando.executeQuery(sql);
      
      if (rs.next()) {        
        medico.setCrm(rs.getString("crm"));
        medico.setNumUnidade(rs.getInt("numUnidadeMedico"));
      }
      
      sql = "SELECT especialidade FROM MED_ESPECIALIDADE WHERE cpfEspecialidade="
          + formatarParaStringSql(cpf);
      rs = comando.executeQuery(sql);
      while(rs.next()) {
        medico.getEspecialidades().add(rs.getString("medicamento"));
      }
            
      sql = "SELECT id_horario, horaInicio, horaFim, dataInicio, dataFim"
          + " FROM MED_HORARIO WHERE cpfMedicoHorario=" + formatarParaStringSql(cpf);
      rs = comando.executeQuery(sql);
      while(rs.next()) {
        Horario horario = new Horario();
        horario.setIdHorario(rs.getInt("id_horario"));
        horario.setHoraInicio(rs.getTime("horaInicio"));
        horario.setHoraFim(rs.getTime("horaFim"));
        horario.setDataInicio(rs.getDate("dataInicio"));
        horario.setDataFim(rs.getDate("dataFim"));
        
        medico.getHorarios().add(horario);
      }
      
      fechar();
      
      return medico;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e){
      e.printStackTrace();
    }
    return null;
  }
  
  public List<Medico> retrieveMedicos() {
    try {
      conectar();
      
      String sql = "SELECT * FROM MEDICO";
      ResultSet rs = comando.executeQuery(sql);
      
      List<Medico> medicos = new ArrayList<Medico>();
      while (rs.next()) {      
        Medico medico = new Medico();
        medico.setCpf(rs.getString("cpfMedico"));
        medico.setCrm(rs.getString("crm"));
        medico.setNumUnidade(rs.getInt("numUnidadeMedico")); 
        
        medicos.add(medico);
      }
      
      for (Iterator<Medico> iterator = medicos.iterator(); iterator.hasNext();) {
        Medico medico = (Medico) iterator.next();
        
        
        sql = "SELECT * FROM PESSOA WHERE cpf="
            + formatarParaStringSql(medico.getCpf());
        rs = comando.executeQuery(sql);
        if (rs.next()) {
          medico.setNome(rs.getString("nome"));
          medico.setData(rs.getDate("dataNascimento"));
          medico.setTelefone(rs.getString("telefone"));
          medico.setEmail(rs.getString("email"));
          medico.setEndereco(rs.getString("endereco"));
          medico.setSexo(rs.getString("sexo"));
          medico.setSenha(rs.getString("senha"));
        }      
        
        sql = "SELECT especialidade FROM MED_ESPECIALIDADE WHERE cpfEspecialidade="
            + formatarParaStringSql(medico.getCpf());
        rs = comando.executeQuery(sql);
        while(rs.next()) {
          medico.getEspecialidades().add(rs.getString("medicamento"));
        }
              
        sql = "SELECT id_horario, horaInicio, horaFim, dataInicio, dataFim"
            + " FROM MED_HORARIO WHERE cpfMedicoHorario=" + formatarParaStringSql(medico.getCpf());
        rs = comando.executeQuery(sql);
        while(rs.next()) {
          Horario horario = new Horario();
          horario.setIdHorario(rs.getInt("id_horario"));
          horario.setHoraInicio(rs.getTime("horaInicio"));
          horario.setHoraFim(rs.getTime("horaFim"));
          horario.setDataInicio(rs.getDate("dataInicio"));
          horario.setDataFim(rs.getDate("dataFim"));
          
          medico.getHorarios().add(horario);
        } 
        
      }   
      fechar();
      
      return medicos;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e){
      e.printStackTrace();
    }
    return null;
  }
  
  private String retornarValorBDMedico(Medico medico) {
    return 
        formatarParaStringSql(medico.getCpf()) + ", "
        + formatarParaStringSql(medico.getCrm()) + ", "
        + formatarParaStringSql(Integer.toString(medico.getNumUnidade()));
  }

  private String retornarValorBDPessoa(Medico medico) {
    return formatarParaStringSql(medico.getCpf()) + ", " + formatarParaStringSql(medico.getNome()) + ", "
        + formatarParaStringSql(medico.getData().toString()) + ", " + formatarParaStringSql(medico.getTelefone())
        + ", " + formatarParaStringSql(medico.getEmail()) + ", " + formatarParaStringSql(medico.getEndereco())
        + ", " + formatarParaStringSql(medico.getSexo()) + ", " + formatarParaStringSql(medico.getSenha());
  }

  private String retornarCamposBDPessoa(Medico medico) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("cpf= ");
    buffer.append(formatarParaStringSql(medico.getCpf()));
    buffer.append(", nome= ");
    buffer.append(formatarParaStringSql(medico.getNome()));
    buffer.append(", dataNascimento= ");
    buffer.append(formatarParaStringSql(medico.getData().toString()));
    buffer.append(", telefone= ");
    buffer.append(formatarParaStringSql(medico.getTelefone()));
    buffer.append(", email= ");
    buffer.append(formatarParaStringSql(medico.getEmail()));
    buffer.append(", endereco= ");
    buffer.append(formatarParaStringSql(medico.getEndereco()));
    buffer.append(", sexo= ");
    buffer.append(formatarParaStringSql(medico.getSexo()));
    buffer.append(", senha= ");
    buffer.append(formatarParaStringSql(medico.getSenha()));

    return buffer.toString();

  }

  private String retornarCamposBDMedico(Medico medico) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("cpfMedico= ");
    buffer.append(formatarParaStringSql(medico.getCpf()));
    buffer.append(", crm= ");
    buffer.append(formatarParaStringSql(medico.getCrm()));
    buffer.append(", numUnidadeMedico= ");
    buffer.append(Integer.toString(medico.getNumUnidade()));

    return buffer.toString();

  }

}