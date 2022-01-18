CREATE TABLE forma_pagamento (
	id BIGINT NOT NULL AUTO_INCREMENT,
	descricao VARCHAR(60) NOT NULL,
	CONSTRAINT pk_forma_pagamento PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE grupo (
	id BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(60) NOT NULL,
	CONSTRAINT pk_grupo PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE grupo_permissao (
	grupo_id BIGINT NOT NULL,
	permissao_id BIGINT NOT NULL,
	CONSTRAINT pk_grupo_permissao PRIMARY KEY (grupo_id, permissao_id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE permissao (
	id BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	descricao VARCHAR(100) NOT NULL,
	CONSTRAINT pk_permissao PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE produto (
	id BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	preco DECIMAL(19,4) NOT NULL,
	ativo TINYINT(1) NOT NULL,
	restaurante_id BIGINT NOT NULL,
	CONSTRAINT pk_produto PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE restaurante (
	id BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	taxa_frete DECIMAL(19,4) NOT NULL,
	data_atualizacao DATETIME NOT NULL,
	data_cadastro DATETIME NOT NULL,
	endereco_logradouro VARCHAR(100),
	endereco_numero VARCHAR(50),
	endereco_complemento VARCHAR(100),
	endereco_bairro VARCHAR(100),
	endereco_cep VARCHAR(8),
	endereco_cidade_id BIGINT,
	cozinha_id BIGINT NOT NULL,
	CONSTRAINT pk_restaurante PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE restaurante_forma_pagamento (
	restaurante_id BIGINT NOT NULL,
	forma_pagamento_id BIGINT NOT NULL,
	CONSTRAINT pk_restaurante_forma_pagamento PRIMARY KEY (restaurante_id, forma_pagamento_id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE usuario (
	id BIGINT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	senha VARCHAR(255) NOT NULL,
	data_cadastro DATETIME NOT NULL,
	CONSTRAINT pk_usuario PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE usuario_grupo (
	usuario_id BIGINT NOT NULL,
	grupo_id BIGINT NOT NULL,
	CONSTRAINT pk_usuario_grupo PRIMARY KEY (usuario_id, grupo_id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

ALTER TABLE grupo_permissao ADD CONSTRAINT fk_grupo_permissao_permissao FOREIGN KEY (permissao_id) REFERENCES permissao (id);
ALTER TABLE grupo_permissao ADD CONSTRAINT fk_grupo_permissao_grupo FOREIGN KEY (grupo_id) REFERENCES grupo (id);
ALTER TABLE produto ADD CONSTRAINT fk_produto_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);
ALTER TABLE restaurante ADD CONSTRAINT fk_restaurante_cozinha FOREIGN KEY (cozinha_id) REFERENCES cozinha (id);
ALTER TABLE restaurante ADD CONSTRAINT fk_restaurante_cidade FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id);
ALTER TABLE restaurante_forma_pagamento ADD CONSTRAINT fk_restaurante_forma_pagamento_forma_pagamento FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id);
ALTER TABLE restaurante_forma_pagamento ADD CONSTRAINT fk_restaurante_forma_pagamento_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);
ALTER TABLE usuario_grupo ADD CONSTRAINT fk_usuario_grupo_grupo FOREIGN KEY (grupo_id) REFERENCES grupo (id);
ALTER TABLE usuario_grupo ADD CONSTRAINT fk_usuario_grupo_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id);