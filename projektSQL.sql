
-------------- DDL ---------------

SELECT * FROM WINTER

DROP TABLE CABIN
DROP TABLE CUSTOMER
DROP TABLE RESERVATIONS
DROP TABLE SUMMER
DROP TABLE WINTER

CREATE TABLE CABIN (
	cabinNr int IDENTITY(1,1) NOT NULL,
	adress varchar(30) NOT NULL,
	beds int NOT NULL,
	kitchenStandard varchar(15) NOT NULL,
	otherEq varchar(15),
	rentLow money NOT NULL,
	rentHigh money NOT NULL,
	PRIMARY KEY (cabinNr)
);

INSERT INTO CABIN VALUES('Dalagatan 1', 3, 'Ugn', NULL, 1300, 2000)
INSERT INTO CABIN VALUES('Dalagatan 2', 7, 'Ugn, Spis', NULL, 3000, 4500)
INSERT INTO CABIN VALUES('Dalagatan 3', 6, 'Ugn', 'Torkskåp', 2800, 3200)
INSERT INTO CABIN VALUES('Dalagatan 4', 5, 'Ugn, mikro', 'Skovärmare', 2500, 3000)
INSERT INTO CABIN VALUES('Dalagatan 5', 4, 'Ugn', NULL, 2200, 3000)
INSERT INTO CABIN VALUES('Dalagatan 6', 5, 'Ugn', NULL, 2500, 3000)

CREATE TABLE CUSTOMER (
	civicNr BIGINT NOT NULL,
	name varchar(30) NOT NULL,
	adress varchar(30) NOT NULL,
	zipCode int NOT NULL,
	province varchar(15) NOT NULL,
	groupSize int NOT NULL,
	PRIMARY KEY (civicNr)
);



CREATE TABLE RESERVATIONS (
	r_id int IDENTITY(1,1) NOT NULL,
	cabinNr int FOREIGN KEY REFERENCES CABIN(cabinNr),
	civicNr BIGINT FOREIGN KEY (civicNr) REFERENCES CUSTOMER(civicNr),
	groupSize int NOT NULL,
	cabinPrice money NOT NULL,
	eqPrice money,
	startDate date NOT NULL,
	endDate date NOT NULL,
	PRIMARY KEY (r_id)
);





CREATE TABLE SUMMER (
	s_id int IDENTITY (1,1),
	r_id int NOT NULL FOREIGN KEY REFERENCES RESERVATIONS(r_id),
	type varchar(17) NOT NULL,
	length int NOT NULL,
	headSize int NOT NULL,
	groupmember int NOT NULL,
	price MONEY NOT NULL
	PRIMARY KEY(s_id)
);


CREATE TABLE WINTER (
	w_id int IDENTITY (1,1),
	r_id int NOT NULL FOREIGN KEY REFERENCES RESERVATIONS(r_id),
	type varchar(13) NOT NULL,
	shoeSize int NOT NULL,
	skiSize int NOT NULL,
	poleSize int NOT NULL,
	headSize int NOT NULL,
	groupmember int NOT NULL,
	price MONEY NOT NULL
	PRIMARY KEY (w_id)
);

SELECT * FROM RESERVATIONS
SELECT * FROM SUMMER
SELECT * FROM WINTER


------- CHANGE PRICE 

CREATE PROCEDURE CHANGEPRICE
	@rentHigh float,
	@rentLow float,
	@cabinNr int
as BEGIN
	
	UPDATE CABIN
	SET rentHigh=@rentHigh,rentLow=@rentLow
	WHERE cabinNr=@cabinNr;
	
END;
GO
------- PROCEDURE FOR MAKING AN RESERVATION 


DROP PROCEDURE MAKERESERVATION



CREATE PROCEDURE MAKERESERVATION
	@civicNr BIGINT,
	@name varchar(30),
	@adress varchar(30),
	@zipCode int,
	@province varchar(20),
	@groupsize int,
	@cabinNr int,
	@startDate date
as BEGIN
--insert function body here
	DECLARE @cabinPrice MONEY;
	DECLARE @eqPrice MONEY;
	DECLARE @endDate DATE;
	DECLARE @season INT;
	DECLARE @ret int
	EXEC @ret = checkDuplicate @civicNrF = @civicNr;
	
	print @ret;
	
	SET @endDate = DATEADD(day,7,@startDate);
	
	SET @season = DATEPART(month, @startdate);
	
	
	
	IF @season = '12' OR @season < '3' BEGIN
		SET @cabinPrice = (SELECT rentHigh FROM CABIN WHERE cabinNr = @cabinNr);
		
	END ELSE BEGIN
		SET @cabinPrice = (SELECT rentLow FROM CABIN WHERE cabinNr = @cabinNr);
	END 
	
	-- här kollar vi om den redan finns i customer databasen
	
	IF @ret < 1 BEGIN
		INSERT INTO CUSTOMER (civicNr, name, adress, zipCode, province, groupSize) VALUES (@civicNr, @name, @adress, @zipCode, @province, @groupsize);
	END ELSE BEGIN
		UPDATE CUSTOMER SET groupSize = @groupsize WHERE civicNr = civicnr + @civicNr
	END 
	
	
	INSERT INTO RESERVATIONS (cabinNr, civicNr, groupSize, cabinPrice, eqPrice, startDate, endDate) VALUES (@cabinNr, @civicNr, @groupSize, @cabinPrice, 0, @startDate, @endDate);
	
	

