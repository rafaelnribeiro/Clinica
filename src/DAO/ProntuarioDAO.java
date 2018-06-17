package DAO;

import java.sql.SQLException;

import entidades.Prontuario;
import entidades.Medico;
import entidades.Paciente;

public class ProntuarioDAO extends DAO {

  public ProntuarioDAO(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Paciente paciente, Prontuario prontuario) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO PAC_PRONTUARIO (");
      buffer.append("cpfPacienteProntuario, id_prontuario, hora, data, ficha");
      buffer.append(") VALUES (");
      buffer.append(retornarValorStringBD(paciente.getCpf()) + ", ");
      buffer.append(retornarValorBD(prontuario));
      buffer.append(");");
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void remove(Paciente paciente, Prontuario prontuario) {
    try {
      conectar();
      
      StringBuffer buffer = new StringBuffer();
      buffer.append("DELETE FROM PAC_PRONTUARIO WHERE cpfPacienteProntuario= ");
      buffer.append(retornarValorStringBD(paciente.getCpf()));
      buffer.append(" AND id_prontuario= ");
      buffer.append(retornarValorStringBD(Integer.toString(prontuario.getIdProntuario())));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void update(Paciente paciente, Prontuario prontuario) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE PAC_PRONTUARIO SET ");
      buffer.append("cpfPacienteProntuario= ");
      buffer.append(retornarValorStringBD(paciente.getCpf()) + ", ");
      buffer.append(retornarCamposBD(prontuario));
      buffer.append(" WHERE cpfPacienteProntuario=" + retornarValorStringBD(paciente.getCpf()));
      buffer.append(" AND id_Prontuario= ");
      buffer.append(Integer.toString(prontuario.getIdProntuario()));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  private String retornarValorBD(Prontuario prontuario) {
    return 
        retornarValorStringBD(Integer.toString(prontuario.getIdProntuario())) + ", "
        + retornarValorStringBD(prontuario.getHora().toString()) + ", "
        + retornarValorStringBD(prontuario.getData().toString()) + ", "
        + retornarValorStringBD(prontuario.getFicha());
  }
  
  private String retornarCamposBD(Prontuario prontuario) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("id_prontuario= ");
    buffer.append(Integer.toString(prontuario.getIdProntuario()));
    buffer.append(", hora= ");
    buffer.append(retornarValorStringBD(prontuario.getHora().toString()));
    buffer.append(", data= ");
    buffer.append(retornarValorStringBD(prontuario.getData().toString()));
    buffer.append(", ficha= ");
    buffer.append(retornarValorStringBD(prontuario.getFicha()));
    
    return buffer.toString();

  }
}
