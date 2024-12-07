package com.interonda.inventory.service;

import com.interonda.inventory.entity.Categoria;
import com.interonda.inventory.dto.CategoriaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaService {

    CategoriaDTO convertToDto(Categoria categoria);

    Categoria convertToEntity(CategoriaDTO categoriaDTO);

    CategoriaDTO createCategoria(CategoriaDTO categoriaDTO);

    CategoriaDTO updateCategoria(Long id, CategoriaDTO categoriaDTO);

    void deleteCategoria(Long id);

    Page<CategoriaDTO> getAllCategorias(Pageable pageable);

    CategoriaDTO getCategoriaById(Long id);


}
