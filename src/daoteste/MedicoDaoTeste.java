package daoteste;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import conexao.ConFactory;
import dao.MedicoDao;
import dao.UnidadeDao;
import entidades.Medico;
import entidades.Unidade;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedicoDaoTeste {
  static MedicoDao medDao;
  Date data;
  Medico m1;
  Medico m2;

  @BeforeAll
  public static void setUpBeforeClass() {
    medDao = new MedicoDao("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);
    UnidadeDao uniDao = new UnidadeDao("jdbc:mysql://localhost/clinica", "root",
        "root", ConFactory.MYSQL);
    List<Medico> med = medDao.recuperarMedicos();
    for (Iterator<Medico> it = med.iterator(); it.hasNext();) {
      Medico medico = (Medico) it.next();
      medDao.remover(medico);
    }
    Unidade uni1 = new Unidade(1, "Unidade Um", "Rua das dezenas");
    Unidade uni2 = new Unidade(2, "Unidade Dois", "Rua das centenas");
    uniDao.remove(uni1);    
    uniDao.remove(uni2);
    uniDao.insert(uni1);
    uniDao.insert(uni2);
  }
  
  

  @BeforeEach
  public void setUp() {
    List<Medico> med = medDao.recuperarMedicos();
    for (Iterator<Medico> it = med.iterator(); it.hasNext();) {
      Medico medico = (Medico) it.next();
      medDao.remover(medico);
    }
    data = Date.valueOf("2018-10-25");
    m1 = new Medico("11223344556", "fulanius", data, "(84)7894-9874", "fulanius@gmail.com",
        "Rua das dores", "M",
        "12345", "1122", 1);
  }

  @Test
  public void inserirMedicoSucesso() throws SQLException {
    medDao.inserir(m1);
    m2 = medDao.buscar(m1.getCpf());
    
    assertEquals(m1, m2);
  }
  
  @Test
  public void inserirMedicoExistente() throws SQLException {
    medDao.inserir(m1);
    try {
      medDao.inserir(m1);
      fail("Não deve ser possível inserir um medico que ja esta no banco");
    } catch (SQLException e) {
     
    }
  }
  
  @Test
  public void removerMedico() throws SQLException {
    medDao.inserir(m1);
    medDao.remover(m1);
    m2 = medDao.buscar(m1.getCpf());
    assertNull(m2);
  }
  
  @Test
  public void alterarMedico() throws SQLException {
    medDao.inserir(m1);
    
    m1.setNome("Fulania");
    m1.setData(Date.valueOf("2017-09-24"));
    m1.setTelefone("(83)7894-9874");
    m1.setEmail("fulania@gmail.com");
    m1.setEndereco("Rua das cores");
    m1.setSexo("F");
    m1.setSenha("54321");
    m1.setCrm("2233");
    m1.setNumUnidade(2);
    
    medDao.atualizarMedico(m1);
    
    m2 = medDao.buscar(m1.getCpf());
    assertEquals(m1, m2);
  }
  
  @Test
  public void recuperarMedicos() throws SQLException {
    List<Medico> meds = new ArrayList<Medico>();
    meds.add(new Medico("00000000001", "Primeiro", data, "(84)0000-0001",
        "m1@gmail.com", "Rua1", "M", "1", "00001", 1));
    meds.add(new Medico("00000000002", "Segunda", data, "(84)0000-0002",
        "m2@gmail.com", "Rua2", "F", "2", "00002", 1));
    meds.add(new Medico("00000000003", "Terceiro", data, "(84)0000-0003",
        "m3@gmail.com", "Rua1", "M", "3", "00003", 2));
    meds.add(new Medico("00000000004", "Quarta", data, "(84)0000-0004",
        "m4@gmail.com", "Rua1", "F", "4", "00004", 2));
    
    medDao.inserir(meds.get(0));
    medDao.inserir(meds.get(1));
    medDao.inserir(meds.get(2));
    medDao.inserir(meds.get(3));
    
    assertArrayEquals(meds.toArray(), medDao.recuperarMedicos().toArray());
    
  }

}
