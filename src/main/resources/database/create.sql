DROP TABLE IF EXISTS vendors;
CREATE TABLE vendors (
  vendor_id INTEGER PRIMARY KEY,
  first_name VARCHAR(80),
  last_name VARCHAR(80),
  phone_number VARCHAR(80),
  delivered INTEGER(1),
  dirty INTEGER(1),
  fetched INTEGER(1),
  received_money INTEGER(1)
);

CREATE TABLE purchases (
  vendor_id INTEGER PRIMARY KEY,
  item_quantity INTEGER,
  item_number INTEGER,
  item_price INTEGER,
  sum DOUBLE,
  profit DOUBLE,
  payment DOUBLE
);
