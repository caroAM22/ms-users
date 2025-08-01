package com.pragma.plazoleta.infrastructure.output.restclient;

import com.pragma.plazoleta.infrastructure.output.restclient.dto.RestaurantResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-plaza", url = "${ms-plaza.url}", configuration = FeignClientConfig.class)
public interface PlazaFeignClient {
    @GetMapping("/restaurants/{id}")
    RestaurantResponse getRestaurantById(@PathVariable("id") String id);
} 