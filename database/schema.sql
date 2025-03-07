-- Drop existing tables if they exist (optional, use carefully)
DROP TABLE IF EXISTS devices, energy_usage, users CASCADE;

-- Users Table (Stores user information)
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) CHECK (role IN ('ADMIN', 'USER')) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Devices Table (Stores IoT device information)
CREATE TABLE devices (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    device_name VARCHAR(100) NOT NULL,
    device_type VARCHAR(50),
    status VARCHAR(20) CHECK (status IN ('ONLINE', 'OFFLINE')) NOT NULL DEFAULT 'OFFLINE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Energy Usage Table (Stores energy consumption logs)
CREATE TABLE energy_usage (
    id SERIAL PRIMARY KEY,
    device_id INT REFERENCES devices(id) ON DELETE CASCADE,
    energy_consumed_kwh DECIMAL(10,2) NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

