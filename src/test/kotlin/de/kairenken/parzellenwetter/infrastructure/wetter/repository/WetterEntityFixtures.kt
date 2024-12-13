package de.kairenken.parzellenwetter.infrastructure.wetter.repository

import de.kairenken.parzellenwetter.domain.wetter.wetterFixture

val wetterEntityFixture = WetterEntity(
    id = wetterFixture.id.value,
    zeitpunkt = wetterFixture.zeitpunkt,
    temperatur = wetterFixture.temperatur,
    luftfeuchtigkeit = wetterFixture.luftfeuchtigkeit,
    taupunkt = wetterFixture.taupunkt,
    luftdruck = wetterFixture.luftdruck,
    windrichtung = wetterFixture.windrichtung,
    windgeschwindigkeit = wetterFixture.windgeschwindigkeit,
    windboeengeschwindigkeit = wetterFixture.windboeengeschwindigkeit,
    sonnenstrahlung = wetterFixture.sonnenstrahlung,
    uvIndex = wetterFixture.uvIndex,
    niederschlag = wetterFixture.niederschlag,
    niederschlagGesamt = wetterFixture.niederschlagGesamt
)