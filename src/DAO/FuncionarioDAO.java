package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entidades.Funcionario;

public class FuncionarioDAO extends DAO {

  public FuncionarioDAO(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Funcionario funcionario) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO PESSOA (");
      buffer.append("cpf, nome, dataNascimento, telefone, email, endereco, sexo, senha");
      buffer.append(") VALUES (");
      buffer.append(retornarValorBDPessoa(funcionario));
      buffer.append(");");
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      buffer.setLength(0);
      buffer.append("INSERT INTO FUNCIONARIO(");
      buffer.append("cpfFuncionario, codFuncionario");
      buffer.append(") VALUES (");
      buffer.append(retornarValorBDFuncionario(funcionario));
      buffer.append(");");
      sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void remove(Funcionario funcionario) {
    try {
      conectar();

      String sql = ("DELETE FROM PESSOA WHERE cpf= " + retornarValorStringBD(funcionario.getCpf()));
      comando.executeUpdate(sql);

      fechar();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void updateFuncionario(Funcionario funcionario) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE PESSOA SET ");
      buffer.append(retornarCamposBDPessoa(funcionario));
      buffer.append(" WHERE cpf=" + retornarValorStringBD(funcionario.getCpf()));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      buffer.setLength(0);
      buffer.append("UPDATE FUNCIONARIO SET ");
      buffer.append(retornarCamposBDFuncionario(funcionario));
      buffer.append(" WHERE cpfFuncionario=" + retornarValorStringBD(funcionario.getCpf()));
      sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  public Funcionario search(String cpf) {
    try {
      conectar();
      
      String sql = "SELECT * FROM PESSOA WHERE cpf=" + retornarValorStringBD(cpf);
      ResultSet rs = comando.executeQuery(sql);
      
      Funcionario funcionario= new Funcionario();
      if (rs.next()) {        
        funcionario.setCpf(rs.getString("cpf"));
        funcionario.setNome(rs.getString("nome"));
        funcionario.setData(rs.getDate("dataNascimento"));
        funcionario.setTelefone(rs.getString("telefone"));
        funcionario.setEmail(rs.getString("email"));
        funcionario.setEndereco(rs.getString("endereco"));
        funcionario.setSexo(rs.getString("sexo"));
        funcionario.setSenha(rs.getString("senha"));
      }
      
      sql = "SELECT * FROM FUNCIONARIO WHERE cpfFuncionario=" + retornarValorStringBD(cpf);
      rs = comando.executeQuery(sql);
      
      if (rs.next()) {        
        funcionario.setCodFuncionario(rs.getInt("codFuncionario"));
      }
      
      fechar();
      
      return funcionario;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e){
      e.printStackTrace();
    }
    return null;
  }
  
  public List<Funcionario> retrieveFuncionarios() {
    try {
      conectar();
      
      String sql = "SELECT * FROM FUNCIONARIO";
      ResultSet rs = comando.executeQuery(sql);
      
      List<Funcionario> funcionarios = new ArrayList<Funcionario>();
      while (rs.next()) {      
        Funcionario funcionario= new Funcionario();
        funcionario.setCpf(rs.getString("cpfFuncionario"));
        funcionario.setCodFuncionario(rs.getInt("codFuncionario"));
        
        funcionarios.add(funcionario);
      }
      
      for (Iterator<Funcionario> iterator = funcionarios.iterator(); iterator.hasNext();) {
        Funcionario funcionario= (Funcionario) iterator.next();
        
        
        sql = "SELECT * FROM PESSOA WHERE cpf="
            + retornarValorStringBD(funcionario.getCpf());
        rs = comando.executeQuery(sql);
        if (rs.next()) {
          funcionario.setNome(rs.getString("nome"));
          funcionario.setData(rs.getDate("dataNascimento"));
          funcionario.setTelefone(rs.getString("telefone"));
          funcionario.setEmail(rs.getString("email"));
          funcionario.setEndereco(rs.getString("endereco"));
          funcionario.setSexo(rs.getString("sexo"));
          funcionario.setSenha(rs.getString("senha"));
        }               
      }
     
      fechar();
      
      return funcionarios;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e){
      e.printStackTrace();
    }
    return null;
  }
  
  private String retornarValorBDFuncionario(Funcionario funcionario) {
    return retornarValorStringBD(funcionario.getCpf()) + ", "
        + retornarValorStringBD(Integer.toString(funcionario.getCodFuncionario()));
  }

  private String retornarValorBDPessoa(Funcionario funcionario) {
    return retornarValorStringBD(funcionario.getCpf()) + ", " 
        + retornarValorStringBD(funcionario.getNome()) + ", "
        + retornarValorStringBD(funcionario.getData().toString()) + ", " 
        + retornarValorStringBD(funcionario.getTelefone()) + ", "
        + retornarValorStringBD(funcionario.getEmail()) + ", " 
        + retornarValorStringBD(funcionario.getEndereco()) + ", "
        + retornarValorStringBD(funcionario.getSexo()) + ", " 
        + retornarValorStringBD(funcionario.getSenha());
  }

  private String retornarCamposBDPessoa(Funcionario funcionario) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("cpf= ");
    buffer.append(retornarValorStringBD(funcionario.getCpf()));
    buffer.append(", nome= ");
    buffer.append(retornarValorStringBD(funcionario.getNome()));
    buffer.append(", dataNascimento= ");
    buffer.append(retornarValorStringBD(funcionario.getData().toString()));
    buffer.append(", telefone= ");
    buffer.append(retornarValorStringBD(funcionario.getTelefone()));
    buffer.append(", email= ");
    buffer.append(retornarValorStringBD(funcionario.getEmail()));
    buffer.append(", endereco= ");
    buffer.append(retornarValorStringBD(funcionario.getEndereco()));
    buffer.append(", sexo= ");
    buffer.append(retornarValorStringBD(funcionario.getSexo()));
    buffer.append(", senha= ");
    buffer.append(retornarValorStringBD(funcionario.getSenha()));

    return buffer.toString();

  }

  private String retornarCamposBDFuncionario(Funcionario funcionario) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("cpfFuncionario= ");
    buffer.append(retornarValorStringBD(funcionario.getCpf()));
    buffer.append(", codFuncionario= ");
    buffer.append(Integer.toString(funcionario.getCodFuncionario()));

    return buffer.toString();

  }

}
