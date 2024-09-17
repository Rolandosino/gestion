CREATE TABLE IF NOT EXISTS role (
                                        id SERIAL PRIMARY KEY,
                                        libelle VARCHAR(255) NOT NULL
    );
INSERT INTO role (libelle)
VALUES
    ('UTILISATEUR'),
    ('MANAGER'),
    ('ADMINISTRATEUR');