CREATE TABLE Genre(
	name VARCHAR(25) PRIMARY KEY
);

CREATE TABLE Actor(
	id NUMBER(5) PRIMARY KEY,
	name VARCHAR(25) NOT NULL
);

CREATE TABLE Director(
	id NUMBER(5) PRIMARY KEY,
	NAME VARCHAR(25) NOT NULL
);

CREATE TABLE Movie(
	id NUMBER(5) PRIMARY KEY,
	name VARCHAR(25) NOT NULL,
	description VARCHAR(300),
	release_date DATE NOT NULL,
	director_id NUMBER(5) NOT NULL,
	FOREIGN KEY(director_id) REFERENCES director(id)
);

CREATE TABLE belongs(
	movie_id NUMBER(5),
	genre VARCHAR(25),
	PRIMARY KEY(movie_id, genre),
	FOREIGN KEY(movie_id) REFERENCES Movie(id),
	FOREIGN KEY(GENRE) REFERENCES Genre(name)
);

CREATE TABLE Acted(
	movie_id NUMBER(5),
	actor_id NUMBER(5),
	FOREIGN KEY(movie_id) REFERENCES Movie(id),
	FOREIGN KEY(actor_id) REFERENCES Actor(id)
);

CREATE TABLE MUser(
	username VARCHAR(25) PRIMARY KEY,
	password VARCHAR(25),
	admin NUMBER(1) CHECK(admin IN (0, 1))
);

CREATE TABLE Rated(
	username VARCHAR(25),
	movie_id NUMBER(5),
	rating NUMBER(3, 1),
	PRIMARY KEY(username, movie_id),
	FOREIGN KEY(username) REFERENCES MUser(username),
	FOREIGN KEY(movie_id) REFERENCES Movie(id)
);

