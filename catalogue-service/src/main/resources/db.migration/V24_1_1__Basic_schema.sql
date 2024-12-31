create schema if not exists words;

CREATE TABLE words.t_verbs
(
    id bigserial primary key,
    infinitive varchar(55) not null,
    verb_v2 varchar(55) not null,
    verb_v3 varchar(55) not null,
    ing varchar(55) not null,
    translate_ua varchar(55) not null
);

INSERT INTO words.t_verbs (infinitive, verb_v2, verb_v3, ing, translate_ua)
VALUES ('enjoy', 'enjoyed', 'enjoyed', 'enjoying', 'обожнювати'),
('want', 'wanted', 'wanted', 'wanting', 'хотіти'),
('sit', 'sat', 'sat', 'sitting', 'сидіти'),
('hurry', 'hurried', 'hurried', 'hurring', 'спішити'),
('see', 'saw', 'seen', 'seeing', 'дивитись'),
('pay','paid','paid', 'paying', 'платити'),
('do', 'did', 'did', 'doing', 'робити'),
(, 'have', 'had', 'had', 'having', 'мати'),
('invoke', 'invoked', 'invoked','invoking', 'визивати');