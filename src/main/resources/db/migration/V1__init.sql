CREATE TABLE colaborador
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    password_score    INT          NOT NULL,
    lider_id BIGINT
);

ALTER TABLE colaborador ADD FOREIGN KEY (lider_id) REFERENCES colaborador (id);