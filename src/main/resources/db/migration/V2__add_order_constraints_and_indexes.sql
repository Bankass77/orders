ALTER TABLE orders
    ADD CONSTRAINT ck_orders_status
        CHECK (status IN ('NEW', 'PAID', 'CANCELLED'));

CREATE INDEX idx_orders_created_at
    ON orders(created_at);

CREATE INDEX idx_orders_status_created_at
    ON orders(status, created_at);