CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  hashed_password VARCHAR(255) NOT NULL,
  full_name VARCHAR(255) NOT NULL
);

CREATE TABLE riads (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  city VARCHAR(255),
  address VARCHAR(255),
  description TEXT,
  base_price_per_night NUMERIC(10,2),
  currency VARCHAR(3),
  amenities VARCHAR(500)
);

CREATE TABLE prestations (
  id BIGSERIAL PRIMARY KEY,
  type VARCHAR(50) NOT NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  base_price NUMERIC(10,2),
  currency VARCHAR(10),
  city VARCHAR(255)
);

INSERT INTO users (email, hashed_password, full_name) VALUES
('alice@example.com', 'hash1', 'Alice Wonderland'),
('bob@example.com', 'hash2', 'Bob Builder'),
('carol@example.com', 'hash3', 'Carol Wave');

INSERT INTO riads (name, city, address, description, base_price_per_night, currency, amenities) VALUES
('Riad Soleil', 'Marrakech', '1 Rue Rouge', 'Cozy riad near the souks.', 120.00, 'MAD', 'pool,terrace'),
('Riad Azure', 'Essaouira', '12 Avenue de la Mer', 'Ocean views and salt-air breezes.', 150.50, 'MAD', 'HAMMAM,spa'),
('Riad Palmier', 'Agadir', '5 Boulevard de la Plage', 'Modern comforts with palm-tree courtyard.', 95.00, 'MAD', 'garden,parking');

INSERT INTO prestations (type, name, description, base_price, currency, city) VALUES
('GUIDE', 'City Walking Tour', 'Guided walk through historic medina streets.', 45.00, 'EUR', 'Marrakech'),
('SPA', 'Desert Spa Ritual', 'Mangrove-inspired hammam and scented oils.', 80.00, 'MAD', 'Agadir'),
('ACTIVITY', 'Atlas Day Trek', 'Private hiking excursion into the High Atlas.', 110.00, 'MAD', 'Marrakech');
