package com.ecommerce.liven.ecommerce_backend.model.dataAccesObject;

import com.ecommerce.liven.ecommerce_backend.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarrinhoDAO extends JpaRepository<Carrinho, Long> {

    Optional<Carrinho> findById(Long carrinhoId);
}
