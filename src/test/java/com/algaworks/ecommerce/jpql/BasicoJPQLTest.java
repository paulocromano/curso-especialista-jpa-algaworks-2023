package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.dto.ProdutoDTO;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BasicoJPQLTest extends EntityManagerTest {

    @Test
    public void usarDistinct() {
        String jpql = "select distinct p from Pedido p " +
                " join p.itens i join i.produto pro " +
                " where pro.id in (1, 2, 3, 4) ";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assertions.assertFalse(lista.isEmpty());

        System.out.println(lista.size());
    }


    @Test
    public void ordenarResultados() {
        String jpql = "select c from Cliente c order by c.nome asc"; // desc

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        Assertions.assertFalse(lista.isEmpty());

        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
    }

    @Test
    public void projetarNoDTO() {
        String jpql = "select new com.algaworks.ecommerce.dto.ProdutoDTO(id, nome) from Produto";
        TypedQuery<ProdutoDTO> typedQuery = entityManager.createQuery(jpql, ProdutoDTO.class);
        List<ProdutoDTO> lista = typedQuery.getResultList();
        Assertions.assertFalse(lista.isEmpty());

        lista.forEach(p -> System.out.println(p.getId() + ", " + p.getNome()));
    }

    @Test
    public void projetarResultado() {
        String jpql = "select id, nome from Produto";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery.getResultList();
        Assertions.assertEquals(2, lista.get(0).length);
        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void selecionarUmAtributoParaRetorno() {
        String jpql = "select p.nome from Produto p";

        TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
        List<String> lista = typedQuery.getResultList();
        Assertions.assertTrue(String.class.equals(lista.get(0).getClass()));

        String jpqlCliente = "select p.cliente from Pedido p";
        TypedQuery<Cliente> typedQueryCliente = entityManager.createQuery(jpqlCliente, Cliente.class);
        List<Cliente> clientes = typedQueryCliente.getResultList();
        Assertions.assertTrue(Cliente.class.equals(clientes.get(0).getClass()));

    }

    @Test
    public void buscarPorIdentificador() {
        // entityManager.find(Pedido.class, 1)

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p where p.id = 1", Pedido.class);

        Pedido pedido = typedQuery.getSingleResult();
        Assertions.assertNotNull(pedido);

//        List<Pedido> lista = typedQuery.getResultList();
//        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void mostrarDiferencaQueries() {
        String jpql = "select p from Pedido p where p.id = 1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        Pedido pedido1 = typedQuery.getSingleResult();
        Assertions.assertNotNull(pedido1);

        Query query = entityManager.createQuery(jpql);
        Pedido pedido2 = (Pedido) query.getSingleResult();
        Assertions.assertNotNull(pedido2);

//        List<Pedido> lista = query.getResultList();
//        Assert.assertFalse(lista.isEmpty());
    }
}
