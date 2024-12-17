import dayjs from "dayjs";
import utc from "dayjs/plugin/utc";
import timezone from "dayjs/plugin/timezone";
import {useEffect, useState} from "react";
import {HttpClientResponse, httpGet, httpPost} from "../utils/HttpClient.ts";
import {
    Box,
    Button,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField
} from "@mui/material";

type CreateNotizDto = {
    verfasser: string,
    nachricht: string
};

type ReadNotizDto = {
    id: string
    verfasser: string,
    nachricht: string,
    zeitpunkt: string
};

dayjs.extend(utc);
dayjs.extend(timezone);

function Notizbrett() {
    const [verfasser, setVerfasser] = useState<string>("")
    const [nachricht, setNachricht] = useState<string>("")
    const [notizen, setNotizen] = useState<ReadNotizDto[]>([]);
    const [isLoading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        getNotizen()
    }, []);

    const getNotizen = () => {
        let path = `/api/notiz/`;
        httpGet<ReadNotizDto[]>(path)
            .then((response: HttpClientResponse<ReadNotizDto[]>) => {
                setNotizen(response.data)
            })
            .finally(() => setLoading(false));
    }

    const postNotiz = () => {
        let path = `/api/notiz/`;
        httpPost<ReadNotizDto[]>(path, {
            verfasser: verfasser,
            nachricht: nachricht
        })
            .then(() => {
                setVerfasser("")
                setNachricht("")
                getNotizen()
            });
    }

    const renderContent = () => {
        if (isLoading) return <div>Lädt Daten...</div>

        return (
            <div style={{display: "flex", flexDirection: "column", gap: "50px", marginTop: "10px"}}>
                <div style={{display: "flex", flexDirection: "row", justifyContent: "center", gap: "50px"}}>
                    <Box
                        component="form"
                        sx={{'& .MuiTextField-root': {m: 1, width: '25ch'}}}
                        noValidate
                        autoComplete="off"
                    >
                        <div>
                            <TextField
                                required
                                id="verfasser"
                                label="Dein Name"
                                onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
                                    setVerfasser(event.target.value);
                                }}
                            />
                            <TextField
                                required
                                id="nachricht"
                                label="Deine Notiz"
                                onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
                                    setNachricht(event.target.value);
                                }}
                            />
                            <Button
                                variant="contained"
                                onClick={() => {
                                    postNotiz()
                                }}
                            >
                                Los!
                            </Button>
                        </div>
                    </Box>
                </div>
                <div style={{display: "flex", flexDirection: "column", gap: "10px"}}>
                    <TableContainer component={Paper}>
                        <Table sx={{minWidth: 650}} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell>Verfasser</TableCell>
                                    <TableCell align="right">Nachricht</TableCell>
                                    <TableCell align="right">Zeitpunkt</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {notizen.map((notiz) => (
                                    <TableRow
                                        key={notiz.id}
                                        sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                    >
                                        <TableCell component="th" scope="row">
                                            {notiz.verfasser}
                                        </TableCell>
                                        <TableCell align="right">{notiz.nachricht}</TableCell>
                                        <TableCell align="right">{notiz.zeitpunkt}</TableCell>
                                    </TableRow>
                                ))}
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

export default Notizbrett