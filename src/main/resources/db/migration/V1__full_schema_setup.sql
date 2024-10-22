CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS couriers (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS addresses (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    city VARCHAR(100) NOT NULL,
    street VARCHAR(100) NOT NULL,
    house_number VARCHAR(10) NOT NULL,
    apartment VARCHAR(10),
    postal_code VARCHAR(10) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS parcels (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    courier_id UUID,
    parcel_status VARCHAR(20),
    creation_date TIMESTAMP NOT NULL,
    pickup_address_id UUID NOT NULL,
    delivery_address_id UUID NOT NULL,
    weight DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (courier_id) REFERENCES couriers (id),
    FOREIGN KEY (pickup_address_id) REFERENCES addresses (id),
    FOREIGN KEY (delivery_address_id) REFERENCES addresses (id)
);