END;
GO

exec MAKERESERVATION @civicNr = 9506270694, @name = 'Nima', @adress = 'Konstapelsgatan 29', @zipCode = 37135, @province = 'karlskrona', @groupsize = 10, @cabinNr = 4, @startDate = '2015-10-10';
exec CHANGEPRICE @rentHigh=2000,@rentLow=1300,@cabinNr=1;



UPDATE RESERVATIONS SET eqPrice = 100 WHERE r_id = 2

------- procedure for adding summer eq

DROP PROCEDURE addSummerEq

CREATE PROCEDURE addSummerEq
	@r_id int,
	@type varchar(20),
	@length int,
	@head int,
	@groupmemberNr int
AS BEGIN
	DECLARE @price INT;
	
	IF @type = 'countrybike' BEGIN
		SET @price = 500;
	END 
	
	IF @type = 'downhill' BEGIN
		SET @price = 900;
	END 
	
	IF @type = 'mountainbike' BEGIN
		SET @price = 900;
	END 
	
	
	INSERT INTO SUMMER (r_id, type, length, headSize, groupmember, price) VALUES (@r_id, @type, @length, @head, @groupmemberNr, @price);
	UPDATE RESERVATIONS SET eqPrice = eqPrice + @price WHERE r_id = @r_id;
END 
	
------- add winter equipment

DROP PROCEDURE addWinterEq

CREATE PROCEDURE addWinterEq
	@r_id int,
	@type varchar(20),
	@skiis int,
	@poles int,
	@shoes float,
	@head int,
	@groupmemberNr int
AS BEGIN
	DECLARE @price INT;
	
	SET @price = 100;
	
	IF @type = 'distanceskiis' BEGIN
		SET @price = 1500;
	END 
	
	IF @type = 'slopeskiis' BEGIN
		SET @price = 2100;
	END 
	
	
	INSERT INTO WINTER (r_id, type, shoeSize, skiSize, poleSize, headSize, groupmember, price) VALUES (@r_id, @type, @shoes, @skiis, @poles, @head, @groupmemberNr, @price);
	UPDATE RESERVATIONS SET eqPrice = eqPrice + @price WHERE r_id = @r_id;
END 

exec addWinterEq @r_id = 2, @type = 'slopeskiis', @shoes = '47', @skiis = '10', @poles = '10', @head = '46', @groupmemberNr = '1';


--------- Function for returning is customer already exists in DB

CREATE FUNCTION checkDuplicate (@civicNrF bigint)
RETURNS INT
AS BEGIN
	DECLARE @check bigint;
	SET @check = (SELECT civicNr FROM CUSTOMER WHERE civicNr = @civicNrF);
	
	IF @check > 0 BEGIN
		SET @check = 1;
	END ELSE BEGIN
		SET @check = 0;
	END 
	
	return @check;
	
END 


PRINT @ret

DROP FUNCTION checkDuplicate

SELECT * FROM CUSTOMER

--------- Function to get right price, depending on date

CREATE FUNCTION getHighOrLow(@r_id int)
RETURNS INT
AS BEGIN
	DECLARE @returnvalue int
	DECLARE @bookingdate date;
	DECLARE @tmp int;
	SET @bookingdate = (SELECT startDate FROM RESERVATIONS WHERE r_id = @r_id)
	
	SET @tmp = DATEPART(month, @bookingdate);
	
	IF @tmp = '12' OR @tmp < '3' BEGIN
		SET @returnvalue = 1;
		
	END ELSE BEGIN
		SET @returnvalue = 0;
	END 
	
	return @returnvalue;
	
END 


 
DECLARE @ret int
EXEC @ret = getHighOrLow @r_id = 1;
PRINT @ret




--------- procedure to list available cabins

CREATE PROCEDURE GetCabins
	@bookingdate as date
AS
BEGIN
	DECLARE @tmp TABLE
	(
     cabinNr int
	)
	
	
	INSERT INTO @tmp (cabinNr)
	SELECT cabinNr FROM RESERVATIONS
	WHERE startDate = @bookingdate
	
	
	SELECT CABIN.cabinNr AS c, * FROM CABIN
	LEFT JOIN @tmp t
	ON t.cabinNr = CABIN.cabinNr
	WHERE t.cabinNr IS NULL

END;
GO



			
		




