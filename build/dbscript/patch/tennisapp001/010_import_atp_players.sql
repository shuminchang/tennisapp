--psql
\encoding UTF8
\timing on
\copy atp_player (player_id, name_first, name_last, hand, dob, ioc, height, wikidata_id) FROM '~/github/tennis_atp/atp_players.csv' DELIMITER ',' CSV HEADER;
\timing off

ALTER TABLE atp_player ADD COLUMN age INT;

UPDATE atp_player
SET age = EXTRACT(YEAR FROM AGE(CURRENT_DATE, TO_DATE(dob, 'YYYYMMDD')));