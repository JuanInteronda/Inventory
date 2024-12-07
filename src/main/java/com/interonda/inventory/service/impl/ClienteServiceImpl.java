package com.interonda.inventory.service.impl;

import com.interonda.inventory.entity.Cliente;
import com.interonda.inventory.dto.ClienteDTO;
import com.interonda.inventory.exceptions.ResourceNotFoundException;
import com.interonda.inventory.mapper.ClienteMapper;
import com.interonda.inventory.repository.ClienteRepository;
import com.interonda.inventory.service.ClienteService;
import com.interonda.inventory.exceptions.DataAccessException;
import com.interonda.inventory.utils.ValidatorUtils;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final Validator validator;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper, Validator validator) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.validator = validator;
    }

    @Override
    public ClienteDTO convertToDto(Cliente cliente) {
        return clienteMapper.toDto(cliente);
    }

    @Override
    public Cliente convertToEntity(ClienteDTO clienteDTO) {
        return clienteMapper.toEntity(clienteDTO);
    }


    @Override
    @Transactional
    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        ValidatorUtils.validateEntity(clienteDTO, validator);
        try {
            logger.info("Creando nuevo Cliente");
            Cliente cliente = clienteMapper.toEntity(clienteDTO);
            Cliente savedCliente = clienteRepository.save(cliente);
            logger.info("Cliente creado exitosamente con id: {}", savedCliente.getId());
            return clienteMapper.toDto(savedCliente);
        } catch (Exception e) {
            logger.error("Error guardando Cliente", e);
            throw new DataAccessException("Error guardando Cliente", e);
        }
    }

    @Override
    @Transactional
    public ClienteDTO updateCliente(ClienteDTO clienteDTO) {
        ValidatorUtils.validateEntity(clienteDTO, validator);
        try {
            logger.info("Actualizando Cliente con id: {}", clienteDTO.getId());
            Cliente cliente = clienteRepository.findById(clienteDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el id: " + clienteDTO.getId()));
            cliente = clienteMapper.toEntity(clienteDTO);
            Cliente updatedCliente = clienteRepository.save(cliente);
            logger.info("Cliente actualizado exitosamente con id: {}", updatedCliente.getId());
            return clienteMapper.toDto(updatedCliente);
        } catch (ResourceNotFoundException e) {
            logger.warn("Cliente no encontrado: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error actualizando Cliente", e);
            throw new DataAccessException("Error actualizando Cliente", e);
        }
    }

    @Override
    @Transactional
    public void deleteCliente(Long id) {
        try {
            logger.info("Eliminando Cliente con id: {}", id);
            if (!clienteRepository.existsById(id)) {
                throw new ResourceNotFoundException("Cliente no encontrado con el id: " + id);
            }
            clienteRepository.deleteById(id);
            logger.info("Cliente eliminado exitosamente con id: {}", id);
        } catch (ResourceNotFoundException e) {
            logger.warn("Cliente no encontrado: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error eliminando Cliente", e);
            throw new DataAccessException("Error eliminando Cliente", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO getCliente(Long id) {
        try {
            logger.info("Obteniendo Cliente con id: {}", id);
            Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con el id: " + id));
            return clienteMapper.toDto(cliente);
        } catch (ResourceNotFoundException e) {
            logger.warn("Cliente no encontrado: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error obteniendo Cliente", e);
            throw new DataAccessException("Error obteniendo Cliente", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> getAllClientes(Pageable pageable) {
        try {
            logger.info("Obteniendo todos los Clientes con paginación");
            Page<Cliente> clientes = clienteRepository.findAll(pageable);
            return clientes.map(clienteMapper::toDto);
        } catch (Exception e) {
            logger.error("Error obteniendo todos los Clientes con paginación", e);
            throw new DataAccessException("Error obteniendo todos los Clientes con paginación", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteDTO> searchClientesByName(String nombre, Pageable pageable) {
        try {
            logger.info("Buscando Clientes por nombre: {}", nombre);
            Page<Cliente> clientes = clienteRepository.findByNombreContainingIgnoreCase(nombre, pageable);
            return clientes.map(clienteMapper::toDto);
        } catch (Exception e) {
            logger.error("Error buscando Clientes por nombre", e);
            throw new DataAccessException("Error buscando Clientes por nombre", e);
        }
    }
}
