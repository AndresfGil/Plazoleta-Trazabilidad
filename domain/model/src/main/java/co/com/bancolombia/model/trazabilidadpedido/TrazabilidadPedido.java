package co.com.bancolombia.model.trazabilidadpedido;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TrazabilidadPedido {
    private String id;
    private Long idPedido;
    private Long idCliente;
    private String estadoAnterior;
    private String estadoNuevo;
    private LocalDateTime fechaCambio;
}
