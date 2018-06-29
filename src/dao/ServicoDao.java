package dao;

import java.sql.SQLException;

import entidades.Servico;
import entidades.Agendamento;

public class ServicoDao extends Dao {

  public ServicoDao(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Agendamento agendamento, Servico servico) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO AGENDAMENTO_SERVICO (");
      buffer.append("id_age_servico, id_servico, horaServico, dataServico, preco, descricao, estaPago");
      buffer.append(") VALUES (");
      buffer.append(Integer.toString(agendamento.getIdAgendamento()) + ", ");
      buffer.append(retornarValorBD(servico));
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

  public void remove(Agendamento agendamento, Servico servico) {
    try {
      conectar();
      
      StringBuffer buffer = new StringBuffer();
      buffer.append("DELETE FROM AGENDAMENTO_SERVICO WHERE id_servico= ");
      buffer.append(Integer.toString(agendamento.getIdAgendamento()));
      buffer.append(" AND id_age_servico= ");
      buffer.append(Integer.toString(servico.getIdServico()));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void update(Agendamento agendamento, Servico servico) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE AGENDAMENTO_SERVICO SET ");
      buffer.append("id_age_servico= ");
      buffer.append(Integer.toString(agendamento.getIdAgendamento()) + ", ");
      buffer.append(retornarCamposBD(servico));
      buffer.append(" WHERE id_servico=");
      buffer.append(Integer.toString(servico.getIdServico()));
      buffer.append(" AND id_age_servico= ");
      buffer.append(Integer.toString(agendamento.getIdAgendamento()));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  private String retornarValorBD(Servico servico) {
    return 
        Integer.toString(servico.getIdServico()) + ", "
        + formatarParaStringSql(servico.getHoraServico().toString()) + ", "
        + formatarParaStringSql(servico.getDataServico().toString()) + ", "
        + Double.toString(servico.getPreco()) + ", "
        + formatarParaStringSql(servico.getDescricao()) + ", "
        + formatarParaStringSql(Integer.toString(servico.getEstaPago()));
  }
  
  private String retornarCamposBD(Servico servico) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("id_age_servico= ");
    buffer.append(Integer.toString(servico.getIdServico()));
    buffer.append(", horaServico= ");
    buffer.append(formatarParaStringSql(servico.getHoraServico().toString()));
    buffer.append(", dataServico= ");
    buffer.append(formatarParaStringSql(servico.getDataServico().toString()));
    buffer.append(", preco= ");
    buffer.append(Double.toString(servico.getPreco()));
    buffer.append(", descricao= ");
    buffer.append(formatarParaStringSql(servico.getDescricao()));
    buffer.append(", estaPago= ");
    buffer.append(Integer.toString(servico.getEstaPago()));
    
    return buffer.toString();

  }
}
