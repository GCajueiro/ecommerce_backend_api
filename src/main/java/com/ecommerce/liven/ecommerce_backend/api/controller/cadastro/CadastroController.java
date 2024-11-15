package com.ecommerce.liven.ecommerce_backend.api.controller.cadastro;

import com.ecommerce.liven.ecommerce_backend.api.model.ProdutoBody;
import com.ecommerce.liven.ecommerce_backend.exeption.ExistingProduct;
import com.ecommerce.liven.ecommerce_backend.model.Produto;
import com.ecommerce.liven.ecommerce_backend.service.CadastroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produto")
public class CadastroController {

    private CadastroService cadastroService;

    public CadastroController(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> cadastrarProduto(@Valid @RequestBody ProdutoBody produtoBody) {
        try {
            Produto produto = cadastroService.cadastraProduto(produtoBody);
            return ResponseEntity.ok().build();
        } catch (ExistingProduct e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
