package com.unyleya.projetofullstack.aula03.produto.repository;

import com.unyleya.projetofullstack.aula03.produto.model.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {
}
