DROP TABLE IF EXISTS vendors;
CREATE TABLE vendors (
  id INTEGER PRIMARY KEY ASC,
  first_name VARCHAR(80),
  last_name VARCHAR(80),
  phone_number VARCHAR(80),
  delivered INTEGER(1),
  dirty INTEGER(1),
  fetched INTEGER(1),
  received_money INTEGER(1)
);