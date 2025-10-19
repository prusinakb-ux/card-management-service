CREATE TABLE Client
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    oib        VARCHAR(11) NOT NULL UNIQUE,
    status     VARCHAR(20) NOT NULL,
    user_created   VARCHAR(255) NOT NULL,
    user_modified  VARCHAR(255),
    date_created   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_modified  TIMESTAMP
);
);