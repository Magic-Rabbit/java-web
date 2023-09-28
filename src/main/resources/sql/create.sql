DROP TABLE IF EXISTS "clients" CASCADE;
CREATE TABLE "clients"
(
    "client_id" SERIAL PRIMARY KEY,
    "fullname" TEXT NOT NULL,
    "phone" TEXT NOT NULL,
    "email" TEXT NOT NULL
);

DROP TABLE IF EXISTS "company" CASCADE;
CREATE TABLE "company"
(
    "company_id" SERIAL PRIMARY KEY,
    "company_name" TEXT NOT NULL ,
    "creation_date" TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS "buses" CASCADE;
CREATE TABLE "buses"
(
    "bus_id" SERIAL PRIMARY KEY,
    "company_id" INT REFERENCES "company" ("company_id") ON DELETE CASCADE,
    "model" TEXT NOT NULL,
    "year" INT NOT NULL,
    "seats_count" INT NOT NULL
);

DROP TABLE IF EXISTS "seats" CASCADE;
CREATE TABLE "seats"
(
    "bus_id" INT REFERENCES "buses" ("bus_id") ON DELETE CASCADE,
    "seat_number" TEXT NOT NULL,
    PRIMARY KEY ("bus_id", "seat_number")
);

DROP TABLE IF EXISTS "waypoint" CASCADE;
CREATE TABLE "waypoint"
(
    "waypoint_id" SERIAL PRIMARY KEY,
    "waypoint_name" TEXT NOT NULL
);

DROP TABLE IF EXISTS "routes" CASCADE;
CREATE TABLE "routes"
(
    "route_id" SERIAL PRIMARY KEY,
    "departure" INT REFERENCES "waypoint" ("waypoint_id") ON DELETE CASCADE,
    "departure_date" TIMESTAMP NOT NULL,
    "bus_id" INT REFERENCES "buses" ("bus_id") ON DELETE CASCADE,
    "arrival" INT REFERENCES "waypoint" ("waypoint_id") ON DELETE CASCADE,
    "arrival_date" TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS "tickets" CASCADE;
CREATE TABLE "tickets"
(
    "ticket_id" SERIAL PRIMARY KEY,
    "route_id" INT REFERENCES "routes" ("route_id") ON DELETE CASCADE,
    "seat_number" TEXT NOT NULL,
    "client_id" INT REFERENCES "clients" ("client_id") ON DELETE CASCADE,
    "price" FLOAT NOT NULL,
    "status" TEXT NOT NULL
);