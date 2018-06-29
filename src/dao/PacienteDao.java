package dao;

import entidades.Paciente;
import entidades.Prontuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe para realizar consultas na tabela Paciente.
 * @author      Rafael do Nascimento Ribeiro
 * @version     1.0, 20 Jun 2018
 */
public class PacienteDao extends Dao {

  public PacienteDao(String url, String usuario, String senha, int banco) {
    super(url, usuario, senha, banco);
  }

  /**
   * Insere um novo paciente no banco.
   * Insere uma nova tupla na tabela PESSOA e sua tupla correspondente na tabela PACIENTE.
   * @param paciente objeto contendo as informacoes a serem inseridas
   * @throws SQLException caso ja exista uma tupla com o mesmo cpf que o paciente a ser inserido
   */
  public void inserir(Paciente paciente) throws SQLException {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("INSERT INTO PESSOA (");
      buffer.append("cpf, nome, dataNascimento, telefone, email, endereco, sexo, senha");
      buffer.append(") VALUES (");
      buffer.append(retornarValoresPessoa(paciente));
      buffer.append(");");
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      buffer.setLength(0);
      buffer.append("INSERT INTO PACIENTE (");
      buffer.append("cpfPaciente, tipoSanguineo, peso, altura");
      buffer.append(") VALUES (");
      buffer.append(retornarValoresPaciente(paciente));
      buffer.append(");");
      sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Remove um paciente do banco.
   * Remove uma tupla da tabela PESSOA e, por CASCADE,
   * todas as tuplas correspondentes nas outras tabelas do banco.
   * @param paciente objeto contendo o cpf do paciente a ser removido. 
   */
  public void remover(Paciente paciente) {
    try {
      conectar();

      String sql = ("DELETE FROM PESSOA WHERE cpf= " + formatarParaStringSql(paciente.getCpf()));
      comando.executeUpdate(sql);

      fechar();
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Altera informacoes de um paciente no banco.
   * Altera uma tupla na tabela PESSOA e sua tupla correspondente na tabela PACIENTE.
   * Modifica apenas os atributos que nao sao chave primaria.
   * @param paciente paciente com os atributos atualizados
   */
  public void atualizarPaciente(Paciente paciente) {
    try {

      conectar();

      StringBuffer buffer = new StringBuffer();
      buffer.append("UPDATE PESSOA SET ");
      buffer.append(retornarCamposPessoa(paciente));
      buffer.append(" WHERE cpf=" + formatarParaStringSql(paciente.getCpf()));
      String sql = buffer.toString();
      comando.executeUpdate(sql);

      buffer.setLength(0);
      buffer.append("UPDATE PACIENTE SET ");
      buffer.append(retornarCamposPaciente(paciente));
      buffer.append(" WHERE cpfPaciente=" + formatarParaStringSql(paciente.getCpf()));
      sql = buffer.toString();
      comando.executeUpdate(sql);

      fechar();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Busca por um paciente no banco.
   * Consulta uma tupla na tabela PESSOA e suas tuplas correspondente nas demais tabelas do banco.
   * @param cpf CPF do paciente procurado
   * @return paciente objeto contendo as informacoes do paciente caso exista,
   *        objeto null caso contrario
   */
  public Paciente buscar(String cpf) {
    try {
      conectar();
      
      String sql = "SELECT * FROM PESSOA WHERE cpf=" + formatarParaStringSql(cpf);
      ResultSet rs = comando.executeQuery(sql);
      
      Paciente paciente = new Paciente();
      if (rs.next()) {
        paciente.setCpf(rs.getString("cpf"));
        paciente.setNome(rs.getString("nome"));
        paciente.setData(rs.getDate("dataNascimento"));
        paciente.setTelefone(rs.getString("telefone"));
        paciente.setEmail(rs.getString("email"));
        paciente.setEndereco(rs.getString("endereco"));
        paciente.setSexo(rs.getString("sexo"));
        paciente.setSenha(rs.getString("senha"));
        
        sql = "SELECT * FROM PACIENTE WHERE cpfPaciente=" + formatarParaStringSql(cpf);
        rs = comando.executeQuery(sql);
        
        if (rs.next()) {        
          paciente.setTipoSanguineo(rs.getString("tipoSanguineo"));
          paciente.setPeso(rs.getDouble("peso"));
          paciente.setAltura(rs.getDouble("altura"));
        }
        
        sql = "SELECT medicamento FROM ALERGIA_MEDICAMENTOS WHERE"
            + " cpfPacienteAlergia=" + formatarParaStringSql(cpf);
        rs = comando.executeQuery(sql);
        while (rs.next()) {
          paciente.getMedicamentos().add(rs.getString("medicamento"));
        }
        
        sql = "SELECT doenca FROM DOEN�AS_CRONICAS WHERE cpfPacienteDoenca="
            + formatarParaStringSql(cpf);
        rs = comando.executeQuery(sql);
        while (rs.next()) {
          paciente.getDoencasCronicas().add(rs.getString("doenca"));
        }
        
        sql = "SELECT id_prontuario, data, hora, ficha FROM PAC_PRONTUARIO"
            + " WHERE cpfPacienteProntuario=" + formatarParaStringSql(cpf);
        rs = comando.executeQuery(sql);
        while (rs.next()) {
          Prontuario prontuario = new Prontuario();
          prontuario.setIdProntuario(rs.getInt("id_prontuario"));
          prontuario.setData(rs.getDate("data"));
          prontuario.setHora(rs.getTime("hora"));
          prontuario.setFicha(rs.getString("ficha"));       
          paciente.getProntuario().add(prontuario);
        }
        fechar();        
        return paciente;
        
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
   * Recupera todos os pacientes no banco.
   * Consulta todas as tuplas da tabela PACIENTE e 
   * suas tuplas correspondetes nas demais tabelas do banco. 
   * @return Lista com todos os pacientes da tabela
   */
  public List<Paciente> recuperarPacientes() {
    try {
      conectar();
      
      String sql = "SELECT * FROM PACIENTE";
      ResultSet rs = comando.executeQuery(sql);
      
      List<Paciente> pacientes = new ArrayList<Paciente>();
      while (rs.next()) {      
        Paciente paciente = new Paciente();
        paciente.setCpf(rs.getString("cpfPaciente"));
        paciente.setTipoSanguineo(rs.getString("tipoSanguineo"));
        paciente.setPeso(rs.getDouble("peso"));
        paciente.setAltura(rs.getDouble("altura"));
        
        pacientes.add(paciente);
      }
      
      for (Iterator<Paciente> iterator = pacientes.iterator(); iterator.hasNext();) {
        Paciente paciente = (Paciente) iterator.next();
        
        
        sql = "SELECT * FROM PESSOA WHERE cpf="
            + formatarParaStringSql(paciente.getCpf());
        rs = comando.executeQuery(sql);
        if (rs.next()) {
          paciente.setNome(rs.getString("nome"));
          paciente.setData(rs.getDate("dataNascimento"));
          paciente.setTelefone(rs.getString("telefone"));
          paciente.setEmail(rs.getString("email"));
          paciente.setEndereco(rs.getString("endereco"));
          paciente.setSexo(rs.getString("sexo"));
          paciente.setSenha(rs.getString("senha"));
        }        
        
        sql = "SELECT medicamento FROM ALERGIA_MEDICAMENTOS WHERE cpfPacienteAlergia="
            + formatarParaStringSql(paciente.getCpf());
        rs = comando.executeQuery(sql);
        while (rs.next()) {
          paciente.getMedicamentos().add(rs.getString("medicamento"));
        }
        
        sql = "SELECT doenca FROM DOEN�AS_CRONICAS WHERE cpfPacienteDoenca="
            + formatarParaStringSql(paciente.getCpf());
        rs = comando.executeQuery(sql);
        while (rs.next()) {
          paciente.getDoencasCronicas().add(rs.getString("doenca"));
        }
        
        sql = "SELECT id_prontuario, data, hora, ficha FROM PAC_PRONTUARIO"
            + " WHERE cpfPacienteProntuario=" + formatarParaStringSql(paciente.getCpf());
        rs = comando.executeQuery(sql);
        
        while (rs.next()) {
          Prontuario prontuario = new Prontuario();
          prontuario.setIdProntuario(rs.getInt("id_prontuario"));
          prontuario.setData(rs.getDate("data"));
          prontuario.setHora(rs.getTime("hora"));
          prontuario.setFicha(rs.getString("ficha"));       
          paciente.getProntuario().add(prontuario);
        } 
        
      }
     
      fechar();
      
      return pacientes;
      
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  private String retornarValoresPaciente(Paciente paciente) {
    return formatarParaStringSql(paciente.getCpf()) + ", " 
        + formatarParaStringSql(paciente.getTipoSanguineo()) + ", "
        + formatarParaStringSql(Double.toString(paciente.getPeso())) + ", "
        + formatarParaStringSql(Double.toString(paciente.getAltura()));
  }

  private String retornarValoresPessoa(Paciente paciente) {
    return formatarParaStringSql(paciente.getCpf()) + ", "
        + formatarParaStringSql(paciente.getNome()) + ", "
        + formatarParaStringSql(paciente.getData().toString()) + ", "
        + formatarParaStringSql(paciente.getTelefone()) + ", "
        + formatarParaStringSql(paciente.getEmail()) + ", "
        + formatarParaStringSql(paciente.getEndereco()) + ", "
        + formatarParaStringSql(paciente.getSexo()) + ", "
        + formatarParaStringSql(paciente.getSenha());
  }

  private String retornarCamposPessoa(Paciente paciente) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("cpf= ");
    buffer.append(formatarParaStringSql(paciente.getCpf()));
    buffer.append(", nome= ");
    buffer.append(formatarParaStringSql(paciente.getNome()));
    buffer.append(", dataNascimento= ");
    buffer.append(formatarParaStringSql(paciente.getData().toString()));
    buffer.append(", telefone= ");
    buffer.append(formatarParaStringSql(paciente.getTelefone()));
    buffer.append(", email= ");
    buffer.append(formatarParaStringSql(paciente.getEmail()));
    buffer.append(", endereco= ");
    buffer.append(formatarParaStringSql(paciente.getEndereco()));
    buffer.append(", sexo= ");
    buffer.append(formatarParaStringSql(paciente.getSexo()));
    buffer.append(", senha= ");
    buffer.append(formatarParaStringSql(paciente.getSenha()));

    return buffer.toString();

  }

  private String retornarCamposPaciente(Paciente paciente) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("cpfPaciente= ");
    buffer.append(formatarParaStringSql(paciente.getCpf()));
    buffer.append(", tipoSanguineo= ");
    buffer.append(formatarParaStringSql(paciente.getTipoSanguineo()));
    buffer.append(", peso= ");
    buffer.append(Double.toString(paciente.getPeso()));
    buffer.append(", altura= ");
    buffer.append(Double.toString(paciente.getAltura()));

    return buffer.toString();

  }

}
