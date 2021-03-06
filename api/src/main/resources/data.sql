INSERT INTO USER (USERNAME, PASSWORD, USER_TYPE, RESERVED_AUTO_BID) VALUES ('user1', '$2a$10$w3Rt.2zXfXMN0IG0vB5Fvur9gl07w5q7VB/aYy2aN8ChHDwixZzly', 'REGULAR', 0);
INSERT INTO USER (USERNAME, PASSWORD, USER_TYPE, RESERVED_AUTO_BID) VALUES ('user2', '$2a$10$qNttw7hkELIJaDd7vczMze07ydfgcvCZNWeIDQ/Ei4V5Vg4drwwAu', 'REGULAR', 0);
INSERT INTO USER (USERNAME, PASSWORD, USER_TYPE) VALUES ('admin1', '$2a$10$Hr5E3gaALbGaLaFgL6KVkeKKX7tHHimzF3TlHsedSkK6fDTxkJsdO', 'ADMIN');
INSERT INTO USER (USERNAME, PASSWORD, USER_TYPE) VALUES ('admin2', '$2a$10$NDOXOE7DSPeKt4kBNHCbJeik.Wl3Zdro9zFTayTr1LlzONFnzKDRO', 'ADMIN');
INSERT INTO ITEM (NAME, DESCRIPTION, START_PRICE, DATE_END, AWARDED) VALUES ('Item01', 'Dummy item number one for auction.', 5, to_timestamp('2021-06-02 23:30', 'yyyy-MM-dd HH24:MI'), FALSE);
INSERT INTO ITEM (NAME, DESCRIPTION, START_PRICE, DATE_END, AWARDED) VALUES ('Item02', 'Dummy item number two for auction.', 15, to_timestamp('2021-06-02 22:30', 'yyyy-MM-dd HH24:MI'), FALSE);
INSERT INTO ITEM (NAME, DESCRIPTION, START_PRICE, DATE_END, AWARDED) VALUES ('Item03', 'Dummy item number three for auction.', 17, '20210721', FALSE);
INSERT INTO ITEM (NAME, DESCRIPTION, START_PRICE, DATE_END, AWARDED) VALUES ('Item04', 'Dummy item number four for auction.', 19, '20210722', FALSE);
INSERT INTO ITEM (NAME, DESCRIPTION, START_PRICE, DATE_END, AWARDED) VALUES ('Item05', 'Dummy item number five for auction.', 25, '20210620', FALSE);
INSERT INTO ITEM (NAME, DESCRIPTION, START_PRICE, DATE_END, AWARDED) VALUES ('Item06', 'Dummy item number six for auction.', 40, '20210920', FALSE);
INSERT INTO ITEM (NAME, DESCRIPTION, START_PRICE, DATE_END, AWARDED) VALUES ('Item07', 'Dummy item number seven for auction.', 30, '20210530', FALSE);
INSERT INTO ITEM (NAME, DESCRIPTION, START_PRICE, DATE_END, AWARDED) VALUES ('Item08', 'Dummy item number eight for auction.', 31, '20210528', FALSE);
INSERT INTO ITEM (NAME, DESCRIPTION, START_PRICE, DATE_END, AWARDED) VALUES ('Item09', 'Dummy item number nine for auction.', 30, '20210529', FALSE);
INSERT INTO ITEM (NAME, DESCRIPTION, START_PRICE, DATE_END, AWARDED) VALUES ('Item10', 'Dummy item number ten for auction.', 45, '20210620', FALSE);
INSERT INTO ITEM (NAME, DESCRIPTION, START_PRICE, DATE_END, AWARDED) VALUES ('Item11', 'Dummy item number eleven for auction.', 5, '20210620', FALSE);
INSERT INTO ITEM (NAME, DESCRIPTION, START_PRICE, DATE_END, AWARDED) VALUES ('Item12', 'Dummy item number twelve for auction.', 9, '20210720', FALSE);
INSERT INTO BID (USER_ID, BID_PRICE) VALUES (2, 10);
INSERT INTO BID (USER_ID, BID_PRICE) VALUES (1, 11);
INSERT INTO BID (USER_ID, BID_PRICE) VALUES (1, 50);
INSERT INTO BID (USER_ID, BID_PRICE) VALUES (2, 51);
INSERT INTO BID (USER_ID, BID_PRICE) VALUES (1, 50);
INSERT INTO ITEM_BIDS (ITEM_ID, BIDS_ID) VALUES (1, 1);
INSERT INTO ITEM_BIDS (ITEM_ID, BIDS_ID) VALUES (1, 2);
INSERT INTO ITEM_BIDS (ITEM_ID, BIDS_ID) VALUES (2, 3);
INSERT INTO ITEM_BIDS (ITEM_ID, BIDS_ID) VALUES (2, 4);
INSERT INTO ITEM_BIDS (ITEM_ID, BIDS_ID) VALUES (3, 5);