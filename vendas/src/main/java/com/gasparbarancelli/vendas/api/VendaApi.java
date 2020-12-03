package com.gasparbarancelli.vendas.api;

import com.gasparbarancelli.vendas.converter.VendaPersistDtoConverter;
import com.gasparbarancelli.vendas.dto.VendaPersistDto;
import com.gasparbarancelli.vendas.exception.VendaNotFoundException;
import com.gasparbarancelli.vendas.model.Venda;
import com.gasparbarancelli.vendas.repository.VendaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("")
public class VendaApi {

    private final VendaModelAssembler vendaModelAssembler;
    private final VendaPersistDtoConverter converter;
    private final VendaRepository repository;

    public VendaApi(VendaPersistDtoConverter converter, VendaRepository repository, VendaModelAssembler vendaModelAssembler) {
        this.converter = converter;
        this.repository = repository;
        this.vendaModelAssembler = vendaModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Venda>> all(
            @NonNull @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @NonNull @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        var pageable = PageRequest.of(page, size);

        var vendas = repository.findAll(pageable).stream()
                .map(vendaModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(vendas, linkTo(methodOn(VendaApi.class).all(size, page))
                .withSelfRel());
    }

    @GetMapping("{id}")
    public EntityModel<Venda> one(@NonNull @PathVariable("id") Long id) {
        var venda = repository.findById(id)
                .orElseThrow(() -> new VendaNotFoundException(id));
        return vendaModelAssembler.toModel(venda);
    }

    @PutMapping
    public EntityModel<Venda> update(@RequestBody VendaPersistDto dto) {
        var venda = converter.toVenda(dto);
        return vendaModelAssembler.toModel(repository.save(venda));
    }

    @PostMapping
    public EntityModel<Venda> insert(@RequestBody VendaPersistDto dto) {
        var venda = converter.toVenda(dto);
        return vendaModelAssembler.toModel(repository.save(venda));
    }

}