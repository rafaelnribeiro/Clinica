package main;

import java.text.SimpleDateFormat;

import DAO.PacienteDAO;
import conexao.ConFactory;
import entidades.Paciente;

public class Main {

	public static void main(String[] args) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");  
		java.sql.Date data = new java.sql.Date(format.parse("1990-10-10").getTime()); 
		
		Paciente p1 = new Paciente("12345678901", "Fulano", data, "(84)7894-9874", 
				"fulano@gmail.com", "Rua das Oliveiras", "M", "12345", "O+", 50, 1.5);
		
		PacienteDAO pDAO = new PacienteDAO("jdbc:mysql://localhost/clinica", "aluno", "aluno", ConFactory.MYSQL);
		
		//pDAO.insert(p1);
		p1.setNome("Joaquim");
		//pDAO.remove(p1);
		pDAO.updatePaciente(p1);
	}

}
