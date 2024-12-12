import { Routes, Route } from "react-router-dom";
import Dashboard from "./components/Dashboard";
import Modules from "./components/Modules";
import Topics from "./components/Topics";
import GameModes from "./components/GameModes";
import Exercise from "./components/Exercise";
import ResultScreen from "./components/ResultScreen.jsx";

function App() {
    return (
        <div className="min-h-screen bg-primary-50 text-gray-900">
            <Routes>
                <Route path="/" element={<Dashboard />} />
                <Route path="/modules" element={<Modules />} />
                <Route path="/topics/:module" element={<Topics />} />
                <Route path="/game-modes/:topic" element={<GameModes />} />
                <Route path="/exercise/:gameMode" element={<Exercise />} />
                <Route path="/result" element={<ResultScreen />} />
            </Routes>
        </div>
    );
}

export default App;
