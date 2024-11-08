package com.interonda.Inventory.service;

import com.interonda.Inventory.entity.Venta;

import java.util.List;
import java.util.Optional;

public interface VentaService {
    List<Venta> findAll();

    Venta findById(Long id);

    Venta save(Venta venta);

    void deleteById(Long id);
}
