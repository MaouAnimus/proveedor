package com.ecomarket.proveedor.repository;

import org.springframework.stereotype.Repository;

import com.ecomarket.proveedor.model.Proveedor;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long>  {
}
