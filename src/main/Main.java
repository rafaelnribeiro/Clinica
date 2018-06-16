package main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import DAO.PacienteDAO;
import conexao.ConFactory;
import entidades.Paciente;

public class Main {

	public static void main(String[] args) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");  
		java.sql.Date data = new java.sql.Date(format.parse("1990-10-10").getTime()); 
		
		Paciente p1 = new Paciente("12345678901", "Fulano", data, "(84)7894-9874", 
				"fulano@gmail.com", "Rua das Oliveiras", "M", "12345", "O+", 50, 1.5);
		Paciente p2 = new Paciente("99999999999", "Sicrano", data, "(84)9512-9874", 
        "sicrano@gmail.com", "Rua das Trincheiras", "M", "12345", "O+", 50, 1.5);
		Paciente p3 = new Paciente("77777777777", "Beltrano", data, "(84)3578-9874", 
        "beltrano@gmail.com", "Rua das Torneiras", "M", "12345", "O+", 50, 1.5);
		
		//PacienteDAO pDAO = new PacienteDAO("jdbc:mysql://localhost/clinica", "aluno", "aluno", ConFactory.MYSQL);
		PacienteDAO pDAO = new PacienteDAO("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);
		
/*	pDAO.insert(p1);
		pDAO.insert(p2);
		pDAO.insert(p3);*/
		
/*	p1.setNome("Joaquim");
		p1.setAltura(1.75);
	  pDAO.updatePaciente(p1);*/
		
	  //pDAO.remove(p1);
		
		Paciente p4 = pDAO.search("12345678901");
		System.out.println(p4.getNome() + " " +p4.getAltura());
		
/*		List<Paciente> pacientes = pDAO.retrievePacientes();		
		for (Iterator iterator = pacientes.iterator(); iterator.hasNext();) {
      Paciente paciente = (Paciente) iterator.next();
      System.out.println(paciente.getNome() + " " +paciente.getAltura());
    }*/
	}

}
