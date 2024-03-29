DROP TABLE IF EXISTS SUBSCRIPTION;
CREATE TABLE SUBSCRIPTION( ID INT AUTO_INCREMENT PRIMARY KEY, SUBSCRIPTION_NAME VARCHAR(200) NOT NULL, MONTHLY_PRICE VARCHAR(200) NOT NULL, LAST_UPDATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

INSERT INTO SUBSCRIPTION (SUBSCRIPTION_NAME, MONTHLY_PRICE,LAST_UPDATE) VALUES
  ('INTERNET-SUB-1', '54',CURRENT_TIMESTAMP),
  ('INTERNET-SUB-2', '29',CURRENT_TIMESTAMP),
  ('INTERNET-SUB-3', '38',CURRENT_TIMESTAMP);