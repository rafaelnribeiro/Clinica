package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entidades.Agendamento;
import entidades.Medico;
import entidades.Paciente;
import entidades.Servico;

public class AgendamentoDao extends Dao {

  public AgendamentoDao(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Agendamento agendamento) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO AGENDAMENTO (");
      buffer.append("id_agendamento, cpfPacienteAgenda, cpfMedico, data, hora, status, comentario");
      buffer.append(") VALUES (");
      buffer.append(retornarValorBD(agendamento));
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

  public void remove(Agendamento agendamento) {
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

  public void updateAgendamento(Agendamento agendamento) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE AGENDAMENTO SET ");
      buffer.append(retornarCamposBD(agendamento));
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
  
  public Agendamento search(int id) {
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
      while(rs.next()) {
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
    } catch (ClassNotFoundException e){
      e.printStackTrace();
    }
    return null;
  }
  
  public List<Agendamento> retrieveAgendamentos() {
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
        while(rs.next()) {
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
    } catch (ClassNotFoundException e){
      e.printStackTrace();
    }
    return null;
  }
  public List<Agendamento> retrieveAgendamentos(Paciente paciente) {
	    try {
	      conectar();
	      
	      String sql = "SELECT * FROM AGENDAMENTO WHERE cpfPacienteAgenda= " + formatarParaStringSql(paciente.getCpf());
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
	        while(rs.next()) {
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
	    } catch (ClassNotFoundException e){
	      e.printStackTrace();
	    }
	    return null;
	  }
  
  public List<Agendamento> retrieveAgendamentos(Medico medico) {
	    try {
	      conectar();
	      
	      String sql = "SELECT * FROM AGENDAMENTO WHERE cpfMedico= " + formatarParaStringSql(medico.getCpf());
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
	        while(rs.next()) {
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
	    } catch (ClassNotFoundException e){
	      e.printStackTrace();
	    }
	    return null;
	  }
  
  private String retornarValorBD(Agendamento agendamento) {
    return formatarParaStringSql(Integer.toString(agendamento.getIdAgendamento())) + ", " 
        + formatarParaStringSql(agendamento.getCpfPaciente()) + ", "
        + formatarParaStringSql(agendamento.getCpfMedico()) + ", "
        + formatarParaStringSql(agendamento.getData().toString()) + ", "
        + formatarParaStringSql(agendamento.getHora().toString()) + ", "
        + formatarParaStringSql(agendamento.getStatus()) + ", "
        + formatarParaStringSql(agendamento.getComentario());
  }

  private String retornarCamposBD(Agendamento agendamento) {
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