package com.interonda.inventory.controller;

import com.interonda.inventory.dto.ProveedorDTO;
import com.interonda.inventory.service.ProveedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tableProveedores")
public class ProveedorController {

    private static final Logger logger = LoggerFactory.getLogger(ProveedorController.class);

    private final ProveedorService proveedorService;

    @Autowired
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @PostMapping
    public String createProveedor(@Valid ProveedorDTO proveedorDTO, BindingResult result, Model model, Pageable pageable) {
        if (result.hasErrors()) {
            model.addAttribute("proveedores", proveedorService.getAllProveedores(pageable).getContent());
            model.addAttribute("proveedorDTO", proveedorDTO);
            return "tableProveedores";
        }
        proveedorService.createProveedor(proveedorDTO);
        return "redirect:/tableProveedores";
    }

    @PostMapping("/update")
    public String updateProveedor(@Valid ProveedorDTO proveedorDTO, BindingResult result, Model model, Pageable pageable) {
        if (result.hasErrors()) {
            model.addAttribute("proveedores", proveedorService.getAllProveedores(pageable).getContent());
            model.addAttribute("proveedorDTO", proveedorDTO);
            return "tableProveedores";
        }
        proveedorService.updateProveedor(proveedorDTO);
        return "redirect:/tableProveedores";
    }

    @PostMapping("/{id}")
    public String deleteProveedor(@PathVariable Long id) {
        logger.debug("Llamando al método deleteProveedor con id: {}", id);
        proveedorService.deleteProveedor(id);
        logger.debug("Proveedor con id: {} eliminado correctamente", id);
        return "redirect:/tableProveedores";  // Redirige después de la eliminación
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> getProveedorById(@PathVariable Long id) {
        ProveedorDTO proveedorDTO = proveedorService.getProveedor(id);
        return new ResponseEntity<>(proveedorDTO, HttpStatus.OK);
    }

    @GetMapping
    public String showProveedores(@RequestParam(required = false) String name, Model model, Pageable pageable) {
        Page<ProveedorDTO> proveedores;
        if (name != null && !name.isEmpty()) {
            logger.info("Solicitud recibida para buscar proveedores por nombre: {}", name);
            proveedores = proveedorService.searchProveedoresByName(name, pageable);
        } else {
            proveedores = proveedorService.getAllProveedores(pageable);
        }
        model.addAttribute("proveedores", proveedores.getContent());
        model.addAttribute("proveedorDTO", new ProveedorDTO());
        return "tableProveedores";
    }

    @GetMapping("/search")
    public String searchProveedoresByName(@RequestParam String name, Model model, Pageable pageable) {
        logger.info("Solicitud recibida para buscar proveedores por nombre: {}", name);
        Page<ProveedorDTO> proveedores = proveedorService.searchProveedoresByName(name, pageable);
        model.addAttribute("proveedores", proveedores.getContent());
        model.addAttribute("proveedorDTO", new ProveedorDTO());
        return "tableProveedores";
    }
}
