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

ALTER TABLE Eleve DROP PRIMARY KEY;

ALTER TABLE Eleve CHANGE id id INT NULL;

ALTER TABLE Eleve ADD PRIMARY KEY(id);
--  #1062 - Duplicata du champ '234567891' pour la clef 'PRIMARY'

-- Insert rows into table 'CoursSuivi'
INSERT INTO CoursSuivi
(
    idEleve, idCours, nomEleve, prenomEleve, nomCours, note
)
VALUES
(
    NULL, 40013, 'WENGER', 'Friedrich', 'Base de donnees avancees', 19
);
-- #1048 - Le champ 'idEleve' ne peut être vide (null)

-- Insert rows into table 'CoursSuivi'
INSERT INTO CoursSuivi
(
    idEleve, idCours, nomEleve, prenomEleve, nomCours, note
)
VALUES
(
    29521651, 40013, 'WENGER', 'Friedrich', 'Base de donnees avancees', 19
);
-- #1452 - Cannot add or update a child row: a foreign key constraint fails (`hello`.`CoursSuivi`, CONSTRAINT `CoursSuivi_ibfk_1` FOREIGN KEY (`idEleve`) REFERENCES `Eleve` (`id`))

ALTER TABLE CoursSuivi DROP FOREIGN KEY idEleve;
ALTER TABLE CoursSuivi DROP FOREIGN KEY idCours;

-- Insert rows into table 'CoursSuivi'
INSERT INTO CoursSuivi
(
    idEleve, idCours, nomEleve, prenomEleve, nomCours, note
)
VALUES
(
    NULL, 40013, 'WENGER', 'Friedrich', 'Base de donnees avancees', 19
);
-- #1048 - Le champ 'idEleve' ne peut être vide (null)
-- Le champ idEleve est toujours clé primaire de la table CoursSuivi

-- Insert rows into table 'CoursSuivi'
INSERT INTO CoursSuivi
(
    idEleve, idCours, nomEleve, prenomEleve, nomCours, note
)
VALUES
(
    29521651, 40013, 'WENGER', 'Friedrich', 'Base de donnees avancees', 19
);

ALTER TABLE CoursSuivi ADD FOREIGN KEY (idEleve) REFERENCES Eleve(id);
-- #1452 - Cannot add or update a child row: a foreign key constraint fails (`hello`.`#sql-6315_3fc`, CONSTRAINT `#sql-6315_3fc_ibfk_1` FOREIGN KEY (`idEleve`) REFERENCES `Eleve` (`id`))

DELETE FROM CoursSuivi WHERE CoursSuivi.idEleve = 29521651 AND CoursSuivi.idCours = 40013

ALTER TABLE CoursSuivi ADD FOREIGN KEY (idCours) REFERENCES Cours(id);