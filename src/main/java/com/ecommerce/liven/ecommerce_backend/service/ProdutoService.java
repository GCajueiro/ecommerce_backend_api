package com.ecommerce.liven.ecommerce_backend.service;

import com.ecommerce.liven.ecommerce_backend.api.model.ProdutoBody;
import com.ecommerce.liven.ecommerce_backend.exeption.EstoqueInvalido;
import com.ecommerce.liven.ecommerce_backend.exeption.ExistingProduct;
import com.ecommerce.liven.ecommerce_backend.exeption.PrecoInvalido;
import com.ecommerce.liven.ecommerce_backend.model.Produto;
import com.ecommerce.liven.ecommerce_backend.model.dataAccesObject.ProdutosDAO;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProdutoService {

    private ProdutosDAO produtosDAO;
    public ProdutoService(ProdutosDAO produtosDAO) {
        this.produtosDAO = produtosDAO;
    }

    public Produto cadastraProduto(ProdutoBody produtoBody) throws ExistingProduct {

        if (produtosDAO.findBySku(produtoBody.getSku()).isPresent() || // A defubur se nome será ou não um parâmetro fixo//
                produtosDAO.findByNameIgnoreCase(produtoBody.getName()).isPresent()) {
            throw new ExistingProduct();
        }
        Produto produto = new Produto();
        produto.setSku(produtoBody.getSku());
        produto.setName(produtoBody.getName());
        produto.setPrice(produtoBody.getPrice());
        produto.setQuantidade(1);

        Produto savedProduto = produtosDAO.save(produto);

        return savedProduto;
    }

    public List<Produto> buscarProdutosComEstoque(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return produtosDAO.findByQuantidadeGreaterThan(0, pageRequest); //A definir posteriormente se estoque zerado exclui item do banco
    }

    public Produto alterarPrecoEEstoque(Produto produto, double novoPreco, int novaQuantidade) {
        // Verifica se o novo preço é válido
        if (novoPreco <= 0) {
            throw new PrecoInvalido();
        }

        // Verifica se o novo estoque é válido
        if (novaQuantidade < 0) {
            throw new EstoqueInvalido();
        }

        // Alterando o preço e o estoque do produto
        produto.setPrice(novoPreco);
        produto.setQuantidade(novaQuantidade);

        return produto;
    }

    public Produto atualizarProduto(String sku, double novoPreco, int novaQuantidade) {
        // Buscar o produto no banco de dados usando o SKU
        Produto produto = produtosDAO.findBySku(sku)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado com o SKU: " + sku));

        // Atualizar o preço e a quantidade
        produto.setPrice(novoPreco);
        produto.setQuantidade(novaQuantidade);

        // Salvar o produto atualizado no banco de dados
        return produtosDAO.save(produto);
    }

    // Método para obter o produto pelo SKU
    public Produto obterProdutoPorSku(String sku) {
        Produto produto = produtosDAO.findBySku(sku)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado com o SKU: " + sku));
        return produto;
    }

}
