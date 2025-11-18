package com.Senai.sistema_estoque_caixa.repository;


import com.Senai.sistema_estoque_caixa.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Para validar se o código do produto já existe
    boolean existsByCodigoProduto(String codigoProduto);
}