package DAO;

import java.sql.SQLException;

import entidades.Paciente;

public class MedicamentoDAO extends DAO {

  public MedicamentoDAO(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Paciente paciente, String medicamento) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO ALERGIA_MEDICAMENTOS (");
      buffer.append("cpfPacienteAlergia, medicamento");
      buffer.append(") VALUES (");
      buffer.append(retornarValorStringBD(paciente.getCpf()) + ", ");
      buffer.append(retornarValorStringBD(medicamento));
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
      buffer.append(retornarValorStringBD(paciente.getCpf()));
      buffer.append(" AND medicamento= ");
      buffer.append(retornarValorStringBD(medicamento));
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
      buffer.append(retornarValorStringBD(paciente.getCpf()));
      buffer.append(", medicamento= ");
      buffer.append(retornarValorStringBD(medModificado));
      buffer.append(" WHERE cpfPacienteAlergia=" + retornarValorStringBD(paciente.getCpf()));
      buffer.append(" AND medicamento= ");
      buffer.append(retornarValorStringBD(medAnterior));
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
