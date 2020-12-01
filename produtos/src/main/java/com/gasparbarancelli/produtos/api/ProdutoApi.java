package com.gasparbarancelli.produtos.api;

import com.gasparbarancelli.produtos.model.Produto;
import com.gasparbarancelli.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
public class ProdutoApi {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping
    public List<Produto> all() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Produto> one(@PathVariable("id") Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public Produto insert(@RequestBody Produto produto) {
        return repository.save(produto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }

}
