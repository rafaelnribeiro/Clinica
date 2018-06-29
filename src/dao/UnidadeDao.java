package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entidades.Unidade;

public class UnidadeDao extends Dao {

  public UnidadeDao(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Unidade unidade) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO UNIDADE (");
      buffer.append("numUnidade, nome, endereco");
      buffer.append(") VALUES (");
      buffer.append(retornarValorBD(unidade));
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

  public void remove(Unidade unidade) {
    try {
      conectar();

      String sql = ("DELETE FROM UNIDADE WHERE numUnidade= " + Integer.toString(unidade.getNumUnidade()));
      comando.executeUpdate(sql);

      fechar();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void updateUnidade(Unidade unidade) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE UNIDADE SET ");
      buffer.append(retornarCamposBD(unidade));
      buffer.append(" WHERE numUnidade=" + Integer.toString(unidade.getNumUnidade()));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public Unidade search(int numUnidade) {
    try {
      conectar();
      
      String sql = "SELECT * FROM UNIDADE WHERE numUnidade=" + Integer.toString(numUnidade);
      ResultSet rs = comando.executeQuery(sql);
      
      Unidade unidade= new Unidade();
      if (rs.next()) {        
        unidade.setNumUnidade(rs.getInt("numUnidade"));
        unidade.setNome(rs.getString("nome"));
        unidade.setEndereco(rs.getString("endereco"));
      }
      
      fechar();
      
      return unidade;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e){
      e.printStackTrace();
    }
    return null;
  }
  
  public List<Unidade> retrieveUnidades() {
    try {
      conectar();
      
      String sql = "SELECT * FROM UNIDADE";
      ResultSet rs = comando.executeQuery(sql);
      
      List<Unidade> unidades = new ArrayList<Unidade>();
      while (rs.next()) {      
        Unidade unidade= new Unidade();
        unidade.setNumUnidade(rs.getInt("numUnidade"));
        unidade.setNome(rs.getString("nome"));
        unidade.setEndereco(rs.getString("endereco"));
        
        unidades.add(unidade);
      }
     
      fechar();
      
      return unidades;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e){
      e.printStackTrace();
    }
    return null;
  }

  private String retornarValorBD(Unidade unidade) {
    return Integer.toString(unidade.getNumUnidade()) + ", "
    	+ formatarParaStringSql(unidade.getNome()) + ", "
        + formatarParaStringSql(unidade.getEndereco());
  }

  private String retornarCamposBD(Unidade unidade) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("numUnidade= ");
    buffer.append(Integer.toString(unidade.getNumUnidade()));
    buffer.append(", nome= ");
    buffer.append(formatarParaStringSql(unidade.getNome()));
    buffer.append(", endereco= ");
    buffer.append(formatarParaStringSql(unidade.getEndereco()));

    return buffer.toString();
  }

}
