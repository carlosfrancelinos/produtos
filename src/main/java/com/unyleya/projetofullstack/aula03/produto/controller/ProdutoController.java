package com.unyleya.projetofullstack.aula03.produto.controller;

import com.unyleya.projetofullstack.aula03.produto.exception.ResourceNotFoundException;
import com.unyleya.projetofullstack.aula03.produto.model.Produto;
import com.unyleya.projetofullstack.aula03.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtos")
    public ResponseEntity<String> consultaTodosProdutos() {
        String produtosJson = produtoService.consultaTodosProdutos();

        return Objects.nonNull(produtosJson)
                ? ResponseEntity.status(HttpStatus.OK).body(produtosJson) : ResponseEntity.notFound().build();
    }

    @GetMapping("produto/{id}")
    public ResponseEntity<Produto> consultaProdutoPorId(@PathVariable(value = "id") String produtoId) throws ResourceNotFoundException {
        Produto produto = produtoService.findByID(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id não existente: " + produtoId));

            return Objects.nonNull(produto)
                    ? ResponseEntity.status(HttpStatus.OK).body(produto) : ResponseEntity.notFound().build();
    }

    @PostMapping("produto")
    public ResponseEntity<String> cadastraProduto(@RequestBody String produtoJson) {
        String produtoSalvo = produtoService.cadastraProduto(produtoJson);

        return Objects.nonNull(produtoSalvo)
                ? ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo) : ResponseEntity.notFound().build();
    }

    @PutMapping("/produto/{id}")
    public ResponseEntity<String> atualizaProduto(@PathVariable(value = "id") String produtoId,
                                                   @RequestBody String produtoDetails) throws ResourceNotFoundException {
        Produto produto = produtoService.findByID(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id não existente: " + produtoId));

        String produtoAtualizado = produtoService.atualizaProduto(produtoDetails, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/produto/{id}")
    public Map<String, Boolean> deletaProduto(@PathVariable(value = "id") String produtoId) throws ResourceNotFoundException {
        Produto produto = produtoService.findByID(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com id não existente: " + produtoId));

        produtoService.delete(produto);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
