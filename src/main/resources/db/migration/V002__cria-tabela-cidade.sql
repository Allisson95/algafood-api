CREATE TABLE cidade (
	id BIGINT NOT NULL AUTO_INCREMENT,
	nome_cidade VARCHAR(80) NOT NULL,
	nome_estado VARCHAR(80) NOT NULL,
	CONSTRAINT pk_cidade PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;