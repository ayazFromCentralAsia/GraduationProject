INSERT INTO users (id, username, email, password, phone, role) VALUES
('11111111-1111-1111-1111-111111111111', 'john_doe', 'john@example.com', 'password123', '1234567890', 'USER'),
('22222222-2222-2222-2222-222222222222', 'jane_smith', 'jane@example.com', 'password123', '0987654321', 'USER'),
('33333333-3333-3333-3333-333333333333', 'admin', 'admin@example.com', 'adminpass', '5555555555', 'ADMIN');

INSERT INTO couriers (id, username, email, phone) VALUES
('44444444-4444-4444-4444-444444444444', 'courier_1', 'courier1@example.com', '1111111111'),
('55555555-5555-5555-5555-555555555555', 'courier_2', 'courier2@example.com', '2222222222');

INSERT INTO addresses (id, user_id, city, street, house_number, apartment, postal_code) VALUES
('66666666-6666-6666-6666-666666666666', '11111111-1111-1111-1111-111111111111', 'New York', '5th Avenue', '10', '101', '10001'),
('77777777-7777-7777-7777-777777777777', '11111111-1111-1111-1111-111111111111', 'Los Angeles', 'Sunset Blvd', '20', NULL, '90001'),
('88888888-8888-8888-8888-888888888888', '22222222-2222-2222-2222-222222222222', 'Chicago', 'Michigan Ave', '30', '202', '60601');

INSERT INTO parcels (id, user_id, courier_id, parcel_status, creation_date, pickup_address_id, delivery_address_id, weight) VALUES
('99999999-9999-9999-9999-999999999999', '11111111-1111-1111-1111-111111111111', '44444444-4444-4444-4444-444444444444', 'IN_TRANSIT', '2024-10-12 10:00:00', '66666666-6666-6666-6666-666666666666', '77777777-7777-7777-7777-777777777777', 5.75),
('10101010-1010-1010-1010-101010101010', '22222222-2222-2222-2222-222222222222', '55555555-5555-5555-5555-555555555555', 'PENDING', '2024-10-12 12:30:00', '88888888-8888-8888-8888-888888888888', '66666666-6666-6666-6666-666666666666', 12.30);
