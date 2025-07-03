package com.ecomarket.repository;

import org.springframework.stereotype.Repository;

import com.ecomarket.model.Proveedor;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long>  {
}
