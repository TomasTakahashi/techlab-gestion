CREATE TABLE productos (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  precio DECIMAL(12,2) NOT NULL,
  stock INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT uk_productos_nombre UNIQUE (nombre),
  CONSTRAINT ck_productos_precio_positivo CHECK (precio > 0),
  CONSTRAINT ck_productos_stock_no_negativo CHECK (stock >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
