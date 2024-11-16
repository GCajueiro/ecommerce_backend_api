package com.ecommerce.liven.ecommerce_backend.service;

import com.ecommerce.liven.ecommerce_backend.model.Carrinho;
import com.ecommerce.liven.ecommerce_backend.model.Produto;
import com.ecommerce.liven.ecommerce_backend.model.UsuarioLocal;
import com.ecommerce.liven.ecommerce_backend.model.dataAccesObject.CarrinhoDAO;
import com.ecommerce.liven.ecommerce_backend.model.dataAccesObject.ProdutosDAO;
import com.ecommerce.liven.ecommerce_backend.model.dataAccesObject.UsuarioLocalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Service
public class CarrinhoService {

    private final UsuarioLocalDAO usuarioLocalDAO;

    @Autowired
    private CarrinhoDAO carrinhoDAO;

    @Autowired
    private ProdutosDAO produtosDAO;

    public CarrinhoService(CarrinhoDAO carrinhoDAO, UsuarioLocalDAO usuarioLocalDAO, ProdutosDAO produtosDAO) {
        this.carrinhoDAO = carrinhoDAO;
        this.usuarioLocalDAO = usuarioLocalDAO;
        this.produtosDAO = produtosDAO;
    }

    public Carrinho criarCarrinho(Long usuarioId) {
        // Verifica se o usuário existe
        Optional<UsuarioLocal> usuarioLocalOptional = usuarioLocalDAO.findById(usuarioId);

        if (usuarioLocalOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário com ID " + usuarioId + " não encontrado.");
        }

        UsuarioLocal usuarioLocal = usuarioLocalOptional.get();

        // Cria um novo carrinho vazio
        Carrinho carrinho = new Carrinho();
        carrinho.setUsuarioLocal(usuarioLocal);

        // Salva o carrinho no banco de dados
        return carrinhoDAO.save(carrinho);
    }

    public Carrinho adicionarProdutoAoCarrinho(Long carrinhoId, Long produtoId) {
        // Buscar o carrinho pelo ID
        Carrinho carrinho = carrinhoDAO.findById(carrinhoId)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho com ID " + carrinhoId + " não encontrado."));

        // Buscar o produto pelo ID
        Produto produto = produtosDAO.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + produtoId + " não encontrado."));

        // Adicionar o produto ao carrinho
        carrinho.getProdutos().add(produto);

        // Salvar o carrinho atualizado
        return carrinhoDAO.save(carrinho);
    }
}
