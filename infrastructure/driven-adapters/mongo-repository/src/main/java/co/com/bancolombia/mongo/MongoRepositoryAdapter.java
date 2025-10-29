package co.com.bancolombia.mongo;

import co.com.bancolombia.mongo.helper.AdapterOperations;
import co.com.bancolombia.model.trazabilidadpedido.TrazabilidadPedido;
import co.com.bancolombia.model.trazabilidadpedido.gateways.TrazabilidadPedidoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import static java.util.stream.StreamSupport.stream;

@Repository
public class MongoRepositoryAdapter extends AdapterOperations<TrazabilidadPedido, TrazabilidadPedidoDocument, String, MongoDBRepository>
implements TrazabilidadPedidoRepository
{

    public MongoRepositoryAdapter(MongoDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, TrazabilidadPedido.class));
    }

    @Override
    public TrazabilidadPedido guardar(TrazabilidadPedido trazabilidadPedido) {
        return save(trazabilidadPedido);
    }

    @Override
    public List<TrazabilidadPedido> consultarPorCliente(Long idCliente) {
        return stream(repository.findByIdCliente(idCliente).spliterator(), false)
                .map(this::toEntity)
                .toList();
    }

    @Override
    public List<TrazabilidadPedido> consultarPorPedido(Long idPedido) {
        return stream(repository.findByIdPedido(idPedido).spliterator(), false)
                .map(this::toEntity)
                .toList();
    }
}
