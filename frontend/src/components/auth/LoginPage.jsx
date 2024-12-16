// Login Component
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api";

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError(""); // Reset error state
        try {
            const response = await api.post("/users/authenticate", { username: email, password });

            console.log("Login successful, response:", response); // Log response

            // Save JWT and role to localStorage
            localStorage.setItem("token", response.token);
            localStorage.setItem("role", response.role);

            // Save username (email used to log in)
            localStorage.setItem("username", email);

            // Fetch additional user details using the username
            const userDetailsResponse = await api.get(`/users/username/${email}`, {
                headers: { Authorization: `Bearer ${response.token}` }, // Include the token in headers
            });

            localStorage.setItem("firstName", userDetailsResponse.firstName);
            localStorage.setItem("lastName", userDetailsResponse.lastName);

            navigate("/"); // Redirect to dashboard
        } catch (err) {
            console.error("Login failed, error:", err); // Log error details
            if (err.response) {
                console.error("Server responded with:", err.response.status, err.response.data);
            }
            setError("Invalid email or password");
        }
    };



    return (
        <div className="flex flex-col items-center justify-center h-screen bg-gray-100">
            <div className="w-full max-w-md p-8 bg-white rounded shadow-md">
                <h1 className="text-2xl font-bold text-center mb-4">Login</h1>
                {error && <p className="text-red-500 text-sm">{error}</p>}
                <form onSubmit={handleLogin}>
                    <div className="mb-4">
                        <label htmlFor="email" className="block text-gray-700">Email</label>
                        <input
                            type="email"
                            id="email"
                            className="w-full px-3 py-2 border rounded"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="password" className="block text-gray-700">Password</label>
                        <input
                            type="password"
                            id="password"
                            className="w-full px-3 py-2 border rounded"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600">
                        Login
                    </button>
                </form>
                <p className="mt-4 text-center text-gray-600">
                    New user?{" "}
                    <a href="/signup" className="text-blue-500 hover:underline">
                        Sign Up
                    </a>
                </p>
            </div>
        </div>
    );
}

export default Login;

// Comment: Future Enhancement
// Consider migrating to a global state management solution like React Context or Redux
// for handling user data (token, role, username) instead of relying on localStorage.
