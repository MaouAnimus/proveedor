package com.ecomarket.proveedor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomarket.proveedor.model.Proveedor;
import com.ecomarket.proveedor.service.ProveedorService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/hateoas")
public class ProveedorControllerV2 {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping(value = "/listar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Proveedor>>> listarProveedoresConLinks() {
        List<Proveedor> proveedores = proveedorService.getAll();
        if (proveedores == null || proveedores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<EntityModel<Proveedor>> proveedorModels = proveedores.stream()
                .map(prov -> EntityModel.of(prov,
                        linkTo(methodOn(ProveedorControllerV2.class).buscarProveedor(prov.getIdProveedor())).withSelfRel()))
                .collect(java.util.stream.Collectors.toList());

        CollectionModel<EntityModel<Proveedor>> collectionModel = CollectionModel.of(
                proveedorModels,
                linkTo(methodOn(ProveedorControllerV2.class).listarProveedoresConLinks()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/buscar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Proveedor>>> buscarProveedores() {
        List<Proveedor> proveedores = proveedorService.getAll();
        if (proveedores == null || proveedores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<EntityModel<Proveedor>> proveedorModels = proveedores.stream()
                .map(prov -> EntityModel.of(prov,
                        linkTo(methodOn(ProveedorControllerV2.class).buscarProveedor(prov.getIdProveedor())).withSelfRel()))
                .collect(java.util.stream.Collectors.toList());

        CollectionModel<EntityModel<Proveedor>> collectionModel = CollectionModel.of(
                proveedorModels,
                linkTo(methodOn(ProveedorControllerV2.class).buscarProveedores()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<EntityModel<Proveedor>> buscarProveedor(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.findById(id);
        if (proveedor == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Proveedor> proveedorModel = EntityModel.of(
                proveedor,
                linkTo(methodOn(ProveedorControllerV2.class).buscarProveedor(id)).withSelfRel(),
                linkTo(methodOn(ProveedorControllerV2.class).listarProveedoresConLinks()).withRel("proveedores")
        );

        return ResponseEntity.ok(proveedorModel);
    }
}
