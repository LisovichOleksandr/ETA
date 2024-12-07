CREATE TABLE verbs
(
    id bigserial primary key,
    infinitive varchar(55) not null,
    verb_v2 varchar(55) not null,
    verb_v3 varchar(55) not null,
    ing varchar(55) not null,
    translate_ua varchar(55) not null
);

INSERT INTO verbs (infinitive, verb_v2, verb_v3, ing, translate_ua)
VALUES ('enjoy', 'enjoyed', 'enjoyed', 'enjoying', 'обожнювати'),
('invoke', 'invoked', 'invoked','invoking', 'визивати');