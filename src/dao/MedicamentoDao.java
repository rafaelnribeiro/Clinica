package dao;

import java.sql.SQLException;

import entidades.Paciente;

public class MedicamentoDao extends Dao {

  public MedicamentoDao(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Paciente paciente, String medicamento) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO ALERGIA_MEDICAMENTOS (");
      buffer.append("cpfPacienteAlergia, medicamento");
      buffer.append(") VALUES (");
      buffer.append(formatarParaStringSql(paciente.getCpf()) + ", ");
      buffer.append(formatarParaStringSql(medicamento));
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

  public void remove(Paciente paciente, String medicamento) {
    try {
      conectar();
      
      StringBuffer buffer = new StringBuffer();
      buffer.append("DELETE FROM ALERGIA_MEDICAMENTOS WHERE cpfPacienteAlergia= ");
      buffer.append(formatarParaStringSql(paciente.getCpf()));
      buffer.append(" AND medicamento= ");
      buffer.append(formatarParaStringSql(medicamento));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void update(Paciente paciente, String medAnterior, String medModificado) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE ALERGIA_MEDICAMENTOS SET ");
      buffer.append("cpfPacienteAlergia= ");
      buffer.append(formatarParaStringSql(paciente.getCpf()));
      buffer.append(", medicamento= ");
      buffer.append(formatarParaStringSql(medModificado));
      buffer.append(" WHERE cpfPacienteAlergia=" + formatarParaStringSql(paciente.getCpf()));
      buffer.append(" AND medicamento= ");
      buffer.append(formatarParaStringSql(medAnterior));
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
