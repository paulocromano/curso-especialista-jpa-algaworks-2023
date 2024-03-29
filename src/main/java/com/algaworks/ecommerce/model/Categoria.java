package com.algaworks.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categoria", uniqueConstraints = @UniqueConstraint(name = "unq_nome", columnNames = "nome"),
        indexes = { @Index(name = "idx_nome", columnList = "nome") })
public class Categoria extends EntidadeBaseInteger {

    @Column(length = 100, nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_pai_id", foreignKey = @ForeignKey(name = "fk_categoria_categoria_pai"))
    private Categoria categoriaPai;

    @OneToMany(mappedBy = "categoriaPai")
    private List<Categoria> categorias;

    @ManyToMany(mappedBy = "categorias")
    private List<Produto> produtos;
}
