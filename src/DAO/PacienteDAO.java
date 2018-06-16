package DAO;

import java.sql.SQLException;
import entidades.Paciente;

public class PacienteDAO extends DAO{
	
	public PacienteDAO(String server, String user, String password, int banco) {
    super(server, user, password, banco);
  }

  public void insert(Paciente paciente) {
		try {
			
			conectar();
			
		StringBuffer buffer = new StringBuffer();
	     buffer.append("INSERT INTO pessoa (");
      buffer.append("cpf, nome, dataNascimento, telefone, email, endereco, sexo, senha");
      buffer.append(") VALUES (");
      buffer.append(retornarValorBDPessoa(paciente));
      buffer.append(");");      
      String sql = buffer.toString();
      comando.executeUpdate(sql);
      
      buffer.setLength(0);
      buffer.append("INSERT INTO paciente (");
      buffer.append("cpfPaciente, tipoSanguineo, peso, altura");
      buffer.append(") VALUES (");
      buffer.append(retornarValorBDPaciente(paciente));
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
  
  	public void remove (Paciente paciente) {
  		try {
			conectar();
			
			String sql = ("DELETE FROM PESSOA WHERE cpf= " + retornarValorStringBD(paciente.getCpf()));			
			comando.executeUpdate(sql);			
			
			fechar();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
  	}
  	
  	public void updatePaciente(Paciente paciente) {
  		try {
  			
  			conectar();
  			
  			StringBuffer buffer = new StringBuffer();
  			buffer.append("UPDATE PESSOA SET ");
  			buffer.append(retornarCamposBDPessoa(paciente));
  			buffer.append(" WHERE cpf=" + retornarValorStringBD(paciente.getCpf()));
  			String sql = buffer.toString();
  			System.out.println(sql);
  			comando.executeUpdate(sql);
  			

  			buffer.setLength(0);
  			buffer.append("UPDATE PACIENTE SET ");
  			buffer.append(retornarCamposBDPaciente(paciente));
  			buffer.append(" WHERE cpfPaciente=" + retornarValorStringBD(paciente.getCpf()));
  			sql = buffer.toString();
  			System.out.println(sql);
  			comando.executeUpdate(sql);  			
  			
  			
  			fechar();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
  	}

	private String retornarValorBDPaciente(Paciente paciente) {
		return
		    retornarValorStringBD(paciente.getCpf())
		    + ", " +
        retornarValorStringBD(paciente.getTipoSanguineo())
        + ", " +
        retornarValorStringBD(Double.toString(paciente.getPeso()))
        + ", " +
        retornarValorStringBD(Double.toString(paciente.getAltura()));
	}
	
	 private String retornarValorBDPessoa(Paciente paciente) {
	    return
	        retornarValorStringBD(paciente.getCpf())
	        + ", " +
	        retornarValorStringBD(paciente.getNome())
	        + ", " +
	        retornarValorStringBD(paciente.getData().toString())
	        + ", " +
	        retornarValorStringBD(paciente.getTelefone())
	        + ", " +
	        retornarValorStringBD(paciente.getEmail())
	        + ", " + 
	        retornarValorStringBD(paciente.getEndereco())
	        + ", " + 
	        retornarValorStringBD(paciente.getSexo())
	        + ", " +
	        retornarValorStringBD(paciente.getSenha());
	  }
	 
	 private String retornarCamposBDPessoa(Paciente paciente) {
		 StringBuffer buffer = new StringBuffer();
		 buffer.append("cpf= ");
		 buffer.append(retornarValorStringBD(paciente.getCpf()));
		 buffer.append(", nome= ");
		 buffer.append(retornarValorStringBD(paciente.getNome()));
		 buffer.append(", dataNascimento= ");
		 buffer.append(retornarValorStringBD(paciente.getData().toString()));
		 buffer.append(", telefone= ");
		 buffer.append(retornarValorStringBD(paciente.getTelefone()));
		 buffer.append(", email= ");
		 buffer.append(retornarValorStringBD(paciente.getEmail()));
		 buffer.append(", endereco= ");
		 buffer.append(retornarValorStringBD(paciente.getEndereco()));
		 buffer.append(", sexo= ");
		 buffer.append(retornarValorStringBD(paciente.getSexo()));
		 buffer.append(", senha= ");
		 buffer.append(retornarValorStringBD(paciente.getSenha()));
		 
		 return buffer.toString();
		 
	 }
	 
	 private String retornarCamposBDPaciente(Paciente paciente) {
		 StringBuffer buffer = new StringBuffer();
		 buffer.append("cpfPaciente= ");
		 buffer.append(retornarValorStringBD(paciente.getCpf()));
		 buffer.append(", tipoSanguineo= ");
		 buffer.append(retornarValorStringBD(paciente.getTipoSanguineo()));
		 buffer.append(", peso= ");
		 buffer.append(Double.toString(paciente.getPeso()));
		 buffer.append(", altura= ");
		 buffer.append(Double.toString(paciente.getAltura()));
		 
		 return buffer.toString();
		 
	 }
	
}
