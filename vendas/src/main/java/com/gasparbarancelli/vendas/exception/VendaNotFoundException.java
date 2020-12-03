package com.gasparbarancelli.vendas.exception;

public class VendaNotFoundException extends RuntimeException {

    public VendaNotFoundException(Long id) {
        super("Venda de id " + id + " nao existe");
    }
}
