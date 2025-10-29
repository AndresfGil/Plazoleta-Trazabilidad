package co.com.bancolombia.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarTrazabilidadRequest {
    @NotNull(message = "El ID del pedido es obligatorio")
    private Long idPedido;
    
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;
    
    @NotNull(message = "El estado anterior es obligatorio")
    private String estadoAnterior;
    
    @NotNull(message = "El estado nuevo es obligatorio")
    private String estadoNuevo;
}
