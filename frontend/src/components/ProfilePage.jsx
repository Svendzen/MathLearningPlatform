import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api.js";

function ProfilePage() {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const navigate = useNavigate();
    const token = localStorage.getItem("token"); // Token for Authorization
    const userId = localStorage.getItem("userId"); // User ID

    useEffect(() => {
        if (!token || !userId) {
            setError("Unauthorized: Please log in again.");
            setLoading(false);
            return;
        }

        const authHeaders = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };

        console.log("Request Headers:", authHeaders);

        api.get(`/users/${userId}`, authHeaders)
            .then((response) => {
                setUser(response);
                setLoading(false);
            })
            .catch((err) => {
                console.error("Error fetching user info:", err.response || err.message);
                setError("Failed to load profile. Please try again later.");
                setLoading(false);
            });
    }, [token, userId]);

    if (loading) return <p className="p-4">Loading profile...</p>;
    if (error) return <p className="p-4 text-red-500">{error}</p>;

    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold text-blue-600">Your Profile</h1>
            <div className="mt-4 space-y-2">
                <p>
                    <strong>Name:</strong> {user.firstName + " " + user.lastName}
                </p>
                <p>
                    <strong>Email:</strong> {user.username}
                </p>
                <p>
                    <strong>Role:</strong> {user.role}
                </p>
                <p>
                    <strong>Joined:</strong>{" Sometime in the distant past... or the future? 🕰️"}
                </p>
            </div>

            {/* Back to StudentDashboard */}
            <div className="mt-4">
                <button
                    onClick={() => navigate("/")}
                    className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded transition duration-300"
                >
                    Back to Dashboard
                </button>
            </div>
        </div>
    );
}

export default ProfilePage;
