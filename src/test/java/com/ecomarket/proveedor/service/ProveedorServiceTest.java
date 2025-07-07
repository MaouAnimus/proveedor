package com.ecomarket.proveedor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ecomarket.proveedor.model.Proveedor;
import com.ecomarket.proveedor.repository.ProveedorRepository;

public class ProveedorServiceTest {
    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearProveedor() {
        Proveedor proveedor = new Proveedor(1L, "ELMATADERO");
        Proveedor proveedorGuardado = new Proveedor(1L, "ELMATADERO");
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedorGuardado);
        Proveedor resultado = proveedorService.crearProveedor(proveedor);
        assertNotNull(resultado);
        assertEquals(proveedorGuardado.getIdProveedor(), resultado.getIdProveedor());
        assertEquals(proveedorGuardado.getNombreProveedor(), resultado.getNombreProveedor());
        verify(proveedorRepository).save(any(Proveedor.class));
    }

    @Test
    void testListarProveedores() {
        Proveedor proveedor = new Proveedor(1L, "ELMATADERO");
        Proveedor proveedor2 = new Proveedor(2L, "ELMATADO");
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedor, proveedor2));
        List<Proveedor> resultado = proveedorService.findAll();

        assertThat(resultado).hasSize(2).contains(proveedor, proveedor2);
        verify(proveedorRepository).findAll();
    }
        
    @Test
    void testFindById() {
        Proveedor proveedor = new Proveedor(1L, "ELMATADERO");
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));
        Proveedor resultado = proveedorService.findById(1L);

        assertThat(resultado).isEqualTo(proveedor);
        verify(proveedorRepository).findById(1L);
    }

    @Test
    void testUpdateById() {
        Proveedor proveedor = new Proveedor(1L, "ELMATADERO");
        Proveedor proveedorUpd = new Proveedor(1L, "ELMATADOR ACTUALIZADO");
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));
        
        Proveedor resultado = proveedorService.updateById(1L, proveedorUpd);

        assertEquals("ELMATADOR ACTUALIZADO", resultado.getNombreProveedor());
        verify(proveedorRepository).findById(1L);
        verify(proveedorRepository).save(proveedor);
    }

    @Test
    void testGetAll() {
        Proveedor proveedor = new Proveedor(1L, "ELMATADERO");
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedor));
        List<Proveedor> resultado = proveedorService.getAll();
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0)).isEqualTo(proveedor);
        verify(proveedorRepository).findAll(); 
    }

    @Test
    void testGetAllEmpty() {
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList());
        List<Proveedor> resultado = proveedorService.getAll();
        assertThat(resultado).isEmpty();
        verify(proveedorRepository).findAll();
    }
    @Test
    void testGetAllNull() {
        when(proveedorRepository.findAll()).thenReturn(null);
        List<Proveedor> resultado = proveedorService.getAll();
        assertThat(resultado).isEmpty();
        verify(proveedorRepository).findAll();
    }

    @Test
    void testGetAllWithNullValues() {
        Proveedor proveedor = new Proveedor(1L, null);
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedor));
        List<Proveedor> resultado = proveedorService.getAll();
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNombreProveedor()).isNull();
        verify(proveedorRepository).findAll();
    }

    @Test
    void testUpdateByIdNotFound(){
        Proveedor proveedor = new Proveedor(1L, "nombre generico");
        when(proveedorRepository.findById(1L)).thenReturn(Optional.empty());
        Proveedor resultado = proveedorService.updateById(1L, proveedor);
        assertThat(resultado).isNull();
        verify(proveedorRepository).findById(1L);
    }

    @Test
    void testUpdateByIdWithNullFields() {
        Proveedor proveedor = new Proveedor(1L, "Nombre generico");
        Proveedor proveedorUpd = new Proveedor(null, null);
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));

        Proveedor resultado = proveedorService.updateById(1L, proveedorUpd);

        assertEquals("Nombre generico", resultado.getNombreProveedor());
        verify(proveedorRepository).save(proveedor);
        
    }
}
