\connect carshow

INSERT INTO autoModel
	("name", "year", "make") VALUES
	('Maxima', 2022, 'Nissan'),
        ('Qashqai', 2022, 'Nissan'),
        ('Rio', 2022, 'Kia'),
        ('Optima', 2022, 'Kia'),
        ('Forte', 2022, 'Kia');

INSERT INTO client
	("name", "address", "phone", "email") VALUES
	('Luke Smith', 'memes', null, 'luke@lukesmith.xyz'),
        ('John Smith', 'USA', '+77777777777', null),
        ('Richard Stallman', 'USA', null, 'rms@gnu.org'),
        ('Linus Torvalds', 'USA', null, null),
        ('Andrey Stolyarov', 'Russia, Moscow', null, null),
        ('Andrey Stolyarov', 'Russia', null, null);

INSERT INTO car
	("model_id", "registration_plate", "price", "kilometers", "service_date", "displacement", "power", "top_speed", "seat_count", "transmission_type", "devices", "color", "saloon") VALUES
	(1, 'c111mk', 300000, 0, '2017-01-12', 1995, 103, 203, 4, 'AT', 'radio', 'black', 'high'),
	(1, 'c111ab', 400000, 100, '2018-01-12', 1995, 103, 203, 4, 'AT', 'radio,GPS,camera', 'white', 'high'),
	(2, 'c111cd', 350000, 500, '2019-01-12', 1332, 103, 197, 4, 'AT', 'radio', 'black', 'high');


INSERT INTO contract
	("client_id", "vin", "date", "test_drive", "status") VALUES
	(1, 1, '2022-01-01', true, 1),
	(2, 1, '2022-01-02', true, 2),
	(3, 2, '2021-01-01', true, 3),
	(1, 3, '2022-01-01', true, 4),
	(1, 2, '2021-05-01', true, 1),
	(4, 1, '2021-01-01', true, 1);
