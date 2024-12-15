// SignUp Component
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api";

function SignUp() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [role, setRole] = useState("STUDENT");
    const [ageConfirmed, setAgeConfirmed] = useState(false);
    const [termsAccepted, setTermsAccepted] = useState(false);
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleSignUp = async (e) => {
        e.preventDefault();
        setError("");

        if (!ageConfirmed) {
            setError("You must confirm your age to sign up.");
            return;
        }
        if (!termsAccepted) {
            setError("You must agree to the terms and conditions.");
            return;
        }

        try {
            await api.post("/auth/signup", {
                username: email,
                password,
                first_name: firstName,
                last_name: lastName,
                role,
            });
            navigate("/login"); // Redirect to login after successful sign-up
        } catch (err) {
            setError("Error signing up. Please check your details and try again.");
        }
    };

    return (
        <div className="flex flex-col items-center justify-center h-screen bg-gray-100">
            <div className="w-full max-w-md p-8 bg-white rounded shadow-md">
                <h1 className="text-2xl font-bold text-center mb-4">Sign Up</h1>
                {error && <p className="text-red-500 text-sm">{error}</p>}
                <form onSubmit={handleSignUp}>
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
                    <div className="mb-4">
                        <label htmlFor="firstName" className="block text-gray-700">First Name</label>
                        <input
                            type="text"
                            id="firstName"
                            className="w-full px-3 py-2 border rounded"
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="lastName" className="block text-gray-700">Last Name</label>
                        <input
                            type="text"
                            id="lastName"
                            className="w-full px-3 py-2 border rounded"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label htmlFor="role" className="block text-gray-700">Role</label>
                        <select
                            id="role"
                            className="w-full px-3 py-2 border rounded"
                            value={role}
                            onChange={(e) => setRole(e.target.value)}
                        >
                            <option value="STUDENT">Student</option>
                            <option value="TEACHER">Teacher</option>
                            <option value="PARENT">Parent</option>
                        </select>
                    </div>
                    <div className="mb-4 flex items-center">
                        <input
                            type="checkbox"
                            id="ageConfirm"
                            checked={ageConfirmed}
                            onChange={(e) => setAgeConfirmed(e.target.checked)}
                        />
                        <label htmlFor="ageConfirm" className="ml-2 text-gray-700">
                            I confirm that I am 13 years or older.
                        </label>
                    </div>
                    <div className="mb-4 flex items-center">
                        <input
                            type="checkbox"
                            id="termsAccept"
                            checked={termsAccepted}
                            onChange={(e) => setTermsAccepted(e.target.checked)}
                        />
                        <label htmlFor="termsAccept" className="ml-2 text-gray-700">
                            I agree to the terms and conditions.
                        </label>
                    </div>
                    <button type="submit" className="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600">
                        Sign Up
                    </button>
                </form>
                <p className="mt-4 text-center text-gray-600">
                    Already have an account?{" "}
                    <a href="/login" className="text-blue-500 hover:underline">
                        Login
                    </a>
                </p>
            </div>
        </div>
    );
}

export default SignUp;
