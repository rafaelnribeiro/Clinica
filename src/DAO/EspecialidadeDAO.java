package DAO;

import java.sql.SQLException;

import entidades.Medico;

public class EspecialidadeDAO extends DAO {

  public EspecialidadeDAO(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Medico medico, String especialidade) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO MED_ESPECIALIDADE (");
      buffer.append("cpfEspecialidade, especialidade");
      buffer.append(") VALUES (");
      buffer.append(retornarValorStringBD(medico.getCpf()) + ", ");
      buffer.append(retornarValorStringBD(especialidade));
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

  public void remove(Medico medico, String especialidade) {
    try {
      conectar();
      
      StringBuffer buffer = new StringBuffer();
      buffer.append("DELETE FROM MED_ESPECIALIDADE WHERE cpfEspecialidade= ");
      buffer.append(retornarValorStringBD(medico.getCpf()));
      buffer.append(" AND especialidade= ");
      buffer.append(retornarValorStringBD(especialidade));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void update(Medico medico, String espAnterior, String espModificada) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE MED_ESPECIALIDADE SET ");
      buffer.append("cpfEspecialidade= ");
      buffer.append(retornarValorStringBD(medico.getCpf()));
      buffer.append(", especialidade= ");
      buffer.append(retornarValorStringBD(espModificada));
      buffer.append(" WHERE cpfEspecialidade=" + retornarValorStringBD(medico.getCpf()));
      buffer.append(" AND especialidade= ");
      buffer.append(retornarValorStringBD(espAnterior));
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
