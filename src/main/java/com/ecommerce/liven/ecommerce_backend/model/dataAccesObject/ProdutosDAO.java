package com.ecommerce.liven.ecommerce_backend.model.dataAccesObject;

import com.ecommerce.liven.ecommerce_backend.model.Produto;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface ProdutosDAO extends ListCrudRepository<Produto, Long> {

    Optional<Produto> findByNameIgnoreCase(String name);
    Optional<Produto> findBySku(String sku);
}
