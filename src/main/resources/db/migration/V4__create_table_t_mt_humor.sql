CREATE TABLE t_mt_humor(
    id_humor SERIAL PRIMARY KEY,
    st_humor VARCHAR(6) NOT NULL,
    ds_comentario VARCHAR(200) NOT NULL,
    id_user INTEGER NOT NULL,
    ts_registro TIMESTAMP,
    CONSTRAINT CK_T_MT_HUMOR_ST_HUMOR
    CHECK (st_humor in ('FELIZ', 'NEUTRO', 'TRISTE', 'RAIVA')),
    CONSTRAINT FK_T_MTU_USER_HUMOR
    FOREIGN KEY (id_user) REFERENCES t_mt_user(id_user)
)