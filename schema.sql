-- 1. Tabela de Clientes
CREATE TABLE clientes (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(255) NOT NULL
);

-- 2. Tabela de Restaurantes
CREATE TABLE restaurantes (
    codigo INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especialidade VARCHAR(50) NOT NULL
);

-- 3. Tabela de Entregadores
-- Usamos a placa como Primary Key, pois é o identificador único usado no teu código atual
CREATE TABLE entregadores (
    placa_veiculo VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    disponivel BOOLEAN DEFAULT TRUE
);

-- 4. Tabelas de Produtos (Aplicando herança de Classes Abstratas)
CREATE TABLE produtos (
    codigo INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco NUMERIC(10, 2) NOT NULL
);

CREATE TABLE comidas (
    codigo_produto INT PRIMARY KEY,
    peso_gramas NUMERIC(10, 2) NOT NULL,
    CONSTRAINT fk_comida_produto FOREIGN KEY (codigo_produto) REFERENCES produtos(codigo) ON DELETE CASCADE
);

CREATE TABLE bebidas (
    codigo_produto INT PRIMARY KEY,
    volume_ml INT NOT NULL,
    CONSTRAINT fk_bebida_produto FOREIGN KEY (codigo_produto) REFERENCES produtos(codigo) ON DELETE CASCADE
);

-- 5. Tabela de Pedidos
-- Vincula o Cliente e o Entregador através de Foreign Keys (Chaves Estrangeiras)
CREATE TABLE pedidos (
    numero_pedido INT PRIMARY KEY,
    cpf_cliente VARCHAR(11) NOT NULL,
    placa_entregador VARCHAR(20), -- Pode ser nulo se o pedido ainda não saiu para entrega
    status VARCHAR(50) NOT NULL DEFAULT 'Aguardando Pagamento',
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cpf_cliente) REFERENCES clientes(cpf),
    CONSTRAINT fk_pedido_entregador FOREIGN KEY (placa_entregador) REFERENCES entregadores(placa_veiculo)
);

-- 6. Tabela Associativa: Itens do Pedido
-- Representa a classe ItemPedido, ligando os Pedidos aos Produtos
CREATE TABLE itens_pedido (
    numero_pedido INT NOT NULL,
    codigo_produto INT NOT NULL,
    quantidade INT NOT NULL CHECK (quantidade > 0),
    PRIMARY KEY (numero_pedido, codigo_produto),
    CONSTRAINT fk_item_pedido FOREIGN KEY (numero_pedido) REFERENCES pedidos(numero_pedido) ON DELETE CASCADE,
    CONSTRAINT fk_item_produto FOREIGN KEY (codigo_produto) REFERENCES produtos(codigo)
);