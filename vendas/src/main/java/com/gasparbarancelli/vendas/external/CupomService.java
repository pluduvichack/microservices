package com.gasparbarancelli.vendas.external;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Service
public class CupomService {

    private final RestTemplate restTemplate;

    public CupomService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "descontoZerado")
    public BigDecimal getDescontoByCupom(@NotNull String cupom) {
        Objects.requireNonNull(cupom, "Código do cupom não pode ser nulo");
        String url = "http://cupons/" + cupom;
        return restTemplate.getForObject(url, BigDecimal.class);
    }

    public BigDecimal descontoZerado(String cupom) {
        return BigDecimal.ZERO;
    }

}
