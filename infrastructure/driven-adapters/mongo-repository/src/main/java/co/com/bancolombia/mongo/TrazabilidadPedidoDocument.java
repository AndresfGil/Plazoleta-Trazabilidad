package co.com.bancolombia.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trazabilidad_pedidos")
public class TrazabilidadPedidoDocument {
    @Id
    private String id;
    private Long idPedido;
    private Long idCliente;
    private String estadoAnterior;
    private String estadoNuevo;
    private LocalDateTime fechaCambio;
}
