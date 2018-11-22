
CREATE OR REPLACE FUNCTION getTop10(page INTEGER, lim INTEGER) RETURNS TABLE (id INTEGER, title TEXT, rating FLOAT)
AS $$
    DECLARE res (id INTEGER, title TEXT, rating FLOAT);
    BEGIN
        res := (SELECT g.id, g.title, AVG(r.rating) FROM game_entity AS g JOIN rating_entity AS r ON g.id = r.game_id GROUP BY g.id LIMIT lim);
        RETURN res;
    END;
$$
LANGUAGE PlPgSQL;
