CREATE TABLE IF NOT EXISTS person(idPerson int PRIMARY KEY, name VARCHAR(50),surname VARCHAR(50),adress VARCHAR(150),
                                  city VARCHAR(50), province VARCHAR(50), phoneNumber VARCHAR(20),workPosition VARCHAR(50),
                                  workCity VARCHAR(50),workCompany VARCHAR(200),geoLocation VARCHAR(200),birthday DATE,
                                  cityBorn VARCHAR(180));

CREATE TABLE IF NOT EXISTS typeRelation(idTypeRelation int PRIMARY KEY,nameRelation VARCHAR(50));

CREATE TABLE IF NOT EXISTS relationship(idRelationship int PRIMARY KEY, idPerson1 int, idPerson2 int, idTypeRelation int,
                                        FOREIGN KEY (idPerson1) REFERENCES person(idPerson),FOREIGN KEY (idPerson2) REFERENCES person(idPerson),
                                        FOREIGN KEY (idTypeRelation) REFERENCES typeRelation(idTypeRelation));

CREATE TABLE IF NOT EXISTS place (idPlace ing PRIMARY KEY, name VARCHAR(250), geoLocation VARCHAR(250), adress VARCHAR(250),
                                  city VARCHAR(50),cap VARCHAR(15), typePlace VARCHAR(50));
