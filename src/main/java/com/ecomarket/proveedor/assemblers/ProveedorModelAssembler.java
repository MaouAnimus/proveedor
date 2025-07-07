package com.ecomarket.proveedor.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.ecomarket.proveedor.controller.ProveedorController;
import com.ecomarket.proveedor.model.Proveedor;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProveedorModelAssembler extends RepresentationModelAssemblerSupport<Proveedor, EntityModel<Proveedor>>{
    
    public ProveedorModelAssembler() {
        super(ProveedorController.class, (Class<EntityModel<Proveedor>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<Proveedor> toModel(Proveedor proveedor) {
        return EntityModel.of(proveedor,
                linkTo(methodOn(ProveedorController.class).getById(proveedor.getIdProveedor(), proveedor)).withSelfRel(),
                linkTo(methodOn(ProveedorController.class).getAll()).withRel("Proveedores"));
    }
}
