CREATE TABLE restaurante_usuario_responsavel (
	restaurante_id BIGINT NOT NULL,
	usuario_id BIGINT NOT NULL,
	CONSTRAINT pk_restaurante_usuario_responsavel PRIMARY KEY (restaurante_id, usuario_id),
	CONSTRAINT fk_restaurante_usuario_responsavel_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),
	CONSTRAINT fk_restaurante_usuario_responsavel_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;