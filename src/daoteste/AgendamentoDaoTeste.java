package daoteste;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import conexao.ConFactory;
import dao.AgendamentoDao;
import dao.MedicoDao;
import dao.PacienteDao;
import dao.UnidadeDao;
import entidades.Agendamento;
import entidades.Medico;
import entidades.Paciente;
import entidades.Unidade;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AgendamentoDaoTeste {
  static AgendamentoDao ageDao;
  Date data;
  Time hora;
  Agendamento age1;
  Agendamento age2;

  @BeforeAll
  static void setUpBeforeClass() throws SQLException {
    ageDao = new AgendamentoDao("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);
    
    Date dt = Date.valueOf("2017-09-12");
    
    List<Paciente> pacs = new ArrayList<Paciente>();
    pacs.add(new Paciente("00000000001", "Primeiro", dt, "(84)0000-0001",
        "n1@gmail.com", "Rua1", "M", "1", "O+", 100, 1));
    pacs.add(new Paciente("00000000002", "Segunda", dt, "(84)0000-0002",
        "n2@gmail.com", "Rua2", "F", "2", "O-", 200, 2));
    pacs.add(new Paciente("00000000003", "Terceiro", dt, "(84)0000-0003",
        "n3@gmail.com", "Rua1", "M", "3", "A+", 300, 3));
    PacienteDao pacDao = new PacienteDao("jdbc:mysql://localhost/clinica", "root",
        "root", ConFactory.MYSQL);
    pacDao.remover(pacs.get(0));
    pacDao.remover(pacs.get(1));
    pacDao.remover(pacs.get(2));
    pacDao.inserir(pacs.get(0));
    pacDao.inserir(pacs.get(1));
    pacDao.inserir(pacs.get(2));
    
    
    Unidade uni1 = new Unidade(1, "Unidade", "Rua das unidades");
    UnidadeDao uniDao = new UnidadeDao("jdbc:mysql://localhost/clinica", "root",
        "root", ConFactory.MYSQL); 
    uniDao.remover(uni1);
    uniDao.inserir(uni1);
    
    
    List<Medico> meds = new ArrayList<Medico>();
    meds.add(new Medico("99999999991", "Primeiro", dt, "(84)0000-0001",
        "m1@gmail.com", "Rua1", "M", "1", "00001", 1));
    meds.add(new Medico("99999999992", "Segunda", dt, "(84)0000-0002",
        "m2@gmail.com", "Rua2", "F", "2", "00002", 1));
    meds.add(new Medico("99999999993", "Terceiro", dt, "(84)0000-0003",
        "m3@gmail.com", "Rua1", "M", "3", "00003", 2));
    MedicoDao medDao = new MedicoDao("jdbc:mysql://localhost/clinica", "root",
        "root", ConFactory.MYSQL);   
    medDao.remover(meds.get(0));
    medDao.remover(meds.get(1));
    medDao.remover(meds.get(2));
    medDao.inserir(meds.get(0));
    medDao.inserir(meds.get(1));
    medDao.inserir(meds.get(2));
    
  }

  @BeforeEach
  void setUp() throws Exception {
    List<Agendamento> ages = ageDao.recuperarAgendamentos();
    for (Iterator<Agendamento> it = ages.iterator(); it.hasNext();) {
      Agendamento agendamento = (Agendamento) it.next();
      ageDao.remover(agendamento);
    }
    
    data = Date.valueOf("2017-08-13");
    hora = Time.valueOf("10:45:00");
    
    age1 = new Agendamento(101, "00000000001", "99999999991", data, hora, "Agendado",
        "Sem comentarios");
  }

  @Test
  public void inserirAgendamentoSucesso() throws SQLException {
    ageDao.inserir(age1);
    
    age2 = ageDao.buscar(age1.getIdAgendamento());
    assertEquals(age1, age2);
  }
  
  @Test
  public void inserirAgendamentoExistente() throws SQLException {
    ageDao.inserir(age1);
    try {
      ageDao.inserir(age1);
      fail("Nao deve ser possivel inserir um agendamento que ja esta cadastrado");
    } catch (SQLException e) {
      
    }
  }
  
  @Test
  public void removerAgendamento() throws SQLException {
    ageDao.inserir(age1);
    ageDao.remover(age1);
    
    age2 = ageDao.buscar(age1.getIdAgendamento());
    assertNull(age2);
  }
  
  @Test
  public void alterarAgendamento() throws SQLException {
    ageDao.inserir(age1);
    
    age1.setCpfMedico("99999999992");
    age1.setData(Date.valueOf("2018-10-10"));
    age1.setHora(Time.valueOf("10:30:00"));
    age1.setStatus("Reagendado");
    age1.setComentario("Reagendado por mudanca de medico");

    ageDao.alterarAgendamento(age1);
    age2 = ageDao.buscar(age1.getIdAgendamento());
    
    assertEquals(age1, age2);
  }
  
  @Test
  public void recuperarTodosAgendamentos() throws SQLException {
    List<Agendamento> age = new ArrayList<Agendamento>();
    age.add(new Agendamento(201, "00000000001", "99999999991", data, hora, "Agendado",
        "Sem comentarios"));
    age.add(new Agendamento(202, "00000000002", "99999999992", data, hora, "Cancelado",
        "Cancelado por paciente"));
    age.add(new Agendamento(203, "00000000003", "99999999993", data, hora, "Concluido",
        "Sem comentarios"));
    
    ageDao.inserir(age.get(0));
    ageDao.inserir(age.get(1));
    ageDao.inserir(age.get(2));
    
    assertArrayEquals(age.toArray(), ageDao.recuperarAgendamentos().toArray());
  }
  
  @Test
  public void recuperarAgendamentosPaciente() throws SQLException {
    List<Agendamento> age = new ArrayList<Agendamento>();
    Paciente pac = new Paciente("00000000001", "Primeiro", data, "(84)0000-0001",
        "n1@gmail.com", "Rua1", "M", "1", "O+", 100, 1);
    
    age.add(new Agendamento(201, pac.getCpf(), "99999999991", data, hora, "Agendado",
        "Sem comentarios"));
    age.add(new Agendamento(202, "00000000002", "99999999992", data, hora, "Cancelado",
        "Cancelado por paciente"));
    age.add(new Agendamento(203, pac.getCpf(), "99999999993", data, hora, "Concluido",
        "Sem comentarios"));
    
    ageDao.inserir(age.get(0));
    ageDao.inserir(age.get(1));
    ageDao.inserir(age.get(2));
    
    age.remove(1);
    
    assertArrayEquals(age.toArray(), ageDao.recuperarAgendamentos(pac).toArray());
  }
  
  @Test
  public void recuperarAgendamentosMedico() throws SQLException {
    List<Agendamento> age = new ArrayList<Agendamento>();
    Medico med = new Medico("99999999991", "Primeiro", data, "(84)0000-0001",
        "m1@gmail.com", "Rua1", "M", "1", "00001", 1);
    
    age.add(new Agendamento(201, "00000000002", med.getCpf(), data, hora, "Agendado",
        "Sem comentarios"));
    age.add(new Agendamento(202, "00000000002", "99999999992", data, hora, "Cancelado",
        "Cancelado por paciente"));
    age.add(new Agendamento(203, "00000000003", med.getCpf(), data, hora, "Concluido",
        "Sem comentarios"));
    
    ageDao.inserir(age.get(0));
    ageDao.inserir(age.get(1));
    ageDao.inserir(age.get(2));
    
    age.remove(1);
    
    assertArrayEquals(age.toArray(), ageDao.recuperarAgendamentos(med).toArray());
  }

}
