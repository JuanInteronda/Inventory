package com.interonda.Inventory.mapper;

import com.interonda.Inventory.entity.Proveedor;
import com.interonda.Inventory.entityDTO.ProveedorDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T10:48:55-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
public class ProveedorMapperImpl implements ProveedorMapper {

    @Override
    public ProveedorDTO toDto(Proveedor proveedor) {
        if ( proveedor == null ) {
            return null;
        }

        ProveedorDTO proveedorDTO = new ProveedorDTO();

        proveedorDTO.setId( proveedor.getId() );
        proveedorDTO.setNombre( proveedor.getNombre() );
        proveedorDTO.setContactoProveedor( proveedor.getContactoProveedor() );
        proveedorDTO.setDireccion( proveedor.getDireccion() );
        proveedorDTO.setPais( proveedor.getPais() );
        proveedorDTO.setEmailProveedor( proveedor.getEmailProveedor() );

        return proveedorDTO;
    }

    @Override
    public Proveedor toEntity(ProveedorDTO proveedorDTO) {
        if ( proveedorDTO == null ) {
            return null;
        }

        Proveedor proveedor = new Proveedor();

        proveedor.setId( proveedorDTO.getId() );
        proveedor.setNombre( proveedorDTO.getNombre() );
        proveedor.setContactoProveedor( proveedorDTO.getContactoProveedor() );
        proveedor.setDireccion( proveedorDTO.getDireccion() );
        proveedor.setPais( proveedorDTO.getPais() );
        proveedor.setEmailProveedor( proveedorDTO.getEmailProveedor() );

        return proveedor;
    }
}