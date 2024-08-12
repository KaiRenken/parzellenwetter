create table momentaufnahme
(
    id              uuid PRIMARY KEY not null,
    obs_time_local  timestamp        not null,
    solar_radiation float,
    uv              float,
    winddir         int,
    humidity        int,
    temp            int,
    dewpt           int,
    wind_speed      int,
    wind_gust       int,
    pressure        float,
    percip_rate     float,
    percip_total    float
);