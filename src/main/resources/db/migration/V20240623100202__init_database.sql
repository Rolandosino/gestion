CREATE TABLE IF NOT EXISTS user_entity
(
    id
    SERIAL
    PRIMARY
    KEY,
    username
    VARCHAR(255),
    email_address
    VARCHAR(255),
    mot_de_passe
    VARCHAR(255),
    actif BOOLEAN,
    role_id
    integer ,
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL
    );
INSERT INTO user_entity(username, email_address, mot_de_passe, actif, is_deleted)
VALUES('admin','admin@gmail.com', '$2a$12$WsX0nWoDrDCWt7kFLV96iuBsA2k7f5A16eUJxnxY0WosdzELfZ9qi', true, false)
