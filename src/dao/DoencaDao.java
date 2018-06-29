package dao;

import java.sql.SQLException;

import entidades.Paciente;

public class DoencaDao extends Dao {

  public DoencaDao(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Paciente paciente, String doenca) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO DOENÇAS_CRONICAS (");
      buffer.append("cpfPacienteDoenca, doenca");
      buffer.append(") VALUES (");
      buffer.append(formatarParaStringSql(paciente.getCpf()) + ", ");
      buffer.append(formatarParaStringSql(doenca));
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
      buffer.append(formatarParaStringSql(paciente.getCpf()));
      buffer.append(" AND doenca= ");
      buffer.append(formatarParaStringSql(doenca));
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
      buffer.append(formatarParaStringSql(paciente.getCpf()));
      buffer.append(", doenca= ");
      buffer.append(formatarParaStringSql(dModificado));
      buffer.append(" WHERE cpfPacienteDoenca=" + formatarParaStringSql(paciente.getCpf()));
      buffer.append(" AND doenca= ");
      buffer.append(formatarParaStringSql(dAnterior));
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
