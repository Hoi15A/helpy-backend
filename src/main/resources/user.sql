-- -------------------------
-- JobMatcher
-- -------------------------
--
-- Helper
--
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('captain@america.com', 'bio', '2001-01-02', 'Steve', 'Rogers', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '8400', 'M', 2, true); -- pw: m1y9zQYlz
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('spidey@email.com', 'bio', '1998-01-02', 'Peter', 'Parker', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '8403', 'M', 0, true);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('hawkeye@email.com', 'bio', '1998-01-02', 'Clint', 'Barton', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '8404', 'M', 0, true);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('iamironman@email.com', 'bio', '1999-01-02', 'Tony', 'Stark', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '8406', 'M', 1, true);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('leandro@email.com', 'bio', '1997-01-02', 'Leandro', 'Meleti', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '8408', 'M', 0, true);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('hulk@email.com', 'bio', '2000-01-02', 'Bruce', 'Benner', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '8409', 'M', 0, true);

--
-- Helpseeker
--
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('peggy@america.com', 'bio', '2001-01-02', 'Peggy', 'Carter', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '8401', 'F', 0, false);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('mj@email.com', 'bio', '1999-01-02', 'Mary Jane', 'Watson', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '8406', 'F', 0, false);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('pepper@potts.com', 'bio', '1998-01-02', 'Pepper', 'Potts', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '8405', 'F', 0, false);

-- -------------------------
-- Other
-- -------------------------
--
-- Helper
--
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('r-bittencourt@bluewin.ch', 'bio', '1998-01-02', 'Raphaël', 'Bittencourt', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '3186', 'M', 0, true);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('c-trudeau@bluewin.ch', 'bio', '1998-01-02', 'Clovis', 'Trudeau', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '1728', 'M', 0, true);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('e-bassot@bluewin.ch', 'bio', '1998-01-02', 'Émilie', 'Bassot', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '1726', 'F', 0, true);
--
-- Helpseeker
--
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('e-lebeau@bluewin.ch', 'bio', '1998-01-02', 'Edmond', 'LeBeau', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '1700', 'M', 0, false);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('h-fournier@bluewin.ch', 'bio', '1998-01-02', 'Hélène', 'Fournier', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '1763', 'F', 0, false);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('r-millet@bluewin.ch', 'bio', '1998-01-02', 'Rémi', 'Millet', '$2y$12$HEirsCnhaIXkOR1S2MxE1e5d3jyqqln493HtnKsV2OtDlVTPnMbSq', 2, '1752', 'M', 0, false);
insert into USER (email, biographie, birthdate, firstname, lastname, password, permission, plz, sex, status, WANTS_TO_HELP_ACTIVE) values ('ahmed_miri@gmx.net', 'Ich heisse Ahmed und bin 17 Jahre alt und bin seit 2015 in der Schweiz und komme aus Afghanistan. Ich wohne in Winterthur und gehe im Moment in die 10. Klasse. Ich schaue gerne Fussball und spiele beim SC Veltheim in der U19 2. Mannschaft. Ich habe Probleme mit Schreiben und Lesen von wichtigen Papieren in der Schweiz und verstehe sie nicht alle.', '2003-01-05', 'Ahmed', 'Miri', '$2y$12$flo6RSkexyFIRdj5zQjry.kCE9bFR.xkCCfWf6zFD/bUU7ZnV7lHG', 2, '8406', 'M', 1, false);
