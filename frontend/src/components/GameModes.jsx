import { useParams, Link } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../api";

function GameModes() {
    const { topic } = useParams();
    const [gameModes, setGameModes] = useState([]);

    useEffect(() => {
        async function fetchGameModes() {
            try {
                const response = await api.get(`/content/gamemode/by-topic/${topic}`);
                setGameModes(response);
            } catch (error) {
                console.error("Error fetching game modes:", error);
            }
        }

        fetchGameModes();
    }, [topic]);

    return (
        <div className="p-4">
            {/* Breadcrumb navigation */}
            <nav className="mb-4">
                <Link to="/modules" className="text-blue-500 underline">Modules</Link> &gt;{" "}
                <Link to={`/topics/${topic}`} className="text-blue-500 underline">Topics</Link> &gt;{" "}
                <span className="text-gray-700">Game Modes</span>
            </nav>

            <h1 className="text-xl font-bold">Game Modes for {topic}</h1>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
                {gameModes.map((mode) => (
                    <Link
                        to={`/exercise/${mode.id}?topic=${topic}`}
                        key={mode.id}
                        className="block bg-primary-500 text-white p-4 rounded shadow"
                    >
                        {mode.name}
                    </Link>
                ))}
            </div>
        </div>
    );
}

export default GameModes;
