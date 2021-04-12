/*Address Table*/
INSERT INTO address VALUES (null, '123 Black Clover Lane Rhode', 1, 'R45YW71');
INSERT INTO address VALUES (null, '103 Lana Road', 1, 'A98CA34');
INSERT INTO address VALUES (null, '105 Johnny Sin lane', 1, 'R42YW52');
INSERT INTO address VALUES (null, '13 Downing Street', 2, 'G91KF49');
INSERT INTO address VALUES (null, '4 St Bridgets Well Liscannor', 3, 'E82AR97');
INSERT INTO address VALUES (null, 'Avenue De Rennes Mahon', 3, 'K03BI17');
INSERT INTO address VALUES (null, '35 Leinster AVE', 3, 'E69AI22');
INSERT INTO address VALUES (null, 'Unit 10 Centrepoint Centre Park road', 1, 'V57TM21');
INSERT INTO address VALUES (null, '20 Blackmill street', 3, 'A72YL52');
INSERT INTO address VALUES (null, '42 Tryton Heights', 2, 'W17LD37');
INSERT INTO address VALUES (null, '18 Church Street', 2, 'T97RM61');
INSERT INTO address VALUES (null, '50 Main Street', 2, 'B61DS36');
INSERT INTO address VALUES (null, '14 Roseberry', 2, 'H89KH87');
INSERT INTO address VALUES (null, '4 Stephens Court', 2, 'O21KE39');
INSERT INTO address VALUES (null, '3 Parnell street', 3, 'B77AL33');
INSERT INTO address VALUES (null, '14 Clanbrassil Street', 3, 'H75BQ01');
INSERT INTO address VALUES (null, 'Unit 2 Centrepoint Centre Park road', 1, 'C42RL25');
INSERT INTO address VALUES (null, '12 Main Street', 1, 'X67PW05');
INSERT INTO address VALUES (null, '34 Ballybricken', 3, 'U77XS00');
INSERT INTO address VALUES (null, 'Unit 4 Centrepoint Centre Park road', 1, 'V65EX11');
INSERT INTO address VALUES (null, '1 Newtown', 1, 'K14BS64');

/*Customer Table*/
INSERT INTO customer VALUES (null, 'Bill', 'Birr', '0875748271', 1);
INSERT INTO customer VALUES (null, 'Lib', 'Tard', '0541073261', 2);
INSERT INTO customer VALUES (null, 'Joe', 'Joyce', '0861111438', 3);
INSERT INTO customer VALUES (null, 'Mimi', 'Goloba', '0872989363', 4);
INSERT INTO customer VALUES (null, 'Conor', 'Demain', '0858393469', 5);
INSERT INTO customer VALUES (null, 'Gabriel', 'Andra', '0856812433', 6);
INSERT INTO customer VALUES (null, 'Ivan', 'Vlad', '0878643771', 7);
INSERT INTO customer VALUES (null, 'Frans', 'Bernarda', '0879544130', 8);
INSERT INTO customer VALUES (null, 'Ane', 'Rozalia', '0866600910', 9);
INSERT INTO customer VALUES (null, 'Brit', 'Caileigh', '0873887108', 10);
INSERT INTO customer VALUES (null, 'Oleh', 'Olegario', '0873121937', 11);
INSERT INTO customer VALUES (null, 'Angelina', 'Fereydoun', '0863208910', 6);
INSERT INTO customer VALUES (null, 'John', 'Smith', '0874569981', 12);
INSERT INTO customer VALUES (null, 'Tatiana', 'Deirdre', '0866035790', 13);
INSERT INTO customer VALUES (null, 'Marcin', 'Micheal', '0852180858', 14);
INSERT INTO customer VALUES (null, 'Diana', 'Sarita', '0877989887', 14);
INSERT INTO customer VALUES (null, 'Kevin', 'Smith', '0864017087', 15);
INSERT INTO customer VALUES (null, 'John', 'Baans', '0867094522', 16);
INSERT INTO customer VALUES (null, 'Dacre', 'Günter', '0853584613', 17);
INSERT INTO customer VALUES (null, 'Norma', 'Lame', '0860048362', 18);
INSERT INTO customer VALUES (null, 'John', 'Iveta', '0877303995', 19);
INSERT INTO customer VALUES (null, 'Adrian', 'Smith', '0877306818', 20);
INSERT INTO customer VALUES (null, 'Neptune', 'Sorin', '0858673240', 21);

/*invoice Table*/
INSERT INTO invoice VALUES (null, '2021-02-10', 1, 20.20, 1);
INSERT INTO invoice VALUES (null, '2021-04-14', 0, 18.36, 2);
INSERT INTO invoice VALUES (null, '2021-04-18', 1, 19.20, 3);
INSERT INTO invoice VALUES (null, '2021-07-01', 0, 30.60, 4);
INSERT INTO invoice VALUES (null, '2021-09-10', 1, 50.40, 5);

/*holiday Table*/
INSERT INTO holiday VALUES (null, '2021-02-10', '2021-02-15', 1);
INSERT INTO holiday VALUES (null, '2021-01-01', '2021-01-16', 1);
INSERT INTO holiday VALUES (null, '2021-03-02', '2021-05-15', 12);
INSERT INTO holiday VALUES (null, '2021-01-10', '2021-06-01', 6);
INSERT INTO holiday VALUES (null, '2021-12-15', '2021-12-20', 5);
INSERT INTO holiday VALUES (null, '2021-04-29', '2021-05-05', 8);
INSERT INTO holiday VALUES (null, '2021-06-22', '2021-06-29', 4);
INSERT INTO holiday VALUES (null, '2021-04-12', '2021-04-18', 20);
INSERT INTO holiday VALUES (null, '2021-04-04', '2021-04-14', 7);
INSERT INTO holiday VALUES (null, '2021-06-29', '2021-07-29', 13);

/*delivery Table*/
INSERT INTO delivery VALUES (null, '2022-01-05', 1, 1, 1);
INSERT INTO delivery VALUES (null, '2022-04-05', 0, 2, 2);
INSERT INTO delivery VALUES (null, '2022-07-18', 1, 3, 3);
INSERT INTO delivery VALUES (null, '2022-06-10', 1, 4, 4);
INSERT INTO delivery VALUES (null, '2022-09-05', 0, 5, 5);
INSERT INTO delivery VALUES (null, '2022-01-05', 0, 1, 1);

/*employee Table*/
INSERT INTO employee VALUES (null, 'John', 'Doyle');
INSERT INTO employee VALUES (null, 'Micheal', 'DeSanta');
INSERT INTO employee VALUES (null, 'Joey', 'Soul');
INSERT INTO employee VALUES (null, 'Johnny', 'Sins');
INSERT INTO employee VALUES (null, 'Kerum', 'Asul');

/*publication Table*/
INSERT INTO publication VALUES (null, 'Offaly independent', 'Broadsheet', '2.50', 'WEEKLY 4');
INSERT INTO publication VALUES (null, 'Westmeath independent', 'Broadsheet', '1.50', 'WEEKLY 3');
INSERT INTO publication VALUES (null, 'Gamer essential', 'Magazine', '9.50 ', 'MONTHLY 14');
INSERT INTO publication VALUES (null, 'The Sun', 'Tabloid', '2.50', 'DAILY');
INSERT INTO publication VALUES (null, 'PlayBunny', 'Magazine', '10.00', 'MONTHLY 5');
INSERT INTO publication VALUES (null, 'Old Times', 'Magazine', '9.99', 'MONTHLY 1');
INSERT INTO publication VALUES (null, 'The Saturday', 'Tabloid', '3.00', 'WEEKLY 6');
INSERT INTO publication VALUES (null, 'Daily News', 'Broadsheet', '1.95', 'DAILY');
INSERT INTO publication VALUES (null, 'Final Straw', 'Magazine', '10', 'MONTHLY 28');

/*Stock Table UNUSED*/
/*INSERT INTO stock VALUES (null, '2021-07-10', '200', '1-B', 1, 200);*/

/*Prod_for_delivery relation Table*/
INSERT INTO prod_for_delivery VALUES (1, 2);
INSERT INTO prod_for_delivery VALUES (2, 3);
INSERT INTO prod_for_delivery VALUES (3, 1);
INSERT INTO prod_for_delivery VALUES (4, 4);
INSERT INTO prod_for_delivery VALUES (5, 5);

/*Delivers relation Table*/
INSERT INTO delivers VALUES (1, 1);
INSERT INTO delivers VALUES (1, 2);
INSERT INTO delivers VALUES (2, 3);
INSERT INTO delivers VALUES (2, 4);
INSERT INTO delivers VALUES (2, 5);

/*Subscription relation Table*/
INSERT INTO subscription VALUES (1,1,1);
INSERT INTO subscription VALUES (1,8,1);

INSERT INTO subscription VALUES (2,2,1);
INSERT INTO subscription VALUES (2,5,1);

INSERT INTO subscription VALUES (3,7,1);
INSERT INTO subscription VALUES (3,8,1);

INSERT INTO subscription VALUES (4,3,2);

INSERT INTO subscription VALUES (5,5,1);

INSERT INTO subscription VALUES (6,7,1);

INSERT INTO subscription VALUES (7,8,1);
INSERT INTO subscription VALUES (7,7,1);

INSERT INTO subscription VALUES (8,4,1);

INSERT INTO subscription VALUES (9,1,1);
INSERT INTO subscription VALUES (9,4,1);

INSERT INTO subscription VALUES (10,2,3);
INSERT INTO subscription VALUES (10,4,6);
INSERT INTO subscription VALUES (10,8,4);

INSERT INTO subscription VALUES (11,1,1);
INSERT INTO subscription VALUES (11,6,1);

INSERT INTO subscription VALUES (12,2,1);
INSERT INTO subscription VALUES (12,7,1);

INSERT INTO subscription VALUES (13,1,1);

INSERT INTO subscription VALUES (14,5,1);
INSERT INTO subscription VALUES (14,2,1);
INSERT INTO subscription VALUES (14,1,2);

INSERT INTO subscription VALUES (15,1,1);
INSERT INTO subscription VALUES (15,3,1);
INSERT INTO subscription VALUES (15,9,1);

INSERT INTO subscription VALUES (16,7,2);
INSERT INTO subscription VALUES (16,6,1);

INSERT INTO subscription VALUES (17,9,1);
INSERT INTO subscription VALUES (17,2,1);
INSERT INTO subscription VALUES (17,1,1);

INSERT INTO subscription VALUES (18,5,1);
INSERT INTO subscription VALUES (18,8,1);
INSERT INTO subscription VALUES (18,3,1);

INSERT INTO subscription VALUES (19,5,1);
INSERT INTO subscription VALUES (19,9,2);
INSERT INTO subscription VALUES (19,6,1);

INSERT INTO subscription VALUES (20,4,1);

INSERT INTO subscription VALUES (21,9,1);
INSERT INTO subscription VALUES (21,2,1);
INSERT INTO subscription VALUES (21,1,2);

INSERT INTO subscription VALUES (22,4,1);
INSERT INTO subscription VALUES (22,7,1);

INSERT INTO subscription VALUES (23,4,2);