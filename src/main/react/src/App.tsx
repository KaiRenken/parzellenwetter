import {useState} from 'react'
import './App.css'
import {LineChart} from "@mui/x-charts";

function App() {
    const [count, setCount] = useState(0)

    return (
        <>
            <div>
                <LineChart
                    xAxis={[{data: [1, 2, 3, 5, 8, 10]}]}
                    series={[
                        {
                            data: [2, 5.5, 2, 8.5, 1.5, 5],
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
