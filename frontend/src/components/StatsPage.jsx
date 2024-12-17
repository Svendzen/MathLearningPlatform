import { useEffect, useState } from "react";
import api from "../api.js";
import { Link } from "react-router-dom";

function StatsPage() {
    const [progress, setProgress] = useState(null);
    const [exerciseResults, setExerciseResults] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const studentId = localStorage.getItem("userId");
    const token = localStorage.getItem("token"); // Fetch JWT token for Authorization header

    useEffect(() => {
        if (!token) {
            setError("Unauthorized: No token found. Please log in.");
            setLoading(false);
            return;
        }

        // Custom headers with Authorization
        const authHeaders = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };

        // Fetch progress and exercise results concurrently
        Promise.all([
            api.get(`/progress/student/${studentId}`, authHeaders), // Progress
            api.get(`/progress/exercise-results/${studentId}`, authHeaders), // Results
        ])
            .then(([progressResponse, resultsResponse]) => {
                console.log("Progress Response:", progressResponse);
                console.log("Exercise Results Response:", resultsResponse);

                setProgress(progressResponse || {}); // Use the response directly
                setExerciseResults(resultsResponse || []);
                setLoading(false);
            })
            .catch((err) => {
                console.error("Error fetching stats data:", err);
                setError("Failed to load statistics. Please try again later.");
                setLoading(false);
            });
    }, [studentId, token]);

    if (loading) return <p className="p-4">Loading stats...</p>;
    if (error) return <p className="p-4 text-red-500">{error}</p>;

    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold text-blue-700">Your Stats</h1>
            <p className="mt-2 text-gray-600">
                Review your progress and exercise performance.
            </p>
            {/* Back to Dashboard */}
            <div className="mt-4">
                <Link
                    to="/"
                    className="inline-block bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded transition duration-300"
                >
                    Back to Dashboard
                </Link>
            </div>
            {/* General Progress */}
            {progress && (
                <div className="mt-4 border p-4 rounded shadow">
                    <h2 className="text-xl font-semibold text-gray-700">
                        General Progress
                    </h2>
                    <ul className="mt-2 space-y-1">
                        <li>
                            <strong>Total Exercises Completed:</strong>{" "}
                            {progress.totalExercisesCompleted}
                        </li>
                        <li>
                            <strong>Total Correct Answers:</strong>{" "}
                            {progress.totalCorrectAnswers}
                        </li>
                        <li>
                            <strong>Total Incorrect Answers:</strong>{" "}
                            {progress.totalIncorrectAnswers}
                        </li>
                        <li>
                            <strong>Overall Accuracy:</strong>{" "}
                            {(progress.overallAccuracy * 100).toFixed(2)}%
                        </li>
                    </ul>
                </div>
            )}

            {/* Exercise Results */}
            <div className="mt-4 border p-4 rounded shadow">
                <h2 className="text-xl font-semibold text-gray-700">
                    Exercise Results
                </h2>
                {Array.isArray(exerciseResults) && exerciseResults.length > 0 ? (
                    <table className="mt-2 w-full border-collapse border">
                        <thead>
                        <tr className="bg-gray-200">
                            <th className="border p-2">Date</th>
                            <th className="border p-2">Topic</th>
                            <th className="border p-2">Game Mode</th>
                            <th className="border p-2">Score</th>
                            <th className="border p-2">Correct Answers</th>
                            <th className="border p-2">Total Questions</th>
                            <th className="border p-2">Time (s)</th>
                        </tr>
                        </thead>
                        <tbody>
                        {exerciseResults.map((result) => (
                            <tr key={result.id} className="text-center">
                                <td className="border p-2">
                                    {new Date(result.completionDate).toLocaleDateString()}
                                </td>
                                <td className="border p-2">{result.mathTopic}</td>
                                <td className="border p-2">{result.gameMode}</td>
                                <td className="border p-2">{result.score}</td>
                                <td className="border p-2">{result.correctAnswers}</td>
                                <td className="border p-2">{result.totalQuestions}</td>
                                <td className="border p-2">{result.completionTime}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                ) : (
                    <p className="mt-2 text-gray-600">
                        {exerciseResults === undefined
                            ? "Loading results..."
                            : "No exercise results available yet."}
                    </p>
                )}

            </div>
        </div>
    );
}

export default StatsPage;
