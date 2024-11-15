package com.ecommerce.liven.ecommerce_backend.model.dataAccesObject;

import com.ecommerce.liven.ecommerce_backend.model.UsuarioLocal;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UsuarioLocalDAO extends ListCrudRepository<UsuarioLocal, Long> {

    Optional<UsuarioLocal> findByEmailIgnoreCase(String email);

}
