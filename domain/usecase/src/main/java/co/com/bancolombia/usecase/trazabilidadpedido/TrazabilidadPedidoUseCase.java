package co.com.bancolombia.usecase.trazabilidadpedido;

import co.com.bancolombia.model.trazabilidadpedido.TrazabilidadPedido;
import co.com.bancolombia.model.trazabilidadpedido.gateways.TrazabilidadPedidoRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TrazabilidadPedidoUseCase {
    
    private final TrazabilidadPedidoRepository trazabilidadPedidoRepository;
    
    public TrazabilidadPedido registrarCambioEstado(Long idPedido, Long idCliente, 
                                                   String estadoAnterior, String estadoNuevo) {
        TrazabilidadPedido trazabilidad = TrazabilidadPedido.builder()
                .idPedido(idPedido)
                .idCliente(idCliente)
                .estadoAnterior(estadoAnterior)
                .estadoNuevo(estadoNuevo)
                .fechaCambio(LocalDateTime.now())
                .build();
                
        return trazabilidadPedidoRepository.guardar(trazabilidad);
    }
    
    public List<TrazabilidadPedido> consultarTrazabilidadPorCliente(Long idCliente) {
        return trazabilidadPedidoRepository.consultarPorCliente(idCliente);
    }
    
    public List<TrazabilidadPedido> consultarTrazabilidadPorPedido(Long idPedido) {
        return trazabilidadPedidoRepository.consultarPorPedido(idPedido);
    }
}
