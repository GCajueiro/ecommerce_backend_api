package com.ecommerce.liven.ecommerce_backend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrinho")
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "carrinho", orphanRemoval = true)
    private List<Produto> produtos = new ArrayList<>();

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "usuario_local_id", nullable = false, unique = true)
    private UsuarioLocal usuarioLocal;

    public UsuarioLocal getUsuarioLocal() {
        return usuarioLocal;
    }

    public void setUsuarioLocal(UsuarioLocal usuarioLocal) {
        this.usuarioLocal = usuarioLocal;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}