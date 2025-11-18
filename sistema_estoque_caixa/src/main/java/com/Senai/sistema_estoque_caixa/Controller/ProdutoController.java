package com.Senai.sistema_estoque_caixa.Controller;

import com.Senai.sistema_estoque_caixa.entity.Produto;
import com.Senai.sistema_estoque_caixa.entity.MovimentacaoEstoque;
import com.Senai.sistema_estoque_caixa.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // --- CRUD Rotas ---
    
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        try {
            Produto novoProduto = produtoService.criarProduto(produto);
            return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); 
        }
    }

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarTodos();
    }

    // ... Rotas PUT e DELETE para CRUD ...

    // --- Rota de Movimentação de Estoque ---
    
    // POST /api/produtos/{id}/movimentacao
    @PostMapping("/{id}/movimentacao")
    public ResponseEntity<MovimentacaoEstoque> registrarMovimentacao(
        @PathVariable Long id, 
        @RequestBody MovimentacaoEstoque movimentacao) {
            
        try {
            MovimentacaoEstoque novaMovimentacao = produtoService.registrarMovimentacao(id, movimentacao);
            return new ResponseEntity<>(novaMovimentacao, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Trata erros como produto não encontrado ou estoque insuficiente
            return ResponseEntity.badRequest().body(null); 
        }
    }
}