CREATE TABLE IF NOT EXISTS FILMS
(
    ID       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    UUID     UUID,
    TITLE    VARCHAR,
    CATEGORY VARCHAR,
    YEAR     int
);
CREATE TABLE IF NOT EXISTS SCREENINGS
(
    ID         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    UUID       UUID,
    MIN_AGE    INT,
    DATE       DATETIME,
    FREE_SEATS INT,
    FILM_ID    BIGINT,
    FOREIGN KEY (FILM_ID) REFERENCES FILMS (ID)
);
CREATE TABLE IF NOT EXISTS TICKETS
(
    ID           UUID PRIMARY KEY,
    FIRST_NAME   VARCHAR,
    LAST_NAME    VARCHAR,
    PRIZE        BIGINT,
    STATUS       ENUM ('OPEN', 'CANCELLED'),
    SCREENING_ID BIGINT,
    FOREIGN KEY (SCREENING_ID) REFERENCES SCREENINGS (ID)
);
CREATE TABLE IF NOT EXISTS USERS
(
    USERNAME VARCHAR PRIMARY KEY,
    PASSWORD VARCHAR,
    ENABLED  BOOLEAN
);
CREATE TABLE IF NOT EXISTS AUTHORITIES
(
    USERNAME  VARCHAR,
    AUTHORITY VARCHAR,
    FOREIGN KEY (USERNAME) REFERENCES USERS (USERNAME)
)