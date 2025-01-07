package com.unyleya.projetofullstack.aula03.produto.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unyleya.projetofullstack.aula03.produto.model.Produto;
import com.unyleya.projetofullstack.aula03.produto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public String consultaTodosProdutos() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(produtoRepository.findAll());
        return json;
    }

    public String cadastraProduto(String produtoJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Produto produto = geraProduto(gson, produtoJson);
        Produto produtoSalvo = produtoRepository.save(produto);
        return geraJson(gson, produtoSalvo);
    }

    public Optional<Produto> findByID(String produtoId) {
        return produtoRepository.findById(produtoId);
    }

    public void delete(Produto produto) {
        produtoRepository.delete(produto);
    }

    public String atualizaProduto(String produtoDetails, Produto produtoOriginal) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Produto produto = geraProduto(gson, produtoDetails);
        comparaProdutos(produto, produtoOriginal);
        Produto produtoAtualizado = produtoRepository.save(produtoOriginal);
        return geraJson(gson, produtoAtualizado);
    }

    public String geraJson(Produto produto) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(produto);
    }

    private void comparaProdutos(Produto produto, Produto produtoOriginal) {
        if (!produto.getNome().equals(produtoOriginal.getNome()))
            produtoOriginal.setNome(produto.getNome());
        if (!produto.getCodigo().equals(produtoOriginal.getCodigo()))
            produtoOriginal.setCodigo(produto.getCodigo());
        if (!produto.getPreco().equals(produtoOriginal.getPreco()))
            produtoOriginal.setPreco(produto.getPreco());

    }

    private Produto geraProduto(Gson gson, String produtoJson) {
        return gson.fromJson(produtoJson, Produto.class);
    }

    private String geraJson(Gson gson, Produto produtoSalvo) {
        return gson.toJson(produtoSalvo);
    }
}
