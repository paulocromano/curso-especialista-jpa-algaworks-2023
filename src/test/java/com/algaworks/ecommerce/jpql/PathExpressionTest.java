package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PathExpressionTest extends EntityManagerTest {

    @Test
    public void bucarPedidoComProdutoEspecifico() {
        String jpql = "select pedido from Pedido pedido join pedido.itens item where item.id.produtoId = 1";
        //String jpql = "select p from Pedido p join p.itens i where i.produto.id = 1";
        //String jpql = "select p from Pedido p join p.itens i join i.produto pro where pro.id = 1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();

        Assertions.assertEquals(2, lista.size());
    }

    @Test
    public void usarPathExpressions() {
        String jpql = "select p.cliente.nome from Pedido p";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assertions.assertFalse(lista.isEmpty());
    }
}
