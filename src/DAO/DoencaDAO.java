package DAO;

import java.sql.SQLException;

import entidades.Paciente;

public class DoencaDAO extends DAO {

  public DoencaDAO(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Paciente paciente, String doenca) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO DOENÇAS_CRONICAS (");
      buffer.append("cpfPacienteDoenca, doenca");
      buffer.append(") VALUES (");
      buffer.append(retornarValorStringBD(paciente.getCpf()) + ", ");
      buffer.append(retornarValorStringBD(doenca));
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

  public void remove(Paciente paciente, String doenca) {
    try {
      conectar();
      
      StringBuffer buffer = new StringBuffer();
      buffer.append("DELETE FROM DOENÇAS_CRONICAS WHERE cpfPacienteDoenca= ");
      buffer.append(retornarValorStringBD(paciente.getCpf()));
      buffer.append(" AND doenca= ");
      buffer.append(retornarValorStringBD(doenca));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void update(Paciente paciente, String dAnterior, String dModificado) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE DOENÇAS_CRONICAS SET ");
      buffer.append("cpfPacienteDoenca= ");
      buffer.append(retornarValorStringBD(paciente.getCpf()));
      buffer.append(", doenca= ");
      buffer.append(retornarValorStringBD(dModificado));
      buffer.append(" WHERE cpfPacienteDoenca=" + retornarValorStringBD(paciente.getCpf()));
      buffer.append(" AND doenca= ");
      buffer.append(retornarValorStringBD(dAnterior));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
