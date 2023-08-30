package com.algaworks.ecommerce.listener;

import jakarta.persistence.PostLoad;

public class GenericoListener {

    @PostLoad
    public void logCarregamento(Object object) {
        System.out.println("Entidade " + object.getClass().getSimpleName() + " foi carregada");
    }
}
