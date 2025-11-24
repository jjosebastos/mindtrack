CREATE TABLE IF NOT EXISTS t_mt_user (
     id_user SERIAL PRIMARY KEY,
     id_username VARCHAR(40), -- Login/Username
     nm_first VARCHAR(40) NOT NULL,
     nm_last VARCHAR(40) NOT NULL,
     ad_email VARCHAR(255) NOT NULL,
     pw_security VARCHAR(72),
     us_role CHAR(5) NOT NULL,
     au_provider VARCHAR(20),
     vl_lang VARCHAR(5),
     ts_creation TIMESTAMP DEFAULT NOW(),
     ts_update TIMESTAMP,
     CONSTRAINT CK_USER_ROLE CHECK (us_role in ('ADMIN', 'BASIC')),
     CONSTRAINT ck_user_lang CHECK (vl_lang IN ('pt_BR', 'en_US'))
);


CREATE TABLE IF NOT EXISTS t_mt_telefone (
    id_telefone SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    nr_telefone VARCHAR(20),
    tp_telefone VARCHAR(15),

    CONSTRAINT FK_T_MTU_USER_TELEFONE
        FOREIGN KEY (id_user)
        REFERENCES t_mt_user(id_user)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS t_mt_humor (
    id_humor SERIAL PRIMARY KEY,
    st_humor VARCHAR(6) NOT NULL,
    ds_comentario VARCHAR(200) NOT NULL,
    id_user INTEGER NOT NULL,
    ts_registro TIMESTAMP DEFAULT NOW(),

    CONSTRAINT CK_T_MT_HUMOR_ST_HUMOR
        CHECK (st_humor in ('FELIZ', 'NEUTRO', 'TRISTE', 'RAIVA')),
    CONSTRAINT FK_T_MTU_USER_HUMOR
        FOREIGN KEY (id_user)
        REFERENCES t_mt_user(id_user)
        ON DELETE CASCADE
);