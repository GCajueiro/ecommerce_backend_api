package com.ecommerce.liven.ecommerce_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "armazenamento")
public class Armazenamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "produto_id", nullable = false, unique = true)
    private Produto  produto;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}