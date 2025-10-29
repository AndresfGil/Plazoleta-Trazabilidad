# API de Trazabilidad de Pedidos

Este microservicio maneja la trazabilidad de cambios de estado de pedidos en el sistema Plazoleta.

## 🚀 Swagger UI

Una vez que el servicio esté ejecutándose, puedes acceder a la documentación interactiva de Swagger en:

**http://localhost:8083/swagger-ui.html**

## Endpoints Disponibles

### 1. Registrar Trazabilidad (Simple)
**POST** `/api/trazabilidad/registrar-simple`

Registra un cambio de estado usando parámetros de URL (compatible con TrazabilidadHttpAdapter).

**Query Parameters:**
- `idPedido`: ID del pedido
- `idCliente`: ID del cliente
- `estadoAnterior`: Estado anterior
- `estadoNuevo`: Estado nuevo

**Response:**
```
Trazabilidad registrada exitosamente
```

### 2. Consultar Trazabilidad por Cliente
**GET** `/api/trazabilidad/cliente/{idCliente}`

Obtiene todos los cambios de estado de pedidos para un cliente específico.

**Response:**
```json
[
    {
        "id": "generated-id",
        "idPedido": 123,
        "idCliente": 456,
        "estadoAnterior": "PENDIENTE",
        "estadoNuevo": "EN_PREPARACION",
        "fechaCambio": "2024-01-15T10:30:00"
    }
]
```

### 3. Consultar Trazabilidad por Pedido
**GET** `/api/trazabilidad/pedido/{idPedido}`

Obtiene todos los cambios de estado para un pedido específico.

**Response:**
```json
[
    {
        "id": "generated-id",
        "idPedido": 123,
        "idCliente": 456,
        "estadoAnterior": "PENDIENTE",
        "estadoNuevo": "EN_PREPARACION",
        "fechaCambio": "2024-01-15T10:30:00"
    }
]
```

## Configuración

- **Puerto**: 8083
- **Base de datos**: MongoDB en `mongodb://localhost:27017/trazabilidad`
- **Colección**: `trazabilidad_pedidos`
- **Swagger UI**: http://localhost:8083/swagger-ui.html

## Integración con Plazoleta

Para integrar con el microservicio de Plazoleta, actualiza la URL en el `TrazabilidadHttpAdapter`:

```java
@Value("${trazabilidad.service.url:http://localhost:8083}")
private String trazabilidadServiceUrl;
```

Y usa el endpoint `/api/trazabilidad/registrar-simple` para la integración simple.
