#----------------------------------------------------------
#-- Creamos el schema. Previamente lo borramos si ya esta creado
#----------------------------------------------------------
DROP DATABASE IF EXISTS bbddJava;
CREATE DATABASE IF NOT EXISTS bbddJava;
USE bbddJava;

#----------------------------------------------------------
#-- Creamos un usuario para el acceso a la base de datos
#----------------------------------------------------------
DROP USER IF EXISTS 'joaalsai'@'%';
CREATE USER 'joaalsai'@'%';
GRANT ALL PRIVILEGES ON bbddJava.* TO 'joaalsai'@'%' IDENTIFIED BY '1111';
GRANT all PRIVILEGES on mysql.proc TO 'joaalsai'@'%' IDENTIFIED BY '1111';

#----------------------------------------------------------
#-- Creamos las tablas
#----------------------------------------------------------
CREATE TABLE Usuario (
	login VARCHAR(45) PRIMARY KEY,
	mail VARCHAR(45) UNIQUE NOT NULL,
	password BLOB NOT NULL);

CREATE TABLE Cliente (
	id INT PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(45) NOT NULL,
	apellidos VARCHAR(50),
	dni VARCHAR(10) UNIQUE,
	fecha_nacimiento DATE,
	foto MEDIUMBLOB);

CREATE TABLE Categoria (
	id int(11) PRIMARY KEY AUTO_INCREMENT,
	descripcion varchar(45) NOT NULL);
	
CREATE TABLE Factura (
	id INT PRIMARY KEY AUTO_INCREMENT,
	fechaFactura DATE NOT NULL,
	importe DECIMAL(10,2) NOT NULL DEFAULT 0,
	clienteId INT NOT NULL,
	FOREIGN KEY (clienteId) REFERENCES Cliente (id) ON UPDATE CASCADE);

CREATE TABLE Articulo (
	id int(11) PRIMARY KEY AUTO_INCREMENT,
	descArticulo varchar(50) NOT NULL,
	existencias int(10) unsigned NOT NULL,
	pvp DECIMAL(10,2) unsigned NOT NULL,
	categoriaId int(11) NOT NULL,
	FOREIGN KEY (categoriaId) REFERENCES Categoria (id));

