package com.algaworks.ecommerce.iniciandocomjpa;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrimeiroCrudTest extends EntityManagerTest {

    @Test
    public void inserirRegistro() {
        Cliente cliente = new Cliente();
        cliente.setId(3);
        cliente.setNome("Bianca");

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Cliente clientepersistido = entityManager.find(Cliente.class, 3);
        Assertions.assertEquals("Bianca", clientepersistido.getNome());
    }

    @Test
    public void consultarRegistro() {
        Cliente cliente = entityManager.find(Cliente.class, 2);
        Assertions.assertNotNull(cliente);
    }

    @Test
    public void atualizarRegistro() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("Fernanda");

        entityManager.getTransaction().begin();
        Cliente clienteAtualizado = entityManager.merge(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Assertions.assertEquals("Fernanda", clienteAtualizado.getNome());
    }

    @Test
    public void excluirRegistro() {
        Cliente cliente = entityManager.find(Cliente.class, 1);

        entityManager.getTransaction().begin();
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();

        Assertions.assertNull(entityManager.find(Cliente.class, 1));
    }
}
