package com.ecommerce.liven.ecommerce_backend.model.dataAccesObject;

import com.ecommerce.liven.ecommerce_backend.model.Produto;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface ProdutosDAO extends ListCrudRepository<Produto, Long> {

    Optional<Produto> findByNameIgnoreCase(String name);
    Optional<Produto> findBySku(String sku);

    List<Produto> findByQuantidadeGreaterThan(int quantidade, Pageable pageable);


}
