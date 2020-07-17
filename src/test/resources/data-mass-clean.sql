-- deleta o conteudo das tabelas
DELETE FROM anime;

-- reinicia o ID's, apartir do 01
ALTER SEQUENCE anime_id_seq RESTART WITH 1;