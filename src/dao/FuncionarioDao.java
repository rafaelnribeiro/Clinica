package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entidades.Funcionario;

public class FuncionarioDao extends Dao {

  public FuncionarioDao(String server, String user, String password, int banco) {
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

      String sql = ("DELETE FROM PESSOA WHERE cpf= " + formatarParaStringSql(funcionario.getCpf()));
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
      buffer.append(" WHERE cpf=" + formatarParaStringSql(funcionario.getCpf()));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      buffer.setLength(0);
      buffer.append("UPDATE FUNCIONARIO SET ");
      buffer.append(retornarCamposBDFuncionario(funcionario));
      buffer.append(" WHERE cpfFuncionario=" + formatarParaStringSql(funcionario.getCpf()));
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
      
      String sql = "SELECT * FROM PESSOA WHERE cpf=" + formatarParaStringSql(cpf);
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
      
      sql = "SELECT * FROM FUNCIONARIO WHERE cpfFuncionario=" + formatarParaStringSql(cpf);
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
            + formatarParaStringSql(funcionario.getCpf());
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
    return formatarParaStringSql(funcionario.getCpf()) + ", "
        + formatarParaStringSql(Integer.toString(funcionario.getCodFuncionario()));
  }

  private String retornarValorBDPessoa(Funcionario funcionario) {
    return formatarParaStringSql(funcionario.getCpf()) + ", " 
        + formatarParaStringSql(funcionario.getNome()) + ", "
        + formatarParaStringSql(funcionario.getData().toString()) + ", " 
        + formatarParaStringSql(funcionario.getTelefone()) + ", "
        + formatarParaStringSql(funcionario.getEmail()) + ", " 
        + formatarParaStringSql(funcionario.getEndereco()) + ", "
        + formatarParaStringSql(funcionario.getSexo()) + ", " 
        + formatarParaStringSql(funcionario.getSenha());
  }

  private String retornarCamposBDPessoa(Funcionario funcionario) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("cpf= ");
    buffer.append(formatarParaStringSql(funcionario.getCpf()));
    buffer.append(", nome= ");
    buffer.append(formatarParaStringSql(funcionario.getNome()));
    buffer.append(", dataNascimento= ");
    buffer.append(formatarParaStringSql(funcionario.getData().toString()));
    buffer.append(", telefone= ");
    buffer.append(formatarParaStringSql(funcionario.getTelefone()));
    buffer.append(", email= ");
    buffer.append(formatarParaStringSql(funcionario.getEmail()));
    buffer.append(", endereco= ");
    buffer.append(formatarParaStringSql(funcionario.getEndereco()));
    buffer.append(", sexo= ");
    buffer.append(formatarParaStringSql(funcionario.getSexo()));
    buffer.append(", senha= ");
    buffer.append(formatarParaStringSql(funcionario.getSenha()));

    return buffer.toString();

  }

  private String retornarCamposBDFuncionario(Funcionario funcionario) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("cpfFuncionario= ");
    buffer.append(formatarParaStringSql(funcionario.getCpf()));
    buffer.append(", codFuncionario= ");
    buffer.append(Integer.toString(funcionario.getCodFuncionario()));

    return buffer.toString();

  }

}
