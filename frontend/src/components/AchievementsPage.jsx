import { useEffect, useState } from "react";
import api from "../api.js";
import { Link } from "react-router-dom";

function AchievementsPage() {
    const [trophies, setTrophies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const studentId = localStorage.getItem("userId");
    const token = localStorage.getItem("token");

    useEffect(() => {
        if (!token) {
            setError("Unauthorized: No token found. Please log in.");
            setLoading(false);
            return;
        }

        const authHeaders = {
            headers: { Authorization: `Bearer ${token}` },
        };

        api.get(`/gamification/trophies/${studentId}`, authHeaders)
            .then((response) => {
                setTrophies(response || []);
                setLoading(false);
            })
            .catch((err) => {
                console.error("Error fetching trophies:", err);
                setError("Failed to load achievements. Please try again later.");
                setLoading(false);
            });
    }, [studentId, token]);

    if (loading) return <p className="p-4">Loading achievements...</p>;
    if (error) return <p className="p-4 text-red-500">{error}</p>;

    // Helper function for trophy color
    const getTrophyColor = (level) => {
        switch (level) {
            case "BRONZE":
                return "text-yellow-700"; // Bronze shade
            case "SILVER":
                return "text-gray-500"; // Silver shade
            case "GOLD":
                return "text-yellow-500"; // Gold shade
            default:
                return "text-gray-700"; // Default color
        }
    };

    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold text-yellow-600">Your Achievements</h1>
            <p className="mt-2 text-gray-700">See all the trophies you have earned!</p>

            {trophies.length > 0 ? (
                <div className="mt-4 space-y-4">
                    {trophies.map((trophy) => (
                        <div
                            key={trophy.id}
                            className="p-4 border rounded shadow bg-white"
                        >
                            <h2 className="text-lg font-semibold">
                                {trophy.mathTopic} - {trophy.gameMode}
                            </h2>
                            <p className={`font-medium ${getTrophyColor(trophy.trophyLevel)}`}>
                                Trophy Level: <strong>{trophy.trophyLevel}</strong>
                            </p>
                        </div>
                    ))}
                </div>
            ) : (
                <p className="mt-4 text-gray-600">No trophies earned yet. Keep going!</p>
            )}

            {/* Back to Dashboard */}
            <div className="mt-4">
                <Link
                    to="/"
                    className="inline-block bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded transition duration-300"
                >
                    Back to Dashboard
                </Link>
            </div>
        </div>
    );
}

export default AchievementsPage;
