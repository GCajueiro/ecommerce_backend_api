package com.ecommerce.liven.ecommerce_backend.api.controller.carrinho;

import com.ecommerce.liven.ecommerce_backend.model.Carrinho;
import com.ecommerce.liven.ecommerce_backend.service.CarrinhoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<Carrinho> criarCarrinho(@RequestParam Long usuarioId) {
        try {
            Carrinho carrinho = carrinhoService.criarCarrinho(usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(carrinho);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/{id}/adicionar")
    public ResponseEntity<Carrinho> adicionarProdutoAoCarrinho(
            @PathVariable Long id,  // ID do carrinho
            @RequestParam Long produtoId) { // ID do produto a ser adicionado
        try {
            Carrinho carrinho = carrinhoService.adicionarProdutoAoCarrinho(id, produtoId);
            return ResponseEntity.ok(carrinho);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
