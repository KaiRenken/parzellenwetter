import './App.css'
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import {LocalizationProvider} from "@mui/x-date-pickers";
import 'dayjs/locale/de';
import NavigationBar from "./navigation/NavigationBar.tsx";
import {Outlet} from "react-router-dom";

function App() {
    return (
        <>
            <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="de">
                <div style={{display: "flex", flexDirection: "row", gap: "20px"}}>
                    <div>
                        <NavigationBar/>
                    </div>
                    <div style={{maxHeight: "100vh", overflow: "auto"}}>
                        <Outlet/>
                    </div>
                </div>
            </LocalizationProvider>
        </>
    )
}

export default App
