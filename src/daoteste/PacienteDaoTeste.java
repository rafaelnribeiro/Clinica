package daoteste;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import conexao.ConFactory;
import dao.PacienteDao;
import entidades.Paciente;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PacienteDaoTeste {
  static PacienteDao pacDao;
  Date data;
  Paciente p1;
  Paciente p2;
  
  @BeforeAll
  public static void setUpBeforeClass() {
    pacDao = new PacienteDao("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);
  }
  
  @BeforeEach
  public void setUp() {
    List<Paciente> pac = pacDao.recuperarPacientes();
    for (Iterator<Paciente> iterator = pac.iterator(); iterator.hasNext();) {
      Paciente paciente = (Paciente) iterator.next();
      pacDao.remover(paciente);
    }
    data = Date.valueOf("2018-10-25");
    p1 = new Paciente("10101010101", "Fulano", data, "(84)7894-9874",
        "fulano@gmail.com", "Rua", "M", "12345", "O+", 50, 1.5);
  }
 

  @Test
  public void inserirPacienteSucesso() throws SQLException {
    pacDao.inserir(p1);
    
    p2 = pacDao.buscar("10101010101");
    assertEquals(p1, p2);
  }
  
  @Test
  public void inserirPacienteExistente() throws SQLException {
    pacDao.inserir(p1);
    try {
      pacDao.inserir(p1);
      fail("Não deve ser possível inserir um paciente existente");
    } catch (SQLException e) {
      
    }
  }
  
  @Test
  public void removerPaciente() throws SQLException {    
    pacDao.inserir(p1);
    pacDao.remover(p1);
    p2 = pacDao.buscar("10101010101");
    assertNull(p2);
  }
  
  @Test
  public void alterarPaciente() throws SQLException {    
    pacDao.inserir(p1);
    
    p1.setNome("Fulana");
    p1.setData(Date.valueOf("2017-09-24"));
    p1.setTelefone("(83)7894-9874");
    p1.setEmail("fulana@gmail.com");
    p1.setEndereco("Endereco");
    p1.setSexo("F");
    p1.setSenha("54321");
    p1.setTipoSanguineo("O-");
    p1.setPeso(55);
    p1.setAltura(1.4);
    
    pacDao.atualizarPaciente(p1);
    
    p2 = pacDao.buscar("10101010101");
    assertEquals(p1, p2);
  }
  
  @Test
  public void recuperarPacientes() throws SQLException {
    List<Paciente> pacs = new ArrayList<Paciente>();
    pacs.add(new Paciente("00000000001", "Primeiro", data, "(84)0000-0001",
        "n1@gmail.com", "Rua1", "M", "1", "O+", 100, 1));
    pacs.add(new Paciente("00000000002", "Segunda", data, "(84)0000-0002",
        "n2@gmail.com", "Rua2", "F", "2", "O-", 200, 2));
    pacs.add(new Paciente("00000000003", "Terceiro", data, "(84)0000-0003",
        "n3@gmail.com", "Rua1", "M", "3", "A+", 300, 3));
    pacs.add(new Paciente("00000000004", "Quarta", data, "(84)0000-0004",
        "n4@gmail.com", "Rua1", "F", "4", "A-", 400, 4));
    
    pacDao.inserir(pacs.get(0));
    pacDao.inserir(pacs.get(1));
    pacDao.inserir(pacs.get(2));
    pacDao.inserir(pacs.get(3));
    
    assertArrayEquals(pacs.toArray(), pacDao.recuperarPacientes().toArray());
  }

}
