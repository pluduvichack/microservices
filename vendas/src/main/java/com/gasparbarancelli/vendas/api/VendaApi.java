package com.gasparbarancelli.vendas.api;

import com.gasparbarancelli.vendas.converter.VendaPersistDtoConverter;
import com.gasparbarancelli.vendas.dto.VendaPersistDto;
import com.gasparbarancelli.vendas.model.Venda;
import com.gasparbarancelli.vendas.repository.VendaRepository;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
public class VendaApi {

    private final VendaPersistDtoConverter converter;
    private final VendaRepository repository;

    public VendaApi(VendaPersistDtoConverter converter, VendaRepository repository) {
        this.converter = converter;
        this.repository = repository;
    }

    @GetMapping
    public List<Venda> all() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Venda> one(@NonNull @PathVariable("id") Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public Venda insert(@RequestBody VendaPersistDto dto) {
        var venda = converter.toVenda(dto);
        return repository.save(venda);
    }

}