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

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UsuarioLocal user;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<QuantidadeCarrinho> quantidadeCarrinhos = new ArrayList<>();

    public List<QuantidadeCarrinho> getQuantidadeCarrinhos() {
        return quantidadeCarrinhos;
    }

    public void setQuantidadeCarrinhos(List<QuantidadeCarrinho> quantidadeCarrinhos) {
        this.quantidadeCarrinhos = quantidadeCarrinhos;
    }

    public UsuarioLocal getUsuarioLocal() {
        return user;
    }

    public void setUsuarioLocal(UsuarioLocal usuarioLocal) {
        this.user = usuarioLocal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}