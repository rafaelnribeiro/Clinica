package dao;

import entidades.Unidade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para realizar consultas na tabela UNIDADE.
 * @author      Rafael do Nascimento Ribeiro
 * @version     1.0, 20 Jun 2018
 */
public class UnidadeDao extends Dao {

  public UnidadeDao(String url, String usuario, String senha, int banco) {
    super(url, usuario, senha, banco);
  }
  
  /**
   * Insere uma nova unidade medica no banco.
   * Insere uma nova tupla na tabela UNIDADE.
   * @param unidade objeto contendo as informacoes a serem inseridas
   * @throws SQLException caso ja exista uma tupla com o mesmo numero que a unidade a ser inserida
   */
  public void inserir(Unidade unidade) throws SQLException {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO UNIDADE (");
      buffer.append("numUnidade, nome, endereco");
      buffer.append(") VALUES (");
      buffer.append(retornarValores(unidade));
      buffer.append(");");
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();
      
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Remove uma unidade do banco.
   * Remove uma tupla da tabela Unidade e, por CASCADE, todas as 
   * tuplas na tabela MEDICO relacionados a unidade removida.
   * @param unidade objeto contendo o numero da unidade a ser removida
   */
  public void remover(Unidade unidade) {
    try {
      conectar();

      String sql = ("DELETE FROM UNIDADE WHERE numUnidade= "
          + Integer.toString(unidade.getNumUnidade()));
      comando.executeUpdate(sql);

      fechar();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Altera informacoes de uma unidade no banco.
   * Modifica apenas os atributos que nao sao chave primaria.
   * @param unidade unidade com os atributos atualizados
   */
  public void alterarUnidade(Unidade unidade) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE UNIDADE SET ");
      buffer.append(retornarCampos(unidade));
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
  
  /**
   * Busca por uma unidade no banco.
   * @param numUnidade numero da unidade buscada
   * @return objeto contendo as informacoes da unidade caso encontrada,
   *        objeto null caso contrario.
   */
  public Unidade buscar(int numUnidade) {
    try {
      conectar();
      
      String sql = "SELECT * FROM UNIDADE WHERE numUnidade=" + Integer.toString(numUnidade);
      ResultSet rs = comando.executeQuery(sql);
      
      Unidade unidade = new Unidade();
      if (rs.next()) {        
        unidade.setNumUnidade(rs.getInt("numUnidade"));
        unidade.setNome(rs.getString("nome"));
        unidade.setEndereco(rs.getString("endereco"));
        
        fechar();        
        return unidade;
        
      } else {
        fechar();
        return null;
      }
      
      
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  /**
   * Recupera todas as unidades no banco.
   * Consulta todas as tuplas da tabela UNIDADE.
   * @return Lista com todas as unidades da tabela
   */
  public List<Unidade> recuperarUnidades() {
    try {
      conectar();
      
      String sql = "SELECT * FROM UNIDADE";
      ResultSet rs = comando.executeQuery(sql);
      
      List<Unidade> unidades = new ArrayList<Unidade>();
      while (rs.next()) {      
        Unidade unidade = new Unidade();
        unidade.setNumUnidade(rs.getInt("numUnidade"));
        unidade.setNome(rs.getString("nome"));
        unidade.setEndereco(rs.getString("endereco"));
        
        unidades.add(unidade);
      }
     
      fechar();
      
      return unidades;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String retornarValores(Unidade unidade) {
    return Integer.toString(unidade.getNumUnidade()) + ", "
        + formatarParaStringSql(unidade.getNome()) + ", "
        + formatarParaStringSql(unidade.getEndereco());
  }

  private String retornarCampos(Unidade unidade) {
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
