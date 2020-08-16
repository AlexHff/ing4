DROP TABLE IF EXISTS Frequents;
DROP TABLE IF EXISTS Likes;
DROP TABLE IF EXISTS Sells;
DROP TABLE IF EXISTS Drinkers;
DROP TABLE IF EXISTS Beers;
DROP TABLE IF EXISTS Bars;

-- Create the table
CREATE TABLE Beers
(
    name VARCHAR(50) NOT NULL PRIMARY KEY,
    manf VARCHAR(50)
);

-- Create a new table called Bars
CREATE TABLE Bars
( 
    name VARCHAR(50) NOT NULL PRIMARY KEY,
    addr VARCHAR(50),
    license VARCHAR(50)
);


-- Create a new table called 'Drinkers' in schema 'SchemaName'
CREATE TABLE Drinkers
(
    name VARCHAR(50) NOT NULL PRIMARY KEY,
    addr VARCHAR(50),
    phone INT(11)
);

-- Create a new table called 'Likes
CREATE TABLE Likes
(
    drinker VARCHAR(50) NOT NULL,
    beer VARCHAR(50) NOT NULL,
    PRIMARY KEY (drinker, beer),
    FOREIGN KEY (drinker) REFERENCES Drinkers(name),
    FOREIGN KEY (beer) REFERENCES Beers(name)
);

-- Create a new table called 'Sells' in schema 'SchemaName'
CREATE TABLE Sells
(
    bar VARCHAR(50) NOT NULL,
    beer VARCHAR(50) NOT NULL,
    price FLOAT,
    PRIMARY KEY (bar, beer),
    FOREIGN KEY (bar) REFERENCES Bars(name),
    FOREIGN key (beer) REFERENCES Beers(name)
);

-- Create a new table called 'Frequents' in schema 'SchemaName'
CREATE TABLE Frequents
(
    drinker VARCHAR(50) NOT NULL,
    bar VARCHAR(50) NOT NULL,
    PRIMARY KEY (drinker, bar),
    FOREIGN KEY (drinker) REFERENCES Drinkers(name),
    FOREIGN KEY (bar) REFERENCES Bars(name)
);

-- Insert rows into table 'Beers'
INSERT INTO Beers
VALUES
(
    'Bud', 'Anheuser-Busch'
),
(
    'Bud-Lite', 'Anheuser-Busch'
),
(
    'Michelob', 'Anheuser-Busch'
),
(
    'Miller', 'Molson Coors'
);

-- Insert rows into table 'Bars'
INSERT INTO Bars
VALUES
(
    'Joe''s', 'Maple st', NULL
),
(
    'Sue''s', 'Bond st', NULL
);

-- Insert rows into table 'Sells
INSERT INTO Sells
VALUES
(
    'Joe''s', 'Bud', 10.0
),
(
    'Sue''s', 'Miller', 12.0
);