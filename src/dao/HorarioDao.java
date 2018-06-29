package dao;

import java.sql.SQLException;

import entidades.Horario;
import entidades.Medico;

public class HorarioDao extends Dao {

  public HorarioDao(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Medico medico, Horario horario) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO MED_HORARIO (");
      buffer.append("cpfMedicoHorario, id_horario, horaInicio, horaFim, dataInicio, dataFim");
      buffer.append(") VALUES (");
      buffer.append(formatarParaStringSql(medico.getCpf()) + ", ");
      buffer.append(retornarValorBD(horario));
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

  public void remove(Medico medico, Horario horario) {
    try {
      conectar();
      
      StringBuffer buffer = new StringBuffer();
      buffer.append("DELETE FROM MED_HORARIO WHERE cpfMedicoHorario= ");
      buffer.append(formatarParaStringSql(medico.getCpf()));
      buffer.append(" AND id_horario= ");
      buffer.append(formatarParaStringSql(Integer.toString(horario.getIdHorario())));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void update(Medico medico, Horario horario) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE MED_HORARIO SET ");
      buffer.append("cpfMedicoHorario= ");
      buffer.append(formatarParaStringSql(medico.getCpf()) + ", ");
      buffer.append(retornarCamposBD(horario));
      buffer.append(" WHERE cpfMedicoHorario=" + formatarParaStringSql(medico.getCpf()));
      buffer.append(" AND id_Horario= ");
      buffer.append(Integer.toString(horario.getIdHorario()));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  private String retornarValorBD(Horario horario) {
    return 
        formatarParaStringSql(Integer.toString(horario.getIdHorario())) + ", "
        + formatarParaStringSql(horario.getHoraInicio().toString()) + ", "
        + formatarParaStringSql(horario.getHoraFim().toString()) + ", "
        + formatarParaStringSql(horario.getDataInicio().toString()) + ", "
        + formatarParaStringSql(horario.getDataFim().toString());
  }
  
  private String retornarCamposBD(Horario horario) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("id_horario= ");
    buffer.append(Integer.toString(horario.getIdHorario()));
    buffer.append(", horaInicio= ");
    buffer.append(formatarParaStringSql(horario.getHoraInicio().toString()));
    buffer.append(", horaFim= ");
    buffer.append(formatarParaStringSql(horario.getHoraFim().toString()));
    buffer.append(", dataInicio= ");
    buffer.append(formatarParaStringSql(horario.getDataInicio().toString()));
    buffer.append(", dataFim= ");
    buffer.append(formatarParaStringSql(horario.getDataFim().toString()));
    
    return buffer.toString();

  }
}
