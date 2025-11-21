

CREATE TABLE t_mt_user (
     id_user SERIAL PRIMARY KEY,
     id_username VARCHAR(40),
     nm_first VARCHAR(40) NOT NULL,
     nm_last VARCHAR(40) NOT NULL,
     ad_email VARCHAR(255) NOT NULL,
     pw_security VARCHAR(72),
     us_role CHAR(5) NOT NULL,
     au_provider VARCHAR(20),
     ts_creation TIMESTAMP DEFAULT NOW(),
     ts_update TIMESTAMP,
     CONSTRAINT CK_USER_ROLE
     CHECK (us_role in ('ADMIN', 'BASIC'))
);