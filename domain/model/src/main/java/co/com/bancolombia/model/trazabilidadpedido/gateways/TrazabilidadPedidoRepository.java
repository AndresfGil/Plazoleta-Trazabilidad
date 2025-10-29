package co.com.bancolombia.model.trazabilidadpedido.gateways;

import co.com.bancolombia.model.trazabilidadpedido.TrazabilidadPedido;
import java.util.List;

public interface TrazabilidadPedidoRepository {
    TrazabilidadPedido guardar(TrazabilidadPedido trazabilidadPedido);
    List<TrazabilidadPedido> consultarPorCliente(Long idCliente);
    List<TrazabilidadPedido> consultarPorPedido(Long idPedido);
}
