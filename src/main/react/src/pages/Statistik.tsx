import {useEffect, useState} from "react";
import dayjs from "dayjs";
import utc from "dayjs/plugin/utc";
import timezone from "dayjs/plugin/timezone";
import {HttpClientResponse, httpGet} from "../utils/HttpClient.ts";
import {DateTimePicker} from "@mui/x-date-pickers";
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";

type ExtremwerteDto = {
    temperatur?: {
        minimum: {
            wert: number,
            zeitpunkt: string,
            wetterId: string
        },
        maximum: {
            wert: number,
            zeitpunkt: string,
            wetterId: string
        }
    }
};

dayjs.extend(utc);
dayjs.extend(timezone);

function Statistik() {
    const [lowerTimeBound, setLowerTimeBound] = useState<Date>(dayjs().subtract(1, 'day'))
    const [upperTimeBound, setUpperTimeBound] = useState<Date>(dayjs())
    const [extremwerte, setExtremwerte] = useState<ExtremwerteDto[]>([]);
    const [isLoading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        getExtremwerte()
    }, [lowerTimeBound, upperTimeBound]);

    const getExtremwerte = () => {
        let lowerTimeBoundString = lowerTimeBound.toISOString()
        let upperTimeBoundString = upperTimeBound.toISOString()

        let path = `/api/wetter/extremwerte/?from=${lowerTimeBoundString}&to=${upperTimeBoundString}`;
        httpGet<ExtremwerteDto[]>(path)
            .then((response: HttpClientResponse<ExtremwerteDto[]>) => {
                setExtremwerte(response.data)
            })
            .finally(() => setLoading(false));
    }

    const renderContent = () => {
        if (isLoading) return <div>Lädt Daten...</div>

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
                    <TableContainer component={Paper}>
                        <Table sx={{minWidth: 650}} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell>Wert</TableCell>
                                    <TableCell align="right">Maximum</TableCell>
                                    <TableCell align="right">Minimum</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {(extremwerte.temperatur !== null) &&
                                    <TableRow
                                        key="Temperatur"
                                        sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                    >
                                        <TableCell component="th" scope="row">
                                            Temperatur
                                        </TableCell>
                                        <TableCell align="right">{extremwerte.temperatur.minimum.wert}</TableCell>
                                        <TableCell align="right">{extremwerte.temperatur.maximum.wert}</TableCell>
                                    </TableRow>
                                }
                            </TableBody>
                        </Table>
                    </TableContainer>
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

export default Statistik