import {useEffect, useState} from 'react'
import './App.css'
import {LineChart} from "@mui/x-charts";
import {HttpClientResponse, httpGet} from "./utils/HttpClient.ts";

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

    useEffect(() => getMomentaufnahmen());

    const getMomentaufnahmen = () => {
        let path = "/api/momentaufnahmen/?from=2024-08-13T15:19&to=2024-08-13T15:57";
        httpGet<MomentaufnahmeDto[]>(path)
            .then((response: HttpClientResponse<MomentaufnahmeDto[]>) => setMomentaufnahmen(response.data))
    }

    return (
        <>
            <div>
                <LineChart
                    xAxis={[{data: [momentaufnahmen[0].zeitpunkt, momentaufnahmen[1].zeitpunkt, momentaufnahmen[2].zeitpunkt]}]}
                    series={[
                        {
                            data: [momentaufnahmen[0].temperatur, momentaufnahmen[1].temperatur, momentaufnahmen[2].temperatur],
                        },
                    ]}
                    width={500}
                    height={300}
                />
            </div>
        </>
    )
}

export default App
