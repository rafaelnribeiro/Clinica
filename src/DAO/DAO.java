package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import conexao.ConFactory;

public class DAO {
  protected String URL;
  protected String NOME;
  protected String SENHA;
  protected int BANCO;

  protected Connection con;
  protected Statement comando;
  
  public DAO(String server, String user, String password, int banco) {
    this.URL = server;
    NOME = user;
    SENHA = password;
    BANCO = banco;
  }

  protected void conectar() throws ClassNotFoundException, SQLException {
    con = ConFactory.conexao(URL, NOME, SENHA, BANCO);
    comando = con.createStatement();
  }

  protected void fechar() {
    try {
      con.close();
      comando.close();
    } catch (SQLException e) {

    }
  }
  
  protected String retornarValorStringBD(String valor) {
    if (valor != null && !"".equals(valor)) {
      valor = "'" + valor + "'";
    } else {
      valor = "'" + "'";
    }
    return valor;
  }
}
