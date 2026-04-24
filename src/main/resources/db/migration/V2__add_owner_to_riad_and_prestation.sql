ALTER TABLE riads
    ADD COLUMN owner_id BIGINT;

ALTER TABLE riads
    ADD CONSTRAINT fk_riads_owner
        FOREIGN KEY (owner_id) REFERENCES users(id);

ALTER TABLE prestations
    ADD COLUMN owner_id BIGINT;

ALTER TABLE prestations
    ADD CONSTRAINT fk_prestations_owner
        FOREIGN KEY (owner_id) REFERENCES users(id);
