package daoteste;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import conexao.ConFactory;
import dao.UnidadeDao;
import entidades.Unidade;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnidadeDaoTeste {
  static UnidadeDao uniDao;
  Unidade u1;
  Unidade u2;

  @BeforeAll
  static void setUpBeforeClass() {
    uniDao = new UnidadeDao("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);
  }

  @BeforeEach
  void setUp() throws Exception {
    List<Unidade> uni = uniDao.recuperarUnidades();
    for (Iterator<Unidade> it = uni.iterator(); it.hasNext();) {
      Unidade unidade = (Unidade) it.next();
      uniDao.remover(unidade);
    }
    u1 = new Unidade(10, "Centro de Especialidades Medicas", "Rua dos Caroas");
  }

  @Test
  public void inserirUnidadeSucesso() throws SQLException {
    uniDao.inserir(u1);
    
    u2 = uniDao.buscar(u1.getNumUnidade());
    assertEquals(u1, u2);
  }
  
  @Test
  public void inserirUnidadeExistente() throws SQLException {
    uniDao.inserir(u1);
    try {
      uniDao.inserir(u1);
      fail("Nao deve ser possível inserir uma unidade que ja esta no banco");
    } catch (SQLException e) {

    }
  }
  
  @Test
  public void removerUnidade() throws SQLException {
    uniDao.inserir(u1);
    uniDao.remover(u1);
    
    u2 = uniDao.buscar(u1.getNumUnidade());
    assertNull(u2);
  }
  
  @Test
  public void alterarUnidade() throws SQLException {
    uniDao.inserir(u1);
    
    u1.setNome("Centro de Saude Maria das Dores");
    u1.setEndereco("Rua Papagaio Flamejante");
    
    uniDao.alterarUnidade(u1);
    
    u2 = uniDao.buscar(u1.getNumUnidade());
    assertEquals(u1, u2);
  }
  
  @Test
  public void recuperarUnidades() throws SQLException {
    List<Unidade> uni = new ArrayList<Unidade>();
    uni.add(new Unidade(20, "Unidade Hospitalar Joaquim Queiroz", "Rua dos passos largos"));
    uni.add(new Unidade(21, "Clinica Luiz Gonzaga", "Rua dos mafagarfos"));
    uni.add(new Unidade(22, "Hospital dos enfermos", "Rua Três Quartos"));
    
    uniDao.inserir(uni.get(0));
    uniDao.inserir(uni.get(1));
    uniDao.inserir(uni.get(2));
    
    assertArrayEquals(uni.toArray(), uniDao.recuperarUnidades().toArray());
  }

}