CREATE TABLE Lin_Fac (
	facturaId INT,
	linea INT,
	articuloId INT NOT NULL,
	unidades DECIMAL(10,2) NOT NULL,
	pvp DECIMAL(10,2) NOT NULL,
	importeLinea DECIMAL(10,2) NOT NULL,
	PRIMARY KEY (facturaId,linea),
	FOREIGN KEY (facturaId) REFERENCES Factura (id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (articuloId) REFERENCES Articulo (id) ON UPDATE CASCADE);
	
#----------------------------------------------------------
#-- Creamos los triggers
#----------------------------------------------------------
DELIMITER @@
DROP TRIGGER IF EXISTS upd_lin_fac @@
CREATE TRIGGER upd_lin_fac
AFTER UPDATE ON Lin_Fac 
FOR EACH ROW
BEGIN
    IF(NEW.importeLinea <> OLD.importeLinea)
    THEN
        UPDATE Factura AS F 
        SET F.importeLinea = (SELECT SUM(L.importeLinea) FROM Lin_Fac L WHERE F.id=L.facturaId)
        WHERE F.id=OLD.facturaId;
    END IF;
END;@@
DELIMITER ;
#----------------------------------------------------------
DELIMITER @@
DROP TRIGGER IF EXISTS del_lin_fac @@
CREATE TRIGGER del_lin_fac
AFTER DELETE ON Lin_Fac 
FOR EACH ROW
BEGIN    
        UPDATE Factura AS F 
        SET F.importe = (SELECT SUM(L.importeLinea) FROM Lin_Fac L WHERE F.id=L.facturaId) 
        WHERE F.id=OLD.facturaId;

END;@@
DELIMITER ;
#----------------------------------------------------------
DELIMITER @@
DROP TRIGGER IF EXISTS delete_factura @@
CREATE TRIGGER delete_factura
BEFORE DELETE ON Factura 
FOR EACH ROW
BEGIN
    DECLARE hoy DATE;
    DECLARE fFactura DATE;
    SELECT fechaFactura INTO fFactura FROM Factura F WHERE F.id = OLD.id;
    SELECT CURDATE() INTO hoy;
    IF (fFactura < hoy)
    THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede eliminar una factura que no sea de hoy';
    END IF;
END;@@
DELIMITER ;

#----------------------------------------------------------
DELIMITER @@

DROP TRIGGER IF EXISTS ins_lin_fra @@

CREATE TRIGGER ins_lin_fra 
AFTER INSERT ON Lin_Fac 
FOR EACH ROW
BEGIN

    UPDATE Factura AS F 
    SET F.importe = IFNULL((SELECT SUM(L.importeLinea) FROM Lin_Fac L WHERE F.id=L.facturaId),0);
    
END;@@
DELIMITER ;

#----------------------------------------------------------
#-- Creamos los procedimientos
#----------------------------------------------------------

DELIMITER @@
DROP PROCEDURE IF EXISTS crear_factura @@
CREATE PROCEDURE crear_factura(OUT numeroFactura INT,IN codCliente INT) 
BEGIN

	DECLARE existeCliente INT DEFAULT 0;
	DECLARE lastNumeroFac INT;
    
    SELECT COUNT(*) INTO existeCliente FROM Cliente WHERE id=codCliente;
    SELECT IFNULL(MAX(id),0) INTO lastNumeroFac FROM Factura;
    
   	IF existeCliente = 1
    THEN
 
    	INSERT INTO Factura (id,fechaFactura,clienteId)
    	VALUES (lastNumeroFac +1,CURDATE(),codCliente);
    	SET numeroFactura=lastNumeroFac+1;
    	    	
    ELSE
    	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No existe el cliente', MYSQL_ERRNO = 1000;  

	END IF;
	
END @@
DELIMITER ;
#----------------------------------------------------------
DELIMITER @@
DROP PROCEDURE IF EXISTS eliminar_factura @@
CREATE PROCEDURE eliminar_factura(IN numeroFactura INT) 
BEGIN

	DECLARE lastNumeroFac INT;
    
    SELECT MAX(id) INTO lastNumeroFac FROM Factura;
    
   	IF lastNumeroFac = numeroFactura
    THEN 
    	DELETE FROM Factura WHERE id=numeroFactura;
    	    	
    ELSE
    	SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Solo se puede eliminar la ultima factura', MYSQL_ERRNO = 1000;  
	END IF;
	
END @@
DELIMITER ;
#----------------------------------------------------------
DELIMITER @@
DROP PROCEDURE IF EXISTS insertar_linea @@
CREATE PROCEDURE insertar_linea(IN nFac INT,IN codArticulo INT, IN cant INT)
BEGIN

    DECLARE pvpArt INT DEFAULT 0; 
    DECLARE lineaFac INT DEFAULT 1;
    DECLARE existenciasArt INT DEFAULT 0;
    DECLARE existeArticulo INT DEFAULT 0;
    DECLARE existeFactura INT DEFAULT 0;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SHOW ERRORS LIMIT 1;
        RESIGNAL;
        ROLLBACK;
    END;

    SELECT COUNT(*) INTO existeFactura FROM Factura WHERE id=nFac;
    SELECT COUNT(*) INTO existeArticulo FROM Articulo WHERE id=codArticulo;
    SELECT existencias INTO existenciasArt FROM Articulo WHERE id=codArticulo;
    SELECT IFNULL(MAX(linea)+1,1) INTO lineaFac FROM Lin_Fac WHERE facturaId=nFac;
    SELECT pvp INTO pvpArt FROM Articulo WHERE id=codArticulo;

    IF existeArticulo=0
    THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El articulo no existe', MYSQL_ERRNO = 1000; 
    ELSEIF existeFactura=0
    THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La factura no existe', MYSQL_ERRNO = 1001; 
    ELSEIF existenciasArt<cant
    THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No hay existencias', MYSQL_ERRNO = 1002;       
    ELSEIF existenciasArt>=cant AND cant<>0 
    THEN

        START TRANSACTION;

        INSERT INTO Lin_Fac VALUES(
        nFac,
        lineaFac,
        codArticulo,
        cant,
        pvpArt,
        cant*pvpArt);

        UPDATE Articulo
        SET existencias = existencias - cant
        WHERE id=codArticulo;

        COMMIT;

    END IF;
END;@@
DELIMITER ;
#----------------------------------------------------------
DELIMITER @@
DROP PROCEDURE IF EXISTS eliminar_linea @@
CREATE PROCEDURE eliminar_linea(IN nFac INT,IN nLinea INT)
BEGIN

	DECLARE cant DECIMAL(10,2);
	DECLARE codArt INT;
	
	SELECT unidades,articuloId INTO cant,codArt FROM Lin_Fac WHERE facturaId=nFac AND linea = nLinea;

    START TRANSACTION;

	DELETE FROM Lin_Fac WHERE facturaId=nFac AND linea = nLinea; 

    UPDATE Articulo
    SET existencias = existencias + cant
    WHERE id=codArt;

    COMMIT;

 
END;@@
DELIMITER ;

#----------------------------------------------------------
#-- Creamos los usuarios que pueden utilizar nuestra aplicacion
#----------------------------------------------------------
INSERT INTO Usuario VALUES("pepe","pepe@ieslavereda.es",sha1('1111'));
INSERT INTO Usuario VALUES("juan","juan@ieslavereda.es",sha1('1234'));
INSERT INTO Usuario VALUES("manolo","manolo@ieslavereda.es",sha1('4321'));
INSERT INTO Usuario VALUES("joaalsai","joaalsai@ieslavereda.es",sha1('4321'));

#----------------------------------------------------------
#-- Creamos algunos clientes
#----------------------------------------------------------
INSERT INTO Cliente (nombre,apellidos,dni,fecha_nacimiento,foto ) VALUES("JOAQUIN VICENTE","ALONSO SAIZ","53052298S","1977-02-28",null);
INSERT INTO Cliente (nombre,apellidos,dni,fecha_nacimiento,foto ) VALUES("ANA ISABEL","ALGARRA VERDU","12879621J","1998-01-22",null);
INSERT INTO Cliente (nombre,apellidos,dni,fecha_nacimiento,foto ) VALUES("HECTOR MIGUEL","BELENGUER MARTINEZ","78552698M","1999-02-9",null);
INSERT INTO Cliente (nombre,apellidos,dni,fecha_nacimiento,foto ) VALUES("RAFAEL","BERNABEU GOMEZ","74985319B","1965-11-13",null);
INSERT INTO Cliente (nombre,apellidos,dni,fecha_nacimiento,foto ) VALUES("JOSE","CARRASCO GARCIA","85547625C","1976-8-11",null);
INSERT INTO Cliente (nombre,apellidos,dni,fecha_nacimiento,foto ) VALUES("VICENTE","DOMINGUEZ GOMEZ","79135894U","2000-5-4",null);
INSERT INTO Cliente (nombre,apellidos,dni,fecha_nacimiento,foto ) VALUES("EMILIA","GONZALEZ ANDRADA","02547856K","2004-2-1",null);
INSERT INTO Cliente (nombre,apellidos,dni,fecha_nacimiento,foto ) VALUES("DAVID","LOPEZ FERNANDEZ","78216635L","1098-04-23",null);
INSERT INTO Cliente (nombre,apellidos,dni,fecha_nacimiento,foto ) VALUES("FRANCISCO","PEREZ RIPOLL","78487769P","2010-06-12",null);
INSERT INTO Cliente (nombre,apellidos,dni,fecha_nacimiento,foto ) VALUES("JOSE ENRIQUE","TERUEL MARTINEZ","16678549B","2000-02-19",null);

#----------------------------------------------------------
#-- Creamos algunas categorias
#----------------------------------------------------------
INSERT INTO Categoria (descripcion) VALUES("DISCOS DUROS");
INSERT INTO Categoria (descripcion) VALUES("MONITORES");
INSERT INTO Categoria (descripcion) VALUES("CPU");
INSERT INTO Categoria (descripcion) VALUES("PERIFERICOS");
INSERT INTO Categoria (descripcion) VALUES("MEMORIAS");

#----------------------------------------------------------
#-- Creamos algunos articulos
#----------------------------------------------------------
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("SEAGATE 1TB",10 ,100 ,1);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("TOSHIBA SSD 240GB",10 ,41.99 ,1);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("SAMSUNG 970 EVO PLUS 500GB",10 ,124.99 ,1);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("WD BLUE 3D NAND SSD 500B",10 ,64.99 ,1);

INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("SAMSUNG 23'", 5,243.5 ,2);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("AOC E2470SWH 24' LED", 5,105.10 ,2);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("BENQ GL258 24.5' LED", 5,129 ,2);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("HP 22W 21.5' LED IPS", 5,99.99 ,2);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("HP 27W LED IPS", 5,149 ,2);

INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("INTEL i5-8400 2.8" ,2 ,203.90 ,3);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("AMD RYZEN 5 2600 3.4" ,2 ,165.99 ,3);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("INTEL i7-8700 3.2" ,2 ,317.90 ,3);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("INTEL i3-8100 3.6" ,2 ,120.99 ,3);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("INTEL i7-9700 3.6" ,2 ,431.90 ,3);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("AMD RYZEN 7 2700 4.1" ,2 ,249.90 ,3);

INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("TECLADO LONGITECH",7 ,29 ,4);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("RATON LONGITECH" ,6 ,15 ,4);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("HP LASERJET PRO M15W" ,6 ,74.99 ,4);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("BROTHER HL-L2350DW" ,2 ,102 ,4);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("DYMO LETRATAG" ,6 ,29.9 ,4);


INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("CRUCIAL DDR4 2400",10 ,52.99,5);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("CORSAIR DDR4 2400",6 ,45.25,5);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("KINGSTON HYPERX DDR4 2400",7 ,49,5);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("G.SKILL V RED DDR4 3000",1 ,109,5);
INSERT INTO Articulo (descArticulo,existencias,pvp,categoriaId) VALUES ("CORSAIR VENGEANCE DDR4 2400",2 ,45.25,5);


#----------------------------------------------------------
#-- Creamos algunas Facturas
#----------------------------------------------------------
# crear_factura(OUT numeroFactura INT,IN codCliente INT) 
CALL crear_factura(@valor,1);
CALL crear_factura(@valor,2);
CALL crear_factura(@valor,3);
CALL crear_factura(@valor,4);

#----------------------------------------------------------
#-- Rellenamos algunas facturas
#----------------------------------------------------------
# insertar_linea(IN nFac INT,IN codArticulo INT, IN cant INT)
CALL insertar_linea(1,1,2);
CALL insertar_linea(1,2,1);
CALL insertar_linea(2,1,5);
CALL insertar_linea(2,1,1);
CALL insertar_linea(3,1,1);
CALL insertar_linea(3,2,1);




