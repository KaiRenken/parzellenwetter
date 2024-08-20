create table momentaufnahme
(
    id                       uuid PRIMARY KEY not null,
    zeitpunkt                timestamp        not null,
    sonnenstrahlung          float,
    uv_index                 float,
    windrichtung             int,
    luftfeuchtigkeit         int,
    temperatur               int,
    taupunkt                 int,
    windgeschwindigkeit      int,
    windboeengeschwindigkeit int,
    luftdruck                float,
    niederschlag             float,
    niederschlag_gesamt       float
);