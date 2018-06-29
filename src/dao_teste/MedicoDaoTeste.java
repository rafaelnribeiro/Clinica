package dao_teste;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import conexao.ConFactory;
import dao.Dao;
import dao.MedicoDao;
import entidades.Medico;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedicoDaoTeste {
  static Dao dao;
  static MedicoDao medDao;
  Date data;
  Medico m1;
  Medico m2;

  @BeforeAll
  static void setUpBeforeClass() {
    dao = new Dao("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);
    medDao = new MedicoDao("jdbc:mysql://localhost/clinica", "root", "root", ConFactory.MYSQL);
  }

  @BeforeEach
  void setUp() {
    List<Medico> med = medDao.recuperarMedicos();
    for (Iterator<Medico> it = med.iterator(); it.hasNext();) {
      Medico medico = (Medico) it.next();
      medDao.remover(medico);
    }
    data = Date.valueOf("2018-10-25");
    m1 = new Medico("11223344556", "fulanius", data, "(84)7894-9874", "fulanius@gmail.com", "Rua dores", "M",
        "12345", "1122", 1);
  }

  @Test
  void test() {
    fail("Not yet implemented");
  }

}
