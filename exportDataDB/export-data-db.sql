CREATE TABLE IF NOT EXISTS person(idPerson int PRIMARY KEY, name VARCHAR(50),surname VARCHAR(50),adress VARCHAR(150),
                                  city VARCHAR(50), province VARCHAR(50), phoneNumber VARCHAR(20),workPosition VARCHAR(50),
                                  workCity VARCHAR(50),workCompany VARCHAR(200),geoLocation VARCHAR(200),birthday DATE,
                                  cityBorn VARCHAR(180));

CREATE TABLE IF NOT EXISTS typeRelation(idTypeRelation int PRIMARY KEY,nameRelation VARCHAR(50));

CREATE TABLE IF NOT EXISTS relationship(idRelationship int PRIMARY KEY, idPerson1 int, idPerson2 int, idTypeRelation int,
                                        FOREIGN KEY (idPerson1) REFERENCES person(idPerson),FOREIGN KEY (idPerson2) REFERENCES person(idPerson),
                                        FOREIGN KEY (idTypeRelation) REFERENCES typeRelation(idTypeRelation));

CREATE TABLE IF NOT EXISTS place (idPlace int PRIMARY KEY, name VARCHAR(250), geoLocation VARCHAR(250), adress VARCHAR(250),
                                  city VARCHAR(50),cap VARCHAR(15), typePlace VARCHAR(50));
INSERT INTO typeRelation(idTypeRelation,nameRelation) VALUES (0,'SON'),(1,'DAUGHTER'),(2,'BROTHER'),(3,'SISTERR'),(4,'MOTHER')
,(5,'FATHER'),(6,'GRAMPA'),(7,'GRANDMOTHER'),(8,'GREATE_GRANDMOTHER'),(9,'GREAT_GRANDFATHER'),(10,'UNCLE'),(11,'AUNT'),(12,'GRANDCHILD')
,(13,'COUSIN'),(14,'SON_IN_LAW'),(15,'FATHER_IN_LAW'),(16,'WIFE'),(17,'HUSBAND'),(18,'FRIEND'),(19,'GIRL_FRIEND'),(20,'BOY_FRIEND')
,(21,'COLLEAGUE'),(22,'BOSS');
INSERT INTO person(idPerson,name,surname,adress,city,province,phoneNumber,workPosition,workCity,workCompany,geoLocation,birthday,cityBorn) VALUES;
INSERT INTO typeRelation(idTypeRelation,nameRelation) VALUES 
INSERT INTO relationship(idRelationship,idPerson1,idPerson2,idTypeRelation) VALUES;
INSERT INTO place(idPlace,name,geoLocation,adress,city,cap,typePlace) VALUES;