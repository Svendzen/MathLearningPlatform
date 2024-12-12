import { useParams, useNavigate, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../api";
import ClassicModeExercise from "./ClassicModeExercise";
import { validTopics } from "./Topics";

function Exercise() {
    const { gameMode } = useParams();
    const navigate = useNavigate(); // Add back useNavigate
    const location = useLocation(); // Keep useLocation for query parameters
    const [exercise, setExercise] = useState(null);
    const [error, setError] = useState(null);

    // Extract the topic from the query string
    const searchParams = new URLSearchParams(location.search);
    const topic = searchParams.get("topic");
    console.log("Parsed topic:", topic);
    console.log("gameMode:", gameMode);


    useEffect(() => {
        console.log("gameMode:", gameMode);
        console.log("topic:", topic);

        async function fetchExercise() {
            try {
                // Validate the topic before making the API call
                if (!validTopics.includes(topic)) {
                    console.error(`Invalid topic: ${topic}`);
                    setError(`Invalid topic: ${topic}`);
                    return;
                }

                console.log("Fetching exercise...");
                const response = await api.post(`/content/gamemode/initialize`, {
                    gameModeId: gameMode,
                    topic,
                });
                console.log("API Response:", response);
                setExercise(response);
            } catch (error) {
                console.error("Error initializing exercise:", error);
                setError(error.message);
            }
        }

        if (topic) {
            console.log("Calling fetchExercise...");
            fetchExercise();
        }
    }, [gameMode, topic]);

    const handleComplete = (answers) => {
        console.log("Exercise completed!", answers);
        alert("Exercise completed!");
        navigate(-1); // Go back to the topics
    };

    if (error) return <p className="text-red-500">{error}</p>;
    if (!exercise) return <p>Loading...</p>;

    return (
        <div className="p-4">
            <h1 className="text-xl font-bold">{exercise.name}</h1>
            <p>{exercise.description}</p>

            <div className="flex justify-between items-center mt-4">
                <button
                    onClick={() => navigate(-1)} // Go back to the previous page
                    className="bg-gray-500 text-white px-4 py-2 rounded"
                >
                    Back to Topics
                </button>
            </div>

            {/* Render ClassicModeExercise */}
            {exercise.name === "Classic Mode" && (
                <ClassicModeExercise exercise={exercise} onComplete={handleComplete} />
            )}
        </div>
    );
}

export default Exercise;
