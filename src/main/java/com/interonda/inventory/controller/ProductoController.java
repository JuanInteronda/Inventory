package com.interonda.inventory.controller;

import com.interonda.inventory.dto.ProductoDTO;
import com.interonda.inventory.service.CategoriaService;
import com.interonda.inventory.service.ProductoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/tableProductos")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final MessageSource messageSource;

    @Autowired
    public ProductoController(ProductoService productoService, CategoriaService categoriaService, MessageSource messageSource) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.messageSource = messageSource;
    }

    @PostMapping
    public String createProducto(@Valid ProductoDTO productoDTO, BindingResult result, Model model, Pageable pageable) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(fieldError -> messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()))
                    .collect(Collectors.joining("<br>"));
            model.addAttribute("errorMessage", errorMessage);
            Page<ProductoDTO> productos = productoService.getAllProductos(pageable);
            model.addAttribute("productos", productos.getContent());
            model.addAttribute("productoDTO", productoDTO);
            model.addAttribute("page", productos); // Asegúrate de agregar el objeto 'page' al modelo
            model.addAttribute("categorias", categoriaService.getAllCategorias(PageRequest.of(0, Integer.MAX_VALUE)).getContent());
            return "tableProductos";
        }
        productoService.createProducto(productoDTO);
        return "redirect:/tableProductos";
    }

    @PostMapping("/update")
    public String updateProducto(@Valid ProductoDTO productoDTO, BindingResult result, Model model, Pageable pageable) {
        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(fieldError -> messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()))
                    .collect(Collectors.joining("<br>"));
            model.addAttribute("errorMessage", errorMessage);
            Page<ProductoDTO> productos = productoService.getAllProductos(pageable);
            model.addAttribute("productos", productos.getContent());
            model.addAttribute("productoDTO", productoDTO);
            model.addAttribute("page", productos); // Asegúrate de agregar el objeto 'page' al modelo
            model.addAttribute("categorias", categoriaService.getAllCategorias(PageRequest.of(0, Integer.MAX_VALUE)).getContent());
            return "tableProductos";
        }
        productoService.updateProducto(productoDTO);
        return "redirect:/tableProductos";
    }

    @PostMapping("/{id}")
    public String deleteProducto(@PathVariable Long id) {
        logger.debug("Llamando al método deleteProducto con id: {}", id);
        productoService.deleteProducto(id);
        logger.debug("Producto con id: {} eliminado correctamente", id);
        return "redirect:/tableProductos";
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        ProductoDTO productoDTO = productoService.getProducto(id);
        return new ResponseEntity<>(productoDTO, HttpStatus.OK);
    }

    @GetMapping
    public String showProductos(@RequestParam(required = false) String name, Model model, Pageable pageable) {
        int pageSize = 15;
        Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageSize);
        Page<ProductoDTO> productos;
        if (name != null && !name.isEmpty()) {
            logger.info("Solicitud recibida para buscar productos por nombre: {}", name);
            productos = productoService.searchProductosByName(name, newPageable);
        } else {
            productos = productoService.getAllProductos(newPageable);
        }
        model.addAttribute("productos", productos.getContent());
        model.addAttribute("productoDTO", new ProductoDTO());
        model.addAttribute("page", productos);
        model.addAttribute("categorias", categoriaService.getAllCategorias(PageRequest.of(0, Integer.MAX_VALUE)).getContent());

        return "tableProductos";
    }

    @GetMapping("/search")
    public String searchProductosByName(@RequestParam String name, Model model, Pageable pageable) {
        int pageSize = 15;
        Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageSize);
        logger.info("Solicitud recibida para buscar productos por nombre: {}", name);
        Page<ProductoDTO> productos = productoService.searchProductosByName(name, newPageable);
        model.addAttribute("productos", productos.getContent());
        model.addAttribute("productoDTO", new ProductoDTO());
        model.addAttribute("page", productos);
        return "tableProductos";
    }
}