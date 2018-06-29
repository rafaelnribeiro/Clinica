package dao;

import java.sql.SQLException;

import entidades.Medico;

public class EspecialidadeDao extends Dao {

  public EspecialidadeDao(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Medico medico, String especialidade) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO MED_ESPECIALIDADE (");
      buffer.append("cpfEspecialidade, especialidade");
      buffer.append(") VALUES (");
      buffer.append(formatarParaStringSql(medico.getCpf()) + ", ");
      buffer.append(formatarParaStringSql(especialidade));
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
      buffer.append(formatarParaStringSql(medico.getCpf()));
      buffer.append(" AND especialidade= ");
      buffer.append(formatarParaStringSql(especialidade));
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
      buffer.append(formatarParaStringSql(medico.getCpf()));
      buffer.append(", especialidade= ");
      buffer.append(formatarParaStringSql(espModificada));
      buffer.append(" WHERE cpfEspecialidade=" + formatarParaStringSql(medico.getCpf()));
      buffer.append(" AND especialidade= ");
      buffer.append(formatarParaStringSql(espAnterior));
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
