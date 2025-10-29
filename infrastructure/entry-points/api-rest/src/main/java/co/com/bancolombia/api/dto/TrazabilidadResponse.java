package co.com.bancolombia.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response con la información de trazabilidad de un pedido")
public class TrazabilidadResponse {
    
    @Schema(description = "ID único de la trazabilidad", example = "507f1f77bcf86cd799439011")
    private String id;
    
    @Schema(description = "ID del pedido", example = "123")
    private Long idPedido;
    
    @Schema(description = "ID del cliente", example = "456")
    private Long idCliente;
    
    @Schema(description = "Estado anterior del pedido", example = "PENDIENTE")
    private String estadoAnterior;
    
    @Schema(description = "Estado nuevo del pedido", example = "EN_PREPARACION")
    private String estadoNuevo;
    
    @Schema(description = "Fecha y hora del cambio de estado", example = "2024-01-15T10:30:00")
    private LocalDateTime fechaCambio;
}
