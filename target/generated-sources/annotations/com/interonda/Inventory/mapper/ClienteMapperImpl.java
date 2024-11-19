package com.interonda.Inventory.mapper;

import com.interonda.Inventory.entity.Cliente;
import com.interonda.Inventory.entityDTO.ClienteDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T10:48:55-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public ClienteDTO toDto(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setId( cliente.getId() );
        clienteDTO.setNombre( cliente.getNombre() );
        clienteDTO.setPais( cliente.getPais() );
        clienteDTO.setCiudad( cliente.getCiudad() );
        clienteDTO.setDireccion( cliente.getDireccion() );
        clienteDTO.setContactoCliente( cliente.getContactoCliente() );
        clienteDTO.seteMailCliente( cliente.geteMailCliente() );

        return clienteDTO;
    }

    @Override
    public Cliente toEntity(ClienteDTO clienteDTO) {
        if ( clienteDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setId( clienteDTO.getId() );
        cliente.setNombre( clienteDTO.getNombre() );
        cliente.setPais( clienteDTO.getPais() );
        cliente.setCiudad( clienteDTO.getCiudad() );
        cliente.setDireccion( clienteDTO.getDireccion() );
        cliente.setContactoCliente( clienteDTO.getContactoCliente() );
        cliente.seteMailCliente( clienteDTO.geteMailCliente() );

        return cliente;
    }
}