CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        public_id UUID NOT NULL UNIQUE,
                        status VARCHAR(20) NOT NULL,
                        created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                        created_by VARCHAR(100),
                        version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE order_lines (
                             id BIGSERIAL PRIMARY KEY,
                             order_id BIGINT NOT NULL REFERENCES orders(id),
                             sku VARCHAR(50) NOT NULL,
                             quantity INT NOT NULL CHECK (quantity > 0)
);