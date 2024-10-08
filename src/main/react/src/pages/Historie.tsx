import {useEffect, useState} from "react";
import dayjs from "dayjs";
import utc from "dayjs/plugin/utc";
import timezone from "dayjs/plugin/timezone";
import {HttpClientResponse, httpGet} from "../utils/HttpClient.ts";
import {LineChart} from "@mui/x-charts";
import {DateTimePicker} from "@mui/x-date-pickers";

type WetterDto = {
    id?: string,
    zeitpunkt: string,
    temperatur?: number,
    luftfeuchtigkeit?: number,
    taupunkt?: number,
    luftdruck?: number,
    windrichtung?: number,
    windgeschwindigkeit?: number,
    windboeengeschwindigkeit?: number,
    sonnenstrahlung?: number,
    uvIndex?: number,
    niederschlag?: number,
    niederschlagGesamt?: number
};

dayjs.extend(utc);
dayjs.extend(timezone);

function Historie() {
    const [lowerTimeBound, setLowerTimeBound] = useState<Date>(dayjs().subtract(1, 'day'))
    const [upperTimeBound, setUpperTimeBound] = useState<Date>(dayjs())
    const [wetter, setWetter] = useState<WetterDto[]>([]);
    const [isLoading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        getWetter()
    }, [lowerTimeBound, upperTimeBound]);

    const getWetter = () => {
        let lowerTimeBoundString = lowerTimeBound.toISOString()
        let upperTimeBoundString = upperTimeBound.toISOString()

        let path = `/api/wetter/?from=${lowerTimeBoundString}&to=${upperTimeBoundString}`;
        httpGet<WetterDto[]>(path)
            .then((response: HttpClientResponse<WetterDto[]>) => {
                setWetter(response.data)
            })
            .finally(() => setLoading(false));
    }

    const renderLineChart = (labelY: string, zeitpunkte: Date[], data: (number | null)[]) => {
        return (
            <LineChart
                xAxis={[
                    {
                        data: zeitpunkte,
                        tickInterval: "auto",
                        scaleType: "time",
                        valueFormatter: (date) => dayjs(date).format("DD.MM.YYYY H:mm")
                    },
                ]}
                series={[
                    {
                        label: labelY,
                        data: data
                    }
                ]}
                style={{height: "50vh", width: "80vw", marginTop: "50px", marginBottom: "50px"}}
            />
        )
    }

    const renderContent = () => {
        if (isLoading) return <div>Lädt Daten...</div>

        const zeitpunkte = wetter.map(wetter => new Date(wetter.zeitpunkt))

        const temperaturen = wetter.map(wetter => wetter.temperatur ?? null)
        const taupunkte = wetter.map(wetter => wetter.taupunkt ?? null)
        const luftfeuchtigkeiten = wetter.map(wetter => wetter.luftfeuchtigkeit ?? null)
        const luftdruecke = wetter.map(wetter => wetter.luftdruck ?? null)
        const niederschlaege = wetter.map(wetter => wetter.niederschlag ?? null)
        const niederschlaegeDurchschnitt = wetter.map(wetter => wetter.niederschlagGesamt ?? null)
        const sonnenstrahlungen = wetter.map(wetter => wetter.sonnenstrahlung ?? null)
        const unIndizes = wetter.map(wetter => wetter.uvIndex ?? null)
        const windrichtungen = wetter.map(wetter => wetter.windrichtung ?? null)
        const windgeschwindigkeiten = wetter.map(wetter => wetter.windgeschwindigkeit ?? null)
        const windboeengeschwindigkeiten = wetter.map(wetter => wetter.windboeengeschwindigkeit ?? null)

        return (
            <div style={{display: "flex", flexDirection: "column", gap: "50px", marginTop: "10px"}}>
                <div style={{display: "flex", flexDirection: "row", justifyContent: "center", gap: "50px"}}>
                    <DateTimePicker
                        timezone="Europe/Paris"
                        label={"von"}
                        views={['year', 'month', 'day', 'hours', 'minutes']}
                        defaultValue={dayjs().subtract(1, 'day')}
                        onChange={(newValue) => setLowerTimeBound(newValue)}
                    />
                    <DateTimePicker
                        timezone="Europe/Paris"
                        label={"bis"}
                        views={['year', 'month', 'day', 'hours', 'minutes']}
                        defaultValue={dayjs()}
                        onChange={(newValue) => setUpperTimeBound(newValue)}
                    />
                </div>
                <div style={{display: "flex", flexDirection: "column", gap: "10px"}}>
                    {renderLineChart("Temperatur (°C)", zeitpunkte, temperaturen)}
                    {renderLineChart("Taupunkt (°C)", zeitpunkte, taupunkte)}
                    {renderLineChart("Luftfeuchtigkeit (%)", zeitpunkte, luftfeuchtigkeiten)}
                    {renderLineChart("Luftdruck (hPa)", zeitpunkte, luftdruecke)}
                    {renderLineChart("Niederschlag (mm/h)", zeitpunkte, niederschlaege)}
                    {renderLineChart("Niederschlag gesamt (mm)", zeitpunkte, niederschlaegeDurchschnitt)}
                    {renderLineChart("Sonnenstrahlung (fc)", zeitpunkte, sonnenstrahlungen)}
                    {renderLineChart("UV-Index", zeitpunkte, unIndizes)}
                    {renderLineChart("Windrichtung", zeitpunkte, windrichtungen)}
                    {renderLineChart("Windgeschwindigkeit (km/h)", zeitpunkte, windgeschwindigkeiten)}
                    {renderLineChart("Windböen (km/h)", zeitpunkte, windboeengeschwindigkeiten)}
                </div>
            </div>
        )
    }

    return (
        <>
            {renderContent()}
        </>
    )
}

export default Historie