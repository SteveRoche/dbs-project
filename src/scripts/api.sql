Prepared Statements

SELECT id, name FROM Movie;
SELECT * from Movie WHERE id=?;

SELECT name FROM Acted INNER JOIN Actor ON id=actor_id WHERE movie_id=?;
SELECT id, name FROM Movie WHERE UTL_MATCH.EDIT_DISTANCE(name, ?) < 6;
SELECT D.name FROM Director D INNER JOIN Movie M ON director_id=D.id WHERE M.id=?;

Callable Statements

BEGIN rate(?, ?, ?); END;