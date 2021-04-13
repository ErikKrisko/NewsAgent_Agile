/*Address Table*/
INSERT INTO address VALUES (null, '123 black clover lane rhode Co.Offaly', 0, 'R45YW71');
INSERT INTO address VALUES (null, '103 Lana Road Clara Co.Offaly', 0, 'R45YW41');
INSERT INTO address VALUES (null, '105 Johnny Sin lane Killbeggan Co.Westmeath', 1, 'R42YW52');
INSERT INTO address VALUES (null, '13 Downing Street Blancherdstown Co.Dublin', 2, 'D45YW81');
INSERT INTO address VALUES (null, '123 clover lane Tullamore Co.Offaly', 3, 'R45YW31');

/*Customer Table*/
INSERT INTO customer VALUES (null, 'Bill', 'Birr', '0951078281', 1);
INSERT INTO customer VALUES (null, 'Lib', 'Tard', '0541073261', 2);
INSERT INTO customer VALUES (null, 'Joe', 'Joyce', '0911078281', 3);
INSERT INTO customer VALUES (null, 'Mimi', 'Goloba', '0591078341', 4);
INSERT INTO customer VALUES (null, 'Conor', 'Demain', '0725014729', 5);

/*invoice Table*/
INSERT INTO invoice VALUES (null, '2021-02-10', 1, 20.20, 1);
INSERT INTO invoice VALUES (null, '2021-04-14', 0, 18.36, 2);
INSERT INTO invoice VALUES (null, '2021-04-18', 1, 19.20, 3);
INSERT INTO invoice VALUES (null, '2021-07-01', 0, 30.60, 4);
INSERT INTO invoice VALUES (null, '2021-09-10', 1, 50.40, 5);

/*holiday Table*/
INSERT INTO holiday VALUES (null, '2021-02-10', '2021-02-15', 1);
INSERT INTO holiday VALUES (null, '2021-01-01', '2021-02-16', 2);
INSERT INTO holiday VALUES (null, '2021-03-02', '2021-05-15', 3);
INSERT INTO holiday VALUES (null, '2021-01-10', '2021-06-01', 4);
INSERT INTO holiday VALUES (null, '2021-12-15', '2021-12-20', 5);

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
INSERT INTO publication VALUES (null, 'PlayBunny', 'Magazine', '10', 'MONTHLY 5');

/*delivery Table*/
INSERT INTO delivery VALUES (null, '2022-01-05', 1, 1, 1, 3);
INSERT INTO delivery VALUES (null, '2022-04-05', 0, 2, 2, 2);
INSERT INTO delivery VALUES (null, '2022-07-05', 1, 3, 3, 4);
INSERT INTO delivery VALUES (null, '2022-06-05', 1, 4, 4, 5);
INSERT INTO delivery VALUES (null, '2022-09-05', 0, 5, 5, 3);
INSERT INTO delivery VALUES (null, '2022-02-05', 0, 1, 1, 3);

/*Stock Table*/
INSERT INTO stock VALUES (null, '2021-07-10', '200', '1-B', 1, 200);
INSERT INTO stock VALUES (null, '2021-05-04', '400', '2-F', 1, 400);
INSERT INTO stock VALUES (null, '2021-09-15', '900', '3-M', 1, 900);
INSERT INTO stock VALUES (null, '2021-11-19', '20', '4-V', 1, 20);
INSERT INTO stock VALUES (null, '2021-12-21', '24', '5-P', 1, 24);

/*Delivers relation Table*/
INSERT INTO delivers VALUES (1, 1);
INSERT INTO delivers VALUES (1, 2);
INSERT INTO delivers VALUES (2, 3);
INSERT INTO delivers VALUES (2, 4);
INSERT INTO delivers VALUES (2, 5);

/*Subscription relation Table*/
INSERT INTO subscription VALUES (1, 3, 1);
INSERT INTO subscription VALUES (2, 2, 1);
INSERT INTO subscription VALUES (3, 4, 1);
INSERT INTO subscription VALUES (4, 5, 1);
INSERT INTO subscription VALUES (5, 3, 2);
