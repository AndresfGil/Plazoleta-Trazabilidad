package co.com.bancolombia.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface MongoDBRepository extends MongoRepository<TrazabilidadPedidoDocument, String> , QueryByExampleExecutor<TrazabilidadPedidoDocument> {
    List<TrazabilidadPedidoDocument> findByIdCliente(Long idCliente);
    List<TrazabilidadPedidoDocument> findByIdPedido(Long idPedido);
}
