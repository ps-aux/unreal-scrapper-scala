DROP TABLE IF EXISTS record;
CREATE TABLE record (
  title       VARCHAR(256),
  url         VARCHAR(256),
  area        INTEGER,
  price       INTEGER,
  location    VARCHAR(256),
  type        VARCHAR(256),
  date        VARCHAR(256),
  scrapSource VARCHAR(256),
  scrapDate   DATE
);
