package dao;

import conexao.ConFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe para iniciar e fechar conexoes com o banco de dados.
 */
public class Dao {
  protected String url;
  protected String usuario;
  protected String senha;
  protected int banco;

  protected Connection con;
  protected Statement comando;
  
  /**
   * Construtor da classe.
   * @param url endereco do banco
   * @param usuario nome de usuario para acessar o banco
   * @param senha senha para acessar o banco
   * @param banco inteiro representando o banco utilizado
   */
  public Dao(String url, String usuario, String senha, int banco) {
    this.url = url;
    this.usuario = usuario;
    this.senha = senha;
    this.banco = banco;
  }

  protected void conectar() throws ClassNotFoundException, SQLException {
    con = ConFactory.conexao(url, usuario, senha, banco);
    comando = con.createStatement();
  }

  protected void fechar() {
    try {
      con.close();
      comando.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  protected String formatarParaStringSql(String valor) {
    if (valor != null && !"".equals(valor)) {
      valor = "'" + valor + "'";
    } else {
      valor = "'" + "'";
    }
    return valor;
  }
}
