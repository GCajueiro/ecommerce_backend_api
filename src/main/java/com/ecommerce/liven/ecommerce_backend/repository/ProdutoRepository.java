package com.ecommerce.liven.ecommerce_backend.repository;

import com.ecommerce.liven.ecommerce_backend.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p WHERE p.price > :preco AND p.quantidade BETWEEN :minEstoque AND :maxEstoque")
    List<Produto> buscarPorPrecoEEstoque(@Param("preco") Double preco,
                                         @Param("minEstoque") Integer minEstoque,
                                         @Param("maxEstoque") Integer maxEstoque);
}