package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.TrazabilidadResponse;
import co.com.bancolombia.model.trazabilidadpedido.TrazabilidadPedido;
import co.com.bancolombia.usecase.trazabilidadpedido.TrazabilidadPedidoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/trazabilidad", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Tag(name = "Trazabilidad de Pedidos", description = "API para el manejo de trazabilidad de cambios de estado de pedidos")
public class ApiRest {

    private final TrazabilidadPedidoUseCase trazabilidadPedidoUseCase;

    @Operation(
        summary = "Registrar cambio de estado de pedido", 
        description = "Registra un cambio de estado de pedido usando parámetros de URL. Requiere autenticación JWT con rol CLIENTE.",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trazabilidad registrada exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT inválido o faltante"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado - Se requiere rol CLIENTE"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/registrar-simple")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<String> registrarTrazabilidadSimple(
            @RequestParam("idPedido") Long idPedido,
            @RequestParam("idCliente") Long idCliente,
            @RequestParam("estadoAnterior") String estadoAnterior,
            @RequestParam("estadoNuevo") String estadoNuevo) {
        
        try {
            log.info("Registrando trazabilidad - Pedido: {}, Cliente: {}, Estado: {} -> {}", 
                    idPedido, idCliente, estadoAnterior, estadoNuevo);
            
            TrazabilidadPedido trazabilidad = trazabilidadPedidoUseCase.registrarCambioEstado(
                    idPedido,
                    idCliente,
                    estadoAnterior,
                    estadoNuevo
            );
            
            log.info("Trazabilidad registrada exitosamente con ID: {}", trazabilidad.getId());
            
            return ResponseEntity.ok("Trazabilidad registrada exitosamente");
        } catch (Exception e) {
            log.error("Error al registrar trazabilidad para pedido {}: {}", idPedido, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar trazabilidad: " + e.getMessage());
        }
    }

    @Operation(
        summary = "Consultar trazabilidad por cliente", 
        description = "Obtiene todos los cambios de estado de pedidos para un cliente específico. Requiere autenticación JWT con rol CLIENTE.",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de trazabilidades obtenida exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT inválido o faltante"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado - Se requiere rol CLIENTE")
    })
    @GetMapping("/cliente/{idCliente}")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<List<TrazabilidadResponse>> consultarTrazabilidadPorCliente(
            @PathVariable("idCliente") Long idCliente) {
        
        List<TrazabilidadPedido> trazabilidades = trazabilidadPedidoUseCase.consultarTrazabilidadPorCliente(idCliente);
        List<TrazabilidadResponse> responses = trazabilidades.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }

    @Operation(
        summary = "Consultar trazabilidad por pedido", 
        description = "Obtiene todos los cambios de estado para un pedido específico. Requiere autenticación JWT con rol CLIENTE.",
        security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de trazabilidades obtenida exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado - Token JWT inválido o faltante"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado - Se requiere rol CLIENTE")
    })
    @GetMapping("/pedido/{idPedido}")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<List<TrazabilidadResponse>> consultarTrazabilidadPorPedido(
            @PathVariable("idPedido") Long idPedido) {
        
        List<TrazabilidadPedido> trazabilidades = trazabilidadPedidoUseCase.consultarTrazabilidadPorPedido(idPedido);
        List<TrazabilidadResponse> responses = trazabilidades.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }

    private TrazabilidadResponse mapToResponse(TrazabilidadPedido trazabilidad) {
        return TrazabilidadResponse.builder()
                .id(trazabilidad.getId())
                .idPedido(trazabilidad.getIdPedido())
                .idCliente(trazabilidad.getIdCliente())
                .estadoAnterior(trazabilidad.getEstadoAnterior())
                .estadoNuevo(trazabilidad.getEstadoNuevo())
                .fechaCambio(trazabilidad.getFechaCambio())
                .build();
    }
}
