ALTER TABLE t_mt_user
ADD COLUMN vl_lang VARCHAR(5),
ADD CONSTRAINT ck_user_lang CHECK (vl_lang IN ('pt_BR', 'en_US'));