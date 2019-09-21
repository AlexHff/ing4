-- Drop the table if it already exists
DROP TABLE IF EXISTS CoursSuivi;
DROP TABLE IF EXISTS Cours;
DROP TABLE IF EXISTS Eleve;

-- Eleve ([id], nom, prenom, annee)
CREATE TABLE Eleve
(
    id INT NOT NULL PRIMARY KEY,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    annee INT
);

-- Cours ([id], nom, annee, volumeHoraire)
CREATE TABLE Cours
(
    id INT NOT NULL PRIMARY KEY,
    nom VARCHAR(255),
    annee INT,
    volumeHoraire INT
);

-- CoursSuivi ([#idEleve, #idCours], nomEleve, prenomEleve, nomCours, note)
CREATE TABLE CoursSuivi
(
    idEleve INT NOT NULL,
    idCours INT NOT NULL,
    nomEleve VARCHAR(255),
    prenomEleve VARCHAR(255),
    nomCours VARCHAR(255),
    note FLOAT,
    PRIMARY KEY (idEleve, idCours),
    FOREIGN KEY (idEleve) REFERENCES Eleve(id),
    FOREIGN KEY (idCours) REFERENCES Cours(id)
);

-- Insert rows into table 'Eleve'
INSERT INTO Eleve
(
    id, nom, prenom, annee
)
VALUES
(
    123456789, 'DUPONT', 'Salim', 4
),
(
    234567891, 'WENGER', 'Friedrich', 1
);

-- Insert rows into table 'Cours'
INSERT INTO Cours
(
    id, nom, annee, volumeHoraire
)
VALUES
(
    40013, 'Base de donnees', 2019, 30
),
(
    40014, 'Technologies web', 2019, 27
);

-- Insert rows into table 'Eleve'
INSERT INTO Eleve
(
    id, nom, prenom, annee
)
VALUES
(
    123456788, 'MONTAGNO', 'Gilberto', NULL
);

-- Insert rows into table 'Eleve'
INSERT INTO Eleve
(
    id, nom, prenom
)
VALUES
(
    123456787, 'LEBOSS', 'Bob'
);

-- Update rows in table 'Cours'
UPDATE Cours
SET
    volumeHoraire = 27
WHERE
    nom = 'Base de donnees';

-- Update rows in table 'Cours'
UPDATE Cours
SET
    nom = 'Base de donnees avancees'
WHERE
    nom = 'Base de donnees';

-- Insert rows into table 'Eleve'
INSERT INTO Eleve
(
    id, nom, prenom, annee
)
VALUES
(
    234567891, 'WENGER', 'Friedrich', 1
);
-- #1062 - Duplicata du champ '234567891' pour la clef 'PRIMARY'

-- Insert rows into table 'Eleve'
INSERT INTO Eleve
(
    id, nom, prenom, annee
)
VALUES
(
    NULL, 'WENGER', 'Friedrich', 1
);
-- #1048 - Le champ 'id' ne peut être vide (null)
