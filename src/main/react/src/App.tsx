import './App.css'
import DashBoard from "./pages/DashBoard.tsx";
import {LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";

function App() {
    return (
        <>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DashBoard></DashBoard>
            </LocalizationProvider>
        </>
    )
}

export default App
