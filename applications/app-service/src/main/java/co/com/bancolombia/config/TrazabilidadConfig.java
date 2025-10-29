package co.com.bancolombia.config;

import co.com.bancolombia.model.trazabilidadpedido.gateways.TrazabilidadPedidoRepository;
import co.com.bancolombia.usecase.trazabilidadpedido.TrazabilidadPedidoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrazabilidadConfig {

    @Bean
    public TrazabilidadPedidoUseCase trazabilidadPedidoUseCase(TrazabilidadPedidoRepository trazabilidadPedidoRepository) {
        return new TrazabilidadPedidoUseCase(trazabilidadPedidoRepository);
    }
}
