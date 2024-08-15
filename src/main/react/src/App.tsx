import {useEffect, useState} from 'react'
import './App.css'
import {LineChart} from "@mui/x-charts";
import {HttpClientResponse, httpGet} from "./utils/HttpClient.ts";
import dayjs from "dayjs";

type MomentaufnahmeDto = {
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
    niederschlagDurchschnitt?: number
};

function App() {
    const [momentaufnahmen, setMomentaufnahmen] = useState<MomentaufnahmeDto[]>([]);
    const [isLoading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        getMomentaufnahmen()
    }, []);

    const getMomentaufnahmen = () => {
        let path = "/api/momentaufnahme/?from=2024-08-13T15:19&to=2024-08-13T15:57";
        httpGet<MomentaufnahmeDto[]>(path)
            .then((response: HttpClientResponse<MomentaufnahmeDto[]>) => {
                setMomentaufnahmen(response.data)
            })
            .finally(() => setLoading(false));
    }

    const renderContent = () => {
        if (isLoading) return <div>Lädt Daten...</div>

        const xAxisData = momentaufnahmen.map(momentaufnahme => new Date(momentaufnahme.zeitpunkt))

        const yAxisData = momentaufnahmen.map(momentaufnahme => momentaufnahme.temperatur ?? null)

        return (
            <div>
                <LineChart
                    xAxis={[
                        {
                            label: "Date",
                            data: xAxisData,
                            tickInterval: "auto",
                            scaleType: "time",
                            valueFormatter: (date) => dayjs(date).format("DD/MM/YYYY H:mm")
                        },
                    ]}
                    yAxis={[{label: "Temperatur (°C)"}]}
                    series={[
                        {
                            data: yAxisData
                        },
                    ]}
                    height={1000}
                    width={1500}
                />
            </div>
        )
    }

    return (
        <>
            {renderContent()}
        </>
    )
}

export default App
