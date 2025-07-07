package com.ecomarket.proveedor.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ecomarket.proveedor.model.Proveedor;
import com.ecomarket.proveedor.service.ProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

@WebMvcTest(ProveedorController.class)
public class ProveedorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProveedorService proveedorService;

    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearProveedor() throws Exception{
        Proveedor proveedor = new Proveedor(1L, "ELMATADERO");
        Mockito.when(proveedorService.crearProveedor(Mockito.any(Proveedor.class))).thenReturn(proveedor);
        
        mockMvc.perform(post("/api/proveedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreProveedor").value("ELMATADERO"));
    }

    @Test
    void testListarProveedores() throws Exception {
        Proveedor proveedor = new Proveedor(1L, "ELMATADERO");
        Proveedor proveedor2 = new Proveedor(1L, "ELMATADO");
        Mockito.when(proveedorService.findAll()).thenReturn(Arrays.asList(proveedor, proveedor2));
        mockMvc.perform(get("/api/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreProveedor").value("ELMATADERO"))
                .andExpect(jsonPath("$[1].nombreProveedor").value("ELMATADO"));
    }
    
    @Test
    void testGetById() throws Exception {
        Proveedor proveedor = new Proveedor(1L, "ELMATADERO");
        Mockito.when(proveedorService.findById(1L)).thenReturn(proveedor);
        mockMvc.perform(get("/api/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProveedor").value("ELMATADERO"));
    }

    @Test
    void testUpdateById() throws Exception{
        Proveedor proveedor = new Proveedor(1L, "ELMATADERO");
        Proveedor proveedorUpd = new Proveedor(null, "ELMATAO");
        Mockito.when(proveedorService.updateById(1L, proveedor)).thenReturn(proveedorUpd);
        mockMvc.perform(put("/api/proveedores/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreProveedor").value("ELMATAO"));
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        Mockito.when(proveedorService.findById(9999999L)).thenReturn(null);
        mockMvc.perform(get("/api/proveedores/9999999"))
                .andExpect(status().isNotFound()); 
    }

    @Test
    void testGetAllNoContent() throws Exception {
        when(proveedorService.findAll()).thenReturn(Arrays.asList());
        mockMvc.perform(get("/api/proveedores"))
            .andExpect(status().isNoContent());
    }

    @Test
    void testCrearProveedorConflict() throws Exception{
        Proveedor proveedor = new Proveedor(1L, "ELMATADERO");
        when(proveedorService.crearProveedor(Mockito.any(Proveedor.class))).thenThrow(new RuntimeException("Error al crear proveedor"));
        mockMvc.perform(post("/api/proveedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isConflict());
    }

    @Test
    void testUpdateByIdNotFound() throws Exception{
        Proveedor proveedor = new Proveedor(1L, "ELMATADERO");
        when(proveedorService.updateById(1L, proveedor)).thenReturn(null);
        mockMvc.perform(put("/api/proveedores/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(proveedor)))
                .andExpect(status().isNotFound());
    
    }
}
