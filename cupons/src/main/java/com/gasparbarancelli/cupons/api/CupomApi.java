package com.gasparbarancelli.cupons.api;

import com.gasparbarancelli.cupons.exception.CupomNotFoundException;
import com.gasparbarancelli.cupons.model.Cupom;
import com.gasparbarancelli.cupons.repository.CupomRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Iterator;

@RestController
@RequestMapping("")
public class CupomApi {

    private final CupomRepository repository;

    public CupomApi(CupomRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Cupom save(@RequestParam("desconto") BigDecimal desconto) {
        var cupom = new Cupom(desconto);
        return repository.save(cupom);
    }

    @GetMapping
    public Iterable<Cupom> findAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Cupom findById(@PathVariable("id") String id) {
        return repository.findById(id)
                .orElseThrow(() -> new CupomNotFoundException("Cupom de código " + id + " não encontrado"));
    }
}
