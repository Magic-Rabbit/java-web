INSERT INTO "clients" (fullname, phone, email)
VALUES ('Некрасов Александр Борисович', '+79632501491', 'nekrasov@gmail.com'),
       ('Кудрявцева Екатерина Юрьевна', '+79635859942', 'kudryav2018@mail.ru'),
       ('Федоров Адам Фёдорович', '+79635230121', 'adam4ik@rambler.ru'),
       ('Громова Анна Романовна', '+79639755867', 'annet123@yandex.ru'),
       ('Баранов Роман Максимович', '+79632727641', 'baranovrm@gmail.com');


INSERT INTO "company" (company_name, creation_date)
VALUES ('EUROBUS', timestamp '2022-09-28 11:06'),
       ('Красные автобусы', timestamp '2022-09-28 11:06'),
       ('BusTravel', timestamp '2022-09-28 11:06');

INSERT INTO "buses" (company_id, model, year, seats_count)
VALUES (1, 'ГАЗ 322173', 2020, 13),
       (2, 'Yutong ZK6127HQ', 2023, 55),
       (3, 'Ford Transit', 2016, 19),
       (3, 'ГАЗ 322173', 2019, 13);

INSERT INTO "seats" (bus_id, seat_number)
VALUES (1, '1'),
       (1, '2'),
       (1, '3'),
       (1, '4'),
       (1, '5'),
       (1, '6'),
       (1, '7'),
       (1, '8'),
       (1, '9'),
       (1, '10'),
       (1, '11'),
       (1, '12'),
       (1, '13'),
       (2, '1A'),
       (2, '1B'),
       (2, '1C'),
       (2, '1D'),
       (2, '2A'),
       (2, '2B'),
       (2, '2C'),
       (2, '2D'),
       (2, '3A'),
       (2, '3B'),
       (2, '3C'),
       (2, '3D'),
       (2, '4A'),
       (2, '4B'),
       (2, '4C'),
       (2, '4D'),
       (2, '5A'),
       (2, '5B'),
       (2, '5C'),
       (2, '5D'),
       (2, '6A'),
       (2, '6B'),
       (2, '6C'),
       (2, '6D'),
       (2, '7A'),
       (2, '7B'),
       (2, '7C'),
       (2, '7D'),
       (2, '8A'),
       (2, '8B'),
       (2, '8C'),
       (2, '8D'),
       (2, '9A'),
       (2, '9B'),
       (2, '9C'),
       (2, '9D'),
       (2, '10A'),
       (2, '10B'),
       (2, '10C'),
       (2, '10D'),
       (2, '11A'),
       (2, '11B'),
       (2, '11C'),
       (2, '11D'),
       (2, '12A'),
       (2, '12B'),
       (2, '12C'),
       (2, '12D'),
       (2, '13A'),
       (2, '13B'),
       (2, '13C'),
       (2, '13D'),
       (2, '14A'),
       (2, '14B'),
       (2, '14C'),
       (3, '1'),
       (3, '2'),
       (3, '3'),
       (3, '4'),
       (3, '5'),
       (3, '6'),
       (3, '7'),
       (3, '8'),
       (3, '9'),
       (3, '10'),
       (3, '11'),
       (3, '12'),
       (3, '13'),
       (3, '14'),
       (3, '15'),
       (3, '16'),
       (3, '17'),
       (3, '18'),
       (3, '19'),
       (4, '1'),
       (4, '2'),
       (4, '3'),
       (4, '4'),
       (4, '5'),
       (4, '6'),
       (4, '7'),
       (4, '8'),
       (4, '9'),
       (4, '10'),
       (4, '11'),
       (4, '12'),
       (4, '13');

INSERT INTO "waypoint" (waypoint_name)
VALUES ('Москва'),
       ('Тверь'),
       ('Великий Новгород'),
       ('Санкт-Петербург');

-- [{"waypoint_id":1,"date":1695279961000},{"waypoint_id":2,"date":1695288482000},{"waypoint_id":3,"date":1695298263000},{"waypoint_id":4,"date":1695311404000}]

INSERT INTO "routes" (departure, departure_date, bus_id, arrival, arrival_date)
VALUES (1, '2023-09-21 10:06', 1, 4, '2023-09-21 18:50'),
       (1, '2023-09-20 10:06', 4, 3, '2023-09-20 18:50'),
       (2, '2023-09-19 10:06', 2, 4, '2023-09-19 18:50');


-- INSERT INTO "routes" (departure, departure_date, bus_id, arrival, waypoints)
-- VALUES (1, '2023-09-21', 1, 4, [{"waypoint_id":1,"date":1695279961000},{"waypoint_id":2,"date":1695288482000},{"waypoint_id":3,"date":1695298263000},{"waypoint_id":4,"date":1695311404000}]::jsonb[]);

INSERT INTO "tickets" (route_id, seat_number, client_id, price, status)
VALUES (1, '1', 1, 999.99, 'SOLD'),
       (1, '2', NULL, 999.99, 'AVAILABLE'),
       (1, '3', NULL, 999.99, 'AVAILABLE'),
       (1, '4', NULL, 999.99, 'AVAILABLE'),
       (1, '5', NULL, 999.99, 'AVAILABLE'),
       (1, '6', NULL, 999.99, 'AVAILABLE'),
       (1, '7', NULL, 999.99, 'AVAILABLE'),
       (1, '8', NULL, 999.99, 'AVAILABLE'),
       (1, '9', NULL, 999.99, 'AVAILABLE'),
       (1, '10', NULL, 999.99, 'AVAILABLE'),
       (1, '11', NULL, 999.99, 'AVAILABLE'),
       (1, '12', NULL, 999.99, 'AVAILABLE'),
       (1, '13', NULL, 999.99, 'AVAILABLE');