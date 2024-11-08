package com.interonda.Inventory.service.impl;


import com.interonda.Inventory.entity.DetalleVenta;
import com.interonda.Inventory.exceptions.ResourceNotFoundException;
import com.interonda.Inventory.repository.DetalleVentaRepository;
import com.interonda.Inventory.service.DetalleVentaService;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DetalleVentaServiceImpl implements DetalleVentaService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DetalleVentaServiceImpl.class);

    private final DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaServiceImpl(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @Override
    public List<DetalleVenta> findAll() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public DetalleVenta findById(Long id) {
        return detalleVentaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El detalle de venta numero" + id + " no fue encontrado!"));
    }

    @Override
    public DetalleVenta save(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public void deleteById(Long id) {
        detalleVentaRepository.deleteById(id);
    }
}
