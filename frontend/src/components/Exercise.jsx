import { useParams, useNavigate, useLocation, Link } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../api";
import ClassicExercise from "./ClassicExercise.jsx";
import { validTopics } from "./Topics";

function Exercise() {
    const { gameMode } = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const [exercise, setExercise] = useState(null);
    const [error, setError] = useState(null);

    const searchParams = new URLSearchParams(location.search);
    const topic = searchParams.get("topic");

    useEffect(() => {
        async function fetchExercise() {
            try {
                if (!validTopics.includes(topic)) {
                    console.error(`Invalid topic: ${topic}`);
                    throw new Error(`Invalid topic: ${topic}`);
                }

                console.log(`Fetching exercise for gameMode: ${gameMode}, topic: ${topic}`);
                const response = await api.post(`/content/gamemode/initialize`, {
                    gameModeId: gameMode,
                    topic,
                });

                console.log("Exercise fetched successfully:", response);
                setExercise(response);
            } catch (error) {
                console.error("Error initializing exercise:", error);
                setError(error.message);
            }
        }

        if (topic) fetchExercise();
    }, [gameMode, topic]);

    if (error) return <p className="text-red-500">{error}</p>;
    if (!exercise) return <p>Loading...</p>;

    return (
        <div className="p-4">
            {/* Breadcrumb navigation */}
            <nav className="mb-4">
                <Link to="/modules" className="text-blue-500 underline">Modules</Link> &gt;{" "}
                <Link to={`/topics/${topic}`} className="text-blue-500 underline">Topics</Link> &gt;{" "}
                <Link to={`/game-modes/${topic}`} className="text-blue-500 underline">Game Modes</Link> &gt;{" "}
                <span className="text-gray-700">Exercise</span>
            </nav>

            <h1 className="text-xl font-bold">{exercise.name}</h1>
            <p>{exercise.description}</p>

            <div className="flex justify-between items-center mt-4">
                <button
                    onClick={() => navigate(-1)} // Go back
                    className="bg-gray-500 text-white px-4 py-2 rounded"
                >
                    Back
                </button>
                <button
                    onClick={() => navigate("/modules")} // Go to modules
                    className="bg-blue-500 text-white px-4 py-2 rounded"
                >
                    Restart
                </button>
                <button
                    onClick={() => navigate("/")} // Go to dashboard
                    className="bg-green-500 text-white px-4 py-2 rounded"
                >
                    Go Home
                </button>
            </div>

            {exercise.name === "Classic" && (
                <ClassicExercise exercise={exercise} />
            )}
        </div>
    );
}

export default Exercise;
