package main;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import DAO.AgendamentoDAO;
import DAO.DoencaDAO;
import DAO.EspecialidadeDAO;
import DAO.HorarioDAO;
import DAO.MedicamentoDAO;
import DAO.MedicoDAO;
import DAO.PacienteDAO;
import conexao.ConFactory;
import entidades.Agendamento;
import entidades.Horario;
import entidades.Medico;
import entidades.Paciente;

public class Main {

  public static void main(String[] args) throws Exception {
    SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
    java.sql.Date data = new java.sql.Date(format.parse("1990-10-10").getTime());

    Paciente p1 = new Paciente("12345678901", "Fulano", data, "(84)7894-9874", "fulano@gmail.com", "Rua das Oliveiras",
        "M", "12345", "O+", 50, 1.5);
    Paciente p2 = new Paciente("99999999999", "Sicrano", data, "(84)9512-9874", "sicrano@gmail.com",
        "Rua das Trincheiras", "M", "12345", "O+", 50, 1.5);
    Paciente p3 = new Paciente("77777777777", "Beltrano", data, "(84)3578-9874", "beltrano@gmail.com",
        "Rua das Torneiras", "M", "12345", "O+", 50, 1.5);

    // PacienteDAO pDAO = new PacienteDAO("jdbc:mysql://localhost/clinica",
    // "aluno", "aluno", ConFactory.MYSQL);
    PacienteDAO pDAO = new PacienteDAO("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);
    MedicamentoDAO mdDAO = new MedicamentoDAO("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);
    DoencaDAO dDAO = new DoencaDAO("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);
    //
    //pDAO.insert(p1);
    //pDAO.insert(p2);
    //pDAO.insert(p3);
    //
    // p1.setNome("Joaquim");
    // p1.setAltura(1.75);
    // pDAO.updatePaciente(p1);
    //
    // pDAO.remove(p1);
    //
    // Paciente p4 = pDAO.search("12345678901");
    // System.out.println(p4.getNome() + " " +p4.getAltura());

//     String md1 = "Prosopopol";
//     mdDAO.insert(p1, md1);
//     mdDAO.update(p1, md1, "Epopol");
//     mdDAO.remove(p1, "Epopol");

//     String d1 = "Diabetes";
//     dDAO.insert(p1, d1);
//     dDAO.update(p1, d1, "Tiabetes");
//     dDAO.remove(p1, "Tiabetes");

//    List<Paciente> pacientes = pDAO.retrievePacientes();
//    for (Iterator<Paciente> iterator = pacientes.iterator(); iterator.hasNext();) {
//      Paciente paciente = (Paciente) iterator.next();
//      System.out.println(paciente.getNome() + " " + paciente.getAltura());
//    }

    Medico m1 = new Medico("88888888888", "Who", data, "(84)7894-9874", "fulano@gmail.com", "Rua das Oliveiras", "M",
        "12345", "2222", 1);
    Medico m2 = new Medico("55555555555", "House", data, "(84)9512-9874", "sicrano@gmail.com", "Rua das Trincheiras",
        "M", "12345", "1111", 1);
    Medico m3 = new Medico("33333333333", "AAA", data, "(84)3578-9874", "beltrano@gmail.com", "Rua das Torneiras", "M",
        "12345", "3333", 1);

 
    MedicoDAO mDAO = new MedicoDAO("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);
    EspecialidadeDAO eDAO = new EspecialidadeDAO("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);
    HorarioDAO hDAO = new HorarioDAO("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);

//     mDAO.insert(m1);
//     mDAO.insert(m2);
//     mDAO.insert(m3);

    // m1.setNome("Joaquim");
    // m1.setCrm("1234");
    // mDAO.updateMedico(m1);

//     mDAO.remove(m1);
//     mDAO.remove(m2);
//     mDAO.remove(m3);
    //
    // Medico m4 = mDAO.search("88888888888");
    // System.out.println(m4.getNome() + " " +m4.getCrm());
    
//  String e1 = "Pediatra";
//  eDAO.insert(m1, e1);
//  eDAO.update(m1, e1, "Reumatologista");
//  eDAO.remove(m1, "Reumatologista");
  
//  Time hInicio = Time.valueOf("10:00:00");
//  Time hfim = Time.valueOf("12:00:00");
//  Date dInicio = Date.valueOf("2018-12-30");
//  Date dFim = Date.valueOf("2018-12-30");
//  Horario h1 = new Horario(1, hInicio, hfim, dInicio, dFim);
//  hDAO.insert(m1, h1);
//  h1.setHoraInicio(Time.valueOf("09:00:00"));
//  hDAO.update(m1, h1);
//  hDAO.remove(m1, h1);
    //
//     List<Medico> medicos = mDAO.retrieveMedicos();
//     for (Iterator<Medico> iterator = medicos.iterator(); iterator.hasNext();)    
//     {
//     Medico medico = (Medico) iterator.next();
//     System.out.println(medico.getNome() + " " +medico.getCrm());
//     }
    
    AgendamentoDAO aDAO = new AgendamentoDAO("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);
    Time horaAg = Time.valueOf("15:00:00");
    Date dataAg = Date.valueOf("2018-10-25");    
    Agendamento a1 = new Agendamento(1, "12345678901", "55555555555", dataAg, horaAg, "Agendado?", "A vida é bela");
    Agendamento a2 = new Agendamento(2, "99999999999", "33333333333", dataAg, horaAg, "Agendado?", "A vida é bala");
    Agendamento a3 = new Agendamento(3, "77777777777", "88888888888", dataAg, horaAg, "Agendado?", "A vida é bola");
    
//    aDAO.insert(a1);
//    aDAO.insert(a2);
//    aDAO.insert(a3);
    
//a1.setComentario("ABCD");
//aDAO.updateAgendamento(a1);
    //aDAO.remove(a1);
    
//    List<Agendamento> agendamentos = aDAO.retrieveAgendamentos();
//    for (Iterator<Agendamento> iterator = agendamentos.iterator(); iterator.hasNext();) {
//      Agendamento agendamento = (Agendamento) iterator.next();
//      System.out.println(agendamento.getCpfPaciente() + " " + agendamento.getCpfMedico());
//    }
    
  }
}
