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
                <div style={{display: "flex", flexDirection: "row", alignItems: "flex-start"}}>
                    <header>
                        <NavigationBar/>
                    </header>
                    <div style={{flexGrow: 1}}>
                        <main>
                            <Outlet/>
                        </main>
                    </div>
                </div>
            </LocalizationProvider>
        </>
    )
}

export default App
