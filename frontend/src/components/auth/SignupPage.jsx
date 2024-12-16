import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api";

function SignUp() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [role, setRole] = useState("STUDENT");
    const [ageConfirmed, setAgeConfirmed] = useState(false);
    const [termsAccepted, setTermsAccepted] = useState(false);
    const [error, setError] = useState("");
    const [validationErrors, setValidationErrors] = useState({}); // Track individual field errors
    const navigate = useNavigate();

    // Field validation logic
    const validateFields = () => {
        const errors = {};

        if (!email) {
            errors.email = "Email is required.";
        } else if (!/\S+@\S+\.\S+/.test(email)) {
            errors.email = "Please provide a valid email address.";
        }

        if (!password) {
            errors.password = "Password is required.";
        } else {
            if (password.length < 8) {
                errors.password = "Password must be at least 8 characters long.";
            }
            if (!/[A-Z]/.test(password)) {
                errors.password = "Password must contain at least one uppercase letter.";
            }
            if (!/[a-z]/.test(password)) {
                errors.password = "Password must contain at least one lowercase letter.";
            }
            if (!/\d/.test(password)) {
                errors.password = "Password must contain at least one digit.";
            }
        }

        if (!confirmPassword) {
            errors.confirmPassword = "Please confirm your password.";
        } else if (password !== confirmPassword) {
            errors.confirmPassword = "Passwords do not match.";
        }

        if (!firstName) {
            errors.firstName = "First Name is required.";
        }

        if (!lastName) {
            errors.lastName = "Last Name is required.";
        }

        if (!ageConfirmed) {
            errors.ageConfirmed = "You must confirm your age to sign up.";
        }

        if (!termsAccepted) {
            errors.termsAccepted = "You must agree to the terms and conditions.";
        }

        setValidationErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const handleSignUp = async (e) => {
        e.preventDefault();
        setError("");

        if (!validateFields()) {
            return; // Abort if validation fails
        }

        try {
            await api.post("/users/register", {
                username: email,
                password,
                firstName,
                lastName,
                role,
            });
            navigate("/login"); // Redirect to login after successful sign-up
        } catch (err) {
            console.error("Signup failed, error:", err); // Log error details
            if (err.response) {
                console.error("Server responded with:", err.response.status, err.response.data);
            }
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
                        />
                        {validationErrors.email && (
                            <p className="text-red-500 text-sm">{validationErrors.email}</p>
                        )}
                    </div>
                    <div className="mb-4">
                        <label htmlFor="password" className="block text-gray-700">Password</label>
                        <input
                            type="password"
                            id="password"
                            className="w-full px-3 py-2 border rounded"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                        {validationErrors.password && (
                            <p className="text-red-500 text-sm">{validationErrors.password}</p>
                        )}
                    </div>
                    <div className="mb-4">
                        <label htmlFor="confirmPassword" className="block text-gray-700">Confirm Password</label>
                        <input
                            type="password"
                            id="confirmPassword"
                            className="w-full px-3 py-2 border rounded"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                        />
                        {validationErrors.confirmPassword && (
                            <p className="text-red-500 text-sm">{validationErrors.confirmPassword}</p>
                        )}
                    </div>
                    <div className="mb-4">
                        <label htmlFor="firstName" className="block text-gray-700">First Name</label>
                        <input
                            type="text"
                            id="firstName"
                            className="w-full px-3 py-2 border rounded"
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                        />
                        {validationErrors.firstName && (
                            <p className="text-red-500 text-sm">{validationErrors.firstName}</p>
                        )}
                    </div>
                    <div className="mb-4">
                        <label htmlFor="lastName" className="block text-gray-700">Last Name</label>
                        <input
                            type="text"
                            id="lastName"
                            className="w-full px-3 py-2 border rounded"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                        />
                        {validationErrors.lastName && (
                            <p className="text-red-500 text-sm">{validationErrors.lastName}</p>
                        )}
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
                        {validationErrors.ageConfirmed && (
                            <p className="text-red-500 text-sm">{validationErrors.ageConfirmed}</p>
                        )}
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
                        {validationErrors.termsAccepted && (
                            <p className="text-red-500 text-sm">{validationErrors.termsAccepted}</p>
                        )}
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
