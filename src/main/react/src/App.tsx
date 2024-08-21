import './App.css'
import DashBoard from "./pages/DashBoard.tsx";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import {LocalizationProvider} from "@mui/x-date-pickers";
import 'dayjs/locale/de';

function App() {
    return (
        <>
            <LocalizationProvider dateAdapter={AdapterDayjs} adapterLocale="de">
                <DashBoard/>
            </LocalizationProvider>
        </>
    )
}

export default App
