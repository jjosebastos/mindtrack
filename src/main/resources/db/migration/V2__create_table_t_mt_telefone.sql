CREATE TABLE t_mt_telefone(
    id_telefone SERIAL PRIMARY KEY,
    id_user INTEGER NOT NULL,
    nr_telefone VARCHAR(20),
    tp_telefone VARCHAR(15),
    CONSTRAINT FK_T_MTU_USER_TELEFONE
        FOREIGN KEY (id_user)
        REFERENCES t_mt_user(id_user)
);