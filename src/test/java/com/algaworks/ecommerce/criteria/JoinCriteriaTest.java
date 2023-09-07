package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JoinCriteriaTest extends EntityManagerTest {

    @Test
    public void buscarPedidosComProdutoEspecificoExercicio() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<Pedido, Produto> join = root.join("itens").join("produto");

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(join.get("id"), 1));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> pedidos = typedQuery.getResultList();
        Assertions.assertEquals(3, pedidos.size());
    }

    @Test
    public void buscarPedidosComProdutoEspecifico() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<ItemPedido, Produto> joinItemPedidoProduto = root
                .join(Pedido_.itens)
                .join(ItemPedido_.produto);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(
                joinItemPedidoProduto.get("id"), 1));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();
        Assertions.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarJoinFetch() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        root.fetch("notaFiscal", JoinType.LEFT);
        root.fetch("pagamento", JoinType.LEFT);
        root.fetch("cliente");
        //Join<Pedido, Cliente> joinCliente = (Join<Pedido, Cliente>) root.<Pedido, Cliente>fetch("cliente");

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        Pedido pedido = typedQuery.getSingleResult();
        Assertions.assertNotNull(pedido);
    }

    @Test
    public void fazerLeftOuterJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = root.join(Pedido_.pagamento, JoinType.LEFT);

        criteriaQuery.select(root);

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        Assertions.assertEquals(5, lista.size());
    }

    @Test
    public void fazerJoinComOn() {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
            Root<Pedido> root = criteriaQuery.from(Pedido.class);
            Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
            joinPagamento.on(criteriaBuilder.equal(
                    joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

            criteriaQuery.select(root);

            TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
            List<Pedido> lista = typedQuery.getResultList();
        Assertions.assertEquals(2, lista.size());
    }

    @Test
    public void fazerJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
//        Join<Pedido, ItemPedido> joinItens = root.join("itens");
//        Join<ItemPedido, Produto> joinItemProduto = joinItens.join("produto");

        criteriaQuery.select(root);

//        criteriaQuery.select(joinPagamento);
//        criteriaQuery.where(criteriaBuilder
//                .equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        Assertions.assertEquals(4, lista.size());
    }
}
