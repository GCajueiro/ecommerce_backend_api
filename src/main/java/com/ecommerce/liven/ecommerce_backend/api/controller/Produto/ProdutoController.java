package com.ecommerce.liven.ecommerce_backend.api.controller.Produto;

import com.ecommerce.liven.ecommerce_backend.api.model.ProdutoBody;
import com.ecommerce.liven.ecommerce_backend.exeption.ExistingProduct;
import com.ecommerce.liven.ecommerce_backend.model.Produto;
import com.ecommerce.liven.ecommerce_backend.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> cadastrarProduto(@Valid @RequestBody ProdutoBody produtoBody) {
        try {
            Produto produto = produtoService.cadastraProduto(produtoBody);
            return ResponseEntity.ok().build();
        } catch (ExistingProduct e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/buscar")
    public List<Produto> buscarProdutosComEstoque(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return produtoService.buscarProdutosComEstoque(page, size);
    }

    // 1. Método GET para obter o produto pelo SKU
    @GetMapping("/{sku}")
    public ResponseEntity<Produto> obterProdutoPorSku(@PathVariable String sku) {
        try {
            Produto produto = produtoService.obterProdutoPorSku(sku);
            return ResponseEntity.ok(produto); // Retorna o produto com preço e quantidade atuais
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Caso o produto não seja encontrado
        }
    }

    // 2. Metodo POST para atualizar preço e quantidade do produto
    @PostMapping("/{sku}/alterar")
    public ResponseEntity<String> alterarProduto(@PathVariable String sku,
                                                 @RequestParam double novoPreco,
                                                 @RequestParam int novaQuantidade) {
        try {
            // Validação dos dados
            if (novoPreco <= 0) {
                throw new IllegalArgumentException("Preço deve ser maior que zero.");
            }
            if (novaQuantidade < 0) {
                throw new IllegalArgumentException("Quantidade não pode ser negativa.");
            }

            // Atualiza os valores do produto
            Produto produto = produtoService.atualizarProduto(sku, novoPreco, novaQuantidade);
            return ResponseEntity.ok("Produto atualizado com sucesso!");

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
