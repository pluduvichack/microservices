package com.gasparbarancelli.vendas.converter;

import com.gasparbarancelli.vendas.dto.VendaItemPersistDto;
import com.gasparbarancelli.vendas.dto.VendaPersistDto;
import com.gasparbarancelli.vendas.external.CupomService;
import com.gasparbarancelli.vendas.model.Venda;
import com.gasparbarancelli.vendas.model.VendaItem;
import com.gasparbarancelli.vendas.repository.ProdutoRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class VendaPersistDtoConverter {

    private final ProdutoRepository produtoRepository;
    private final CupomService cupomService;

    public VendaPersistDtoConverter(ProdutoRepository produtoRepository, CupomService cupomService) {
        this.produtoRepository = produtoRepository;
        this.cupomService = cupomService;
    }

    public Venda toVenda(VendaPersistDto dto) {
        Set<VendaItem> vendaItemList = new HashSet<>(dto.getItens().size());
        for (VendaItemPersistDto itemDto : dto.getItens()) {
            var optionalProduto = produtoRepository.findById(itemDto.getProduto());
            if (optionalProduto.isPresent()) {
                var produto = optionalProduto.get();
                var vendaItem = new VendaItem(produto, itemDto.getQuantidade(), itemDto.getValor());
                vendaItemList.add(vendaItem);
            }
        }

        BigDecimal desconto = BigDecimal.ZERO;
        if (dto.getCupom().isPresent() && StringUtils.isNotBlank(dto.getCupom().get())) {
            desconto = cupomService.getDescontoByCupom(dto.getCupom().get());
        }

        return new Venda(vendaItemList, desconto, dto.getEmail());
    }

}
