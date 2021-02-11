/*Address Table*/ 

INSERT INTO Address VALUES (null, '123 black clover lane rhode Co.Offaly', 'B1', 'R45YW71'); 

INSERT INTO Address VALUES (null, '103 Lana Road Clara Co.Westmeath', 'B1', 'R45YW41'); 

INSERT INTO Address VALUES (null, '105 Johnny Sin lane Killbeggan Co.Westmeath', 'B2', 'R42YW52'); 

INSERT INTO Address VALUES (null, '13 Downing Street Blancherdstown Co.Dublin', 'A1', 'D45YW81'); 

INSERT INTO Address VALUES (null, '123 clover lane Tullamore Co.Offaly', 'A3', 'R45YW31')

/*Customer Table*/ 

INSERT INTO customer VALUES (null, 'Bill', 'Birr', '0951078281', 1); 

INSERT INTO customer VALUES (null, 'Lib', 'Tard', '0541073261', 2); 

INSERT INTO customer VALUES (null, 'Joe', 'Joyce', '0951078281', 3); 

INSERT INTO customer VALUES (null, 'Mimi', 'Goloba', '0591078341', 4); 

INSERT INTO customer VALUES (null, 'Conor', 'Demain', '0725014729', 5); 

/*Invoice Table*/

INSERT INTO Invoice VALUES (null, '10/02/2021', 1, 20, 1); 

INSERT INTO Invoice VALUES (null, '14/04/2021', 0, 20', 2); 

INSERT INTO Invoice VALUES (null, '18/04/2021', 1, '20, 3); 

INSERT INTO Invoice VALUES (null, '01/07/2021', 0, 20, 4); 

INSERT INTO Invoice VALUES (null, '10/09/2021', 1, 20, 5); 

/*Holiday Table*/ 

INSERT INTO Holiday VALUES (null, '10/02/2021', '15/02/2021', 1); 

INSERT INTO Holiday VALUES (null, '1/04/2021', '15/02/2022', 2); 

INSERT INTO Holiday VALUES (null, '02/03/2021', '15/05/2021', 3); 

INSERT INTO Holiday VALUES (null, '10/01/2021', '01/06/2021', 4); 

INSERT INTO Holiday VALUES (null, '15/12/2021', '20/12/2021', 5)

/*Delivery Table*/ 

INSERT INTO Delivery VALUES (null, '05/01/2022', 'Completed', 1, 1); 

INSERT INTO Delivery VALUES (null, '05/04/2022', 'Not Completed', 2, 2); 

INSERT INTO Delivery VALUES (null, '07/018/2022', 'Completed', 3, 3); 

INSERT INTO Delivery VALUES (null, '10/06/2022', 'Completed', 4, 4); 

INSERT INTO Delivery VALUES (null, '05/09/2022', 'Not Completed', 5, 5)

/*Employee Table*/ 

INSERT INTO Employee VALUES (null, 'John', 'Doyle'); 

INSERT INTO Employee VALUES (null, 'Micheal', 'DeSanta'); 

INSERT INTO Employee VALUES (null, 'Joey', 'Soul'); 

INSERT INTO Employee VALUES (null, 'Johnny', 'Sins'); 

INSERT INTO Employee VALUES (null, 'Kerum', 'Asul'); 

/*Frequency Table*/ 

INSERT INTO Frequency VALUES (null, '21/05/2021', 'SDGBWE'); 

INSERT INTO Frequency VALUES (null, '01/07/2021', 'JWDNUJW'); 

INSERT INTO Frequency VALUES (null, '06/03/2021', 'DWDDKWI'); 

INSERT INTO Frequency VALUES (null, '04/06/2021', 'DIJWDIW'); 

INSERT INTO Frequency VALUES (null, '25/05/2021', 'DOKWODK'); 

/*Publication Table*/ 

INSERT INTO Publication VALUES (null, 'Offaly independent', 'BroadSheet', '2.50', 1); 

INSERT INTO Publication VALUES (null, 'Westmeath independent', 'Broadsheet', '1.50', 2); 

INSERT INTO Publication VALUES (null, 'Gamer essential', 'Magazine', '9.50 ', 3); 

INSERT INTO Publication VALUES (null, 'The Sun', 'Tabloid', '2.50', 4); 

INSERT INTO Publication VALUES (null, 'PlayBunny', 'Magazine', '10', 5); 

/*Stock Table*/ 

INSERT INTO Stock VALUES (null, '10/07/2021', '200', '1-B', 1, 200); 

INSERT INTO Stock VALUES (null, '04/05/2021', '400', '2-F', 1, 400); 

INSERT INTO Stock VALUES (null, '15/09/2021', '900', '3-M', 1, 900); 

INSERT INTO Stock VALUES (null, '19/11/2021', '20', '4-V', 1, 20); 

INSERT INTO Stock VALUES (null, '21/12/2021', '24', '5-P', 1, 24)

/*Prod_for_delivery relation Table*/ 

INSERT INTO prod_for_delivery VALUES (1, 2); 

INSERT INTO prod_for_delivery VALUES (2, 3); 

INSERT INTO prod_for_delivery VALUES (3, 1); 

INSERT INTO prod_for_delivery VALUES (4, 4); 

INSERT INTO prod_for_delivery VALUES (5, 5)

/*Delivers relation Table*/ 

INSERT INTO Deliveries VALUES (1, 1); 

INSERT INTO Deliveries VALUES (1, 2); 

INSERT INTO Deliveries VALUES (2, 3); 

INSERT INTO Deliveries VALUES (2, 4); 

INSERT INTO Deliveries VALUES (2, 5);

/*Subscription relation Table*/ 

INSERT INTO Holidays VALUES ('1, '3', '1'); 

INSERT INTO Holidays VALUES ('2', '2', '1'); 

INSERT INTO Holidays VALUES ('3', '4', '1'); 

INSERT INTO Holidays VALUES ('4', '5', '1'); 

INSERT INTO Holidays VALUES ('5', '1', '1'); 
