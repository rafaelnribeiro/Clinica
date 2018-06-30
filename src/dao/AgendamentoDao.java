package dao;

import entidades.Agendamento;
import entidades.Medico;
import entidades.Paciente;
import entidades.Servico;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe para realizar consultas na tabela AGENDAMENTO.
 * @author      Rafael do Nascimento Ribeiro
 * @version     1.0, 20 Jun 2018
 */
public class AgendamentoDao extends Dao {

  public AgendamentoDao(String url, String usuario, String senha, int banco) {
    super(url, usuario, senha, banco);
  }

  /**
   * Insere um novo agendamento no banco.
   * Insere uma nova tupla na tabela AGENDAMENTO.
   * @param agendamento objeto contendo as informacoes a serem inseridas
   * @throws SQLException caso ja exista um agendamento com o mesmo id que
   *       o agendamento a ser inserido
   */
  public void inserir(Agendamento agendamento) throws SQLException {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO AGENDAMENTO (");
      buffer.append("id_agendamento, cpfPacienteAgenda, cpfMedico, data, hora, status, comentario");
      buffer.append(") VALUES (");
      buffer.append(retornarValores(agendamento));
      buffer.append(");");
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Remove um agendamento do banco.
   * Remove uma tupla da tabela AGENDAMENTO.
   * @param agendamento objeto contendo o id do agendamento a ser removido
   */
  public void remover(Agendamento agendamento) {
    try {
      conectar();

      String sql = ("DELETE FROM AGENDAMENTO WHERE id_agendamento= " 
          + formatarParaStringSql(Integer.toString(agendamento.getIdAgendamento())));
      comando.executeUpdate(sql);

      fechar();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Altera informacoes de um agendamento no banco.
   * Altera uma tupla na tabela AGENDAMENTO.
   * Modifica apenas atributos que nao sao chave primaria.
   * @param agendamento agendamento com os atributos atualizados
   */
  public void alterarAgendamento(Agendamento agendamento) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE AGENDAMENTO SET ");
      buffer.append(retornarCampos(agendamento));
      buffer.append(" WHERE id_agendamento=");
      buffer.append(formatarParaStringSql(Integer.toString(agendamento.getIdAgendamento())));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Busca por um agendamento no banco.
   * Consulta uma tupla na tabela AGENDAMENTO e suas tuplas correspondentes no banco.
   * @param id id do agendamento a procurado
   * @return objeto contendo as iformacoes do paciente procurado caso exista,
   *       objeto null caso contrario
   */
  public Agendamento buscar(int id) {
    try {
      conectar();
      
      StringBuffer buffer = new StringBuffer();
      buffer.append("SELECT * FROM AGENDAMENTO WHERE id_agendamento=");
      buffer.append(formatarParaStringSql(Integer.toString(id)));      
      String sql = buffer.toString();
      ResultSet rs = comando.executeQuery(sql);
      
      Agendamento agendamento = new Agendamento();
      if (rs.next()) {        
        agendamento.setIdAgendamento(rs.getInt("id_agendamento"));
        agendamento.setCpfPaciente(rs.getString("cpfPacienteAgenda"));
        agendamento.setCpfMedico(rs.getString("cpfMedico"));
        agendamento.setData(rs.getDate("data"));
        agendamento.setHora(rs.getTime("hora"));
        agendamento.setStatus(rs.getString("status"));
        agendamento.setComentario(rs.getString("comentario"));
      }
      
      buffer.setLength(0);
      buffer.append("SELECT id_servico, horaServico, dataServico, preco, descricao, estaPago");
      buffer.append(" FROM AGENDAMENTO_SERVICO WHERE id_age_servico=");
      buffer.append(formatarParaStringSql(Integer.toString(id)));
      sql = buffer.toString();
      rs = comando.executeQuery(sql);
      while (rs.next()) {
        Servico servico = new Servico();
        servico.setIdServico(rs.getInt("id_servico"));
        servico.setHoraServico(rs.getTime("horaServico"));
        servico.setDataServico(rs.getDate("dataServico"));
        servico.setPreco(rs.getDouble("preco"));
        servico.setDescricao(rs.getString("descricao"));
        servico.setEstaPago(rs.getInt("estaPago"));
        
        agendamento.getServicos().add(servico);
      }
      
      fechar();
      
      return agendamento;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  /**
   * Recupera todos os agendamentos do banco.
   * Consulta todas as tuplas da tabela AGENDAMENTO e suas
   * tuplas correspondentes nas demais tabelas do banco.
   * @return Lista com todos os agendamentos da tabela
   */
  public List<Agendamento> recuperarAgendamentos() {
    try {
      conectar();
      
      String sql = "SELECT * FROM AGENDAMENTO";
      ResultSet rs = comando.executeQuery(sql);
      
      List<Agendamento> agendamentos = new ArrayList<Agendamento>();
      while (rs.next()) {      
        Agendamento agendamento = new Agendamento();
        agendamento.setIdAgendamento(rs.getInt("id_agendamento"));
        agendamento.setCpfPaciente(rs.getString("cpfPacienteAgenda"));
        agendamento.setCpfMedico(rs.getString("cpfMedico"));
        agendamento.setData(rs.getDate("data"));
        agendamento.setHora(rs.getTime("hora"));
        agendamento.setStatus(rs.getString("status"));
        agendamento.setComentario(rs.getString("comentario"));
        
        agendamentos.add(agendamento);
      }
      
      for (Iterator<Agendamento> iterator = agendamentos.iterator(); iterator.hasNext();) {
        Agendamento agendamento = (Agendamento) iterator.next();
        
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT id_servico, horaServico, dataServico, preco, descricao, estaPago");
        buffer.append(" FROM AGENDAMENTO_SERVICO WHERE id_age_servico=");
        buffer.append(formatarParaStringSql(Integer.toString(agendamento.getIdAgendamento())));
        sql = buffer.toString();
        rs = comando.executeQuery(sql);
        while (rs.next()) {
          Servico servico = new Servico();
          servico.setIdServico(rs.getInt("id_servico"));
          servico.setHoraServico(rs.getTime("horaServico"));
          servico.setDataServico(rs.getDate("dataServico"));
          servico.setPreco(rs.getDouble("preco"));
          servico.setDescricao(rs.getString("descricao"));
          servico.setEstaPago(rs.getInt("estaPago"));
          
          agendamento.getServicos().add(servico);
        } 
        
      }
     
      fechar();
      
      return agendamentos;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  /**
   * Recupera todos os agendamentos solicitados por um determinado paciente.
   * @param paciente objeto contendo o cpf do paciente
   * @return Lista com todos os agendamentos do paciente
   */
  public List<Agendamento> retrieveAgendamentos(Paciente paciente) {
    try {
      conectar();
      
      String sql = "SELECT * FROM AGENDAMENTO WHERE cpfPacienteAgenda= "
          + formatarParaStringSql(paciente.getCpf());
      ResultSet rs = comando.executeQuery(sql);
      
      List<Agendamento> agendamentos = new ArrayList<Agendamento>();
      while (rs.next()) {      
        Agendamento agendamento = new Agendamento();
        agendamento.setIdAgendamento(rs.getInt("id_agendamento"));
        agendamento.setCpfPaciente(rs.getString("cpfPacienteAgenda"));
        agendamento.setCpfMedico(rs.getString("cpfMedico"));
        agendamento.setData(rs.getDate("data"));
        agendamento.setHora(rs.getTime("hora"));
        agendamento.setStatus(rs.getString("status"));
        agendamento.setComentario(rs.getString("comentario"));
        
        agendamentos.add(agendamento);
      }
      
      for (Iterator<Agendamento> iterator = agendamentos.iterator(); iterator.hasNext();) {
        Agendamento agendamento = (Agendamento) iterator.next();
        
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT id_servico, horaServico, dataServico, preco, descricao, estaPago");
        buffer.append(" FROM AGENDAMENTO_SERVICO WHERE id_age_servico=");
        buffer.append(formatarParaStringSql(Integer.toString(agendamento.getIdAgendamento())));
        sql = buffer.toString();
        rs = comando.executeQuery(sql);
        while (rs.next()) {
          Servico servico = new Servico();
          servico.setIdServico(rs.getInt("id_servico"));
          servico.setHoraServico(rs.getTime("horaServico"));
          servico.setDataServico(rs.getDate("dataServico"));
          servico.setPreco(rs.getDouble("preco"));
          servico.setDescricao(rs.getString("descricao"));
          servico.setEstaPago(rs.getInt("estaPago"));
          
          agendamento.getServicos().add(servico);
        } 
        
      }
     
      fechar();
      
      return agendamentos;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  /**
   * Recupera todos os agendamentos associados a um determinado medico.
   * @param medico objeto contendo o cpf do medico
   * @return Lista com todos os agendamentos do medico
   */
  public List<Agendamento> retrieveAgendamentos(Medico medico) {
    try {
      conectar();
      
      String sql = "SELECT * FROM AGENDAMENTO WHERE cpfMedico= "
          + formatarParaStringSql(medico.getCpf());
      ResultSet rs = comando.executeQuery(sql);
      
      List<Agendamento> agendamentos = new ArrayList<Agendamento>();
      while (rs.next()) {      
        Agendamento agendamento = new Agendamento();
        agendamento.setIdAgendamento(rs.getInt("id_agendamento"));
        agendamento.setCpfPaciente(rs.getString("cpfPacienteAgenda"));
        agendamento.setCpfMedico(rs.getString("cpfMedico"));
        agendamento.setData(rs.getDate("data"));
        agendamento.setHora(rs.getTime("hora"));
        agendamento.setStatus(rs.getString("status"));
        agendamento.setComentario(rs.getString("comentario"));
        
        agendamentos.add(agendamento);
      }
      
      for (Iterator<Agendamento> iterator = agendamentos.iterator(); iterator.hasNext();) {
        Agendamento agendamento = (Agendamento) iterator.next();
        
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT id_servico, horaServico, dataServico, preco, descricao, estaPago");
        buffer.append(" FROM AGENDAMENTO_SERVICO WHERE id_age_servico=");
        buffer.append(formatarParaStringSql(Integer.toString(agendamento.getIdAgendamento())));
        sql = buffer.toString();
        rs = comando.executeQuery(sql);
        while (rs.next()) {
          Servico servico = new Servico();
          servico.setIdServico(rs.getInt("id_servico"));
          servico.setHoraServico(rs.getTime("horaServico"));
          servico.setDataServico(rs.getDate("dataServico"));
          servico.setPreco(rs.getDouble("preco"));
          servico.setDescricao(rs.getString("descricao"));
          servico.setEstaPago(rs.getInt("estaPago"));
          
          agendamento.getServicos().add(servico);
        } 
        
      }
     
      fechar();
      
      return agendamentos;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  private String retornarValores(Agendamento agendamento) {
    return formatarParaStringSql(Integer.toString(agendamento.getIdAgendamento())) + ", " 
        + formatarParaStringSql(agendamento.getCpfPaciente()) + ", "
        + formatarParaStringSql(agendamento.getCpfMedico()) + ", "
        + formatarParaStringSql(agendamento.getData().toString()) + ", "
        + formatarParaStringSql(agendamento.getHora().toString()) + ", "
        + formatarParaStringSql(agendamento.getStatus()) + ", "
        + formatarParaStringSql(agendamento.getComentario());
  }

  private String retornarCampos(Agendamento agendamento) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("id_agendamento= ");
    buffer.append(formatarParaStringSql(Integer.toString(agendamento.getIdAgendamento())));
    buffer.append(", cpfPacienteAgenda= ");
    buffer.append(formatarParaStringSql(agendamento.getCpfPaciente()));
    buffer.append(", cpfMedico= ");
    buffer.append(formatarParaStringSql(agendamento.getCpfMedico()));
    buffer.append(", data= ");
    buffer.append(formatarParaStringSql(agendamento.getData().toString()));
    buffer.append(", hora= ");
    buffer.append(formatarParaStringSql(agendamento.getHora().toString()));
    buffer.append(", status= ");
    buffer.append(formatarParaStringSql(agendamento.getStatus()));
    buffer.append(", comentario= ");
    buffer.append(formatarParaStringSql(agendamento.getComentario()));

    return buffer.toString();

  }

}
