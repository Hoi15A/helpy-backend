--
-- custom test data
--

--
-- helper
--
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('captain@america.com', 'bio', '2001-01-02', 'Steve', 'Rogers', 'm1y9zQYlz', 2, '8400', 'M', 2, true);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('spidey@email.com', 'bio', '1998-01-02', 'Peter', 'Parker', 'm1y9zQYlz', 2, '8403', 'M', 0, true);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('hawkeye@email.com', 'bio', '1998-01-02', 'Clint', 'Barton', 'm1y9zQYlz', 2, '8404', 'M', 0, true);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('iamironman@email.com', 'bio', '1999-01-02', 'Tony', 'Stark', 'm1y9zQYlz', 2, '8406', 'M', 1, true);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('leandro@email.com', 'bio', '1997-01-02', 'Leandro', 'Meleti', 'm1y9zQYlz', 2, '8408', 'M', 0, true);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('hulk@email.com', 'bio', '2000-01-02', 'Bruce', 'Benner', 'm1y9zQYlz', 2, '8409', 'M', 0, true);

--
-- helpseeker
--
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('peggy@america.com', 'bio', '2001-01-02', 'Peggy', 'Carter', 'm1y9zQYlz', 2, '8401', 'F', 0, false);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('mj@email.com', 'bio', '1999-01-02', 'Mary Jane', 'Watson', 'm1y9zQYlz', 2, '8406', 'F', 0, false);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('pepper@potts.com', 'bio', '1998-01-02', 'Pepper', 'Potts', 'm1y9zQYlz', 2, '8405', 'F', 0, false);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('ahmed_miri@gmx.net', 'Ich heisse Ahmed und bin 17 Jahre alt und bin seit 2015 in der Schweiz und komme aus Afghanistan. Ich wohne in Winterthur und gehe im Moment in die 10. Klasse. Ich schaue gerne Fussball und spiele beim SC Veltheim in der U19 2. Mannschaft. Ich habe Probleme mit Schreiben und Lesen von wichtigen Papieren in der Schweiz und verstehe sie nicht alle.', '2003-01-05', 'Ahmed', 'Miri', 'password123', 2, '8400', 'M', 1, false);