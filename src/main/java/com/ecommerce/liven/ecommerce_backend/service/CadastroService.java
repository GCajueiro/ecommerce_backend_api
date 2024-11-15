package com.ecommerce.liven.ecommerce_backend.service;

import com.ecommerce.liven.ecommerce_backend.api.model.ProdutoBody;
import com.ecommerce.liven.ecommerce_backend.exeption.ExistingProduct;
import com.ecommerce.liven.ecommerce_backend.model.Produto;
import com.ecommerce.liven.ecommerce_backend.model.dataAccesObject.ProdutosDAO;
import org.springframework.stereotype.Service;

@Service
public class CadastroService {

    private ProdutosDAO produtosDAO;
    public CadastroService(ProdutosDAO produtosDAO) {
        this.produtosDAO = produtosDAO;
    }

    public Produto cadastraProduto(ProdutoBody produtoBody) throws ExistingProduct {

        if (produtosDAO.findBySku(produtoBody.getSku()).isPresent() ||
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
}
