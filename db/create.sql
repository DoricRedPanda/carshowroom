DROP DATABASE carshow;

CREATE DATABASE carshow;

\connect carshow

CREATE TABLE "automodel" (
  "model_id" serial PRIMARY KEY,
  "name" text NOT NULL,
  "year" int NOT NULL,
  "make" text NOT NULL
);

CREATE TABLE "car" (
  "vin" serial PRIMARY KEY,
  "model_id" int NOT NULL,
  "registration_plate" text,
  "price" int NOT NULL,
  "kilometers" real,
  "service_date" date,
  "displacement" int,
  "power" real,
  "top_speed" real,
  "seat_count" int,
  "transmission_type" text,
  "devices" text,
  "color" text,
  "saloon" text,
  CONSTRAINT "FK_car.model_id"
    FOREIGN KEY ("model_id")
      REFERENCES "automodel"("model_id")
);

CREATE TABLE "client" (
  "client_id" serial PRIMARY KEY,
  "name" text NOT NULL,
  "phone" text,
  "email" text,
  "address" text
);

CREATE TABLE "contract" (
  "contract_id" serial PRIMARY KEY,
  "client_id" int NOT NULL,
  "vin" int NOT NULL,
  "date" date NOT NULL,
  "test_drive" bool NOT NULL,
  "status" smallint NOT NULL,
  CONSTRAINT "FK_contract.vin"
    FOREIGN KEY ("vin")
      REFERENCES "car"("vin"),
  CONSTRAINT "FK_contract.client_id"
    FOREIGN KEY ("client_id")
      REFERENCES "client"("client_id")
);

