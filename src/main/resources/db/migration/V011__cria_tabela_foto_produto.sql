CREATE TABLE foto_produto (
	produto_id BIGINT NOT NULL,
	nome_arquivo VARCHAR(150) NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	content_type VARCHAR(80) NOT NULL,
	tamanho INT NOT NULL,
	CONSTRAINT pk_foto_produto PRIMARY KEY (produto_id),
    CONSTRAINT fk_foto_produto_produto FOREIGN KEY (produto_id) REFERENCES produto (id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;