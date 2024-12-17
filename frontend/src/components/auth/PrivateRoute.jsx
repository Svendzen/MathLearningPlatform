import { Navigate } from "react-router-dom";
import PropTypes from "prop-types";
import { useEffect, useState } from "react";
import api from "../../api.js";

/**
 * PrivateRoute ensures only authenticated users can access certain routes.
 * Redirects to the login page if no valid token is found in localStorage.
 * Dynamically routes users to the correct dashboard based on their role.
 *
 * @param {object} children - The child components to render if authenticated.
 */
function PrivateRoute({ children }) {
    const [role, setRole] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");

    useEffect(() => {
        if (!token || !userId) {
            setError(true);
            setLoading(false);
            return;
        }

        // Fetch user info to get the role
        const authHeaders = {
            headers: { Authorization: `Bearer ${token}` },
        };

        api.get(`/users/${userId}`, authHeaders)
            .then((response) => {
                setRole(response.role); // Assume role is returned in the response
                setLoading(false);
            })
            .catch((err) => {
                console.error("Error fetching user role:", err);
                setError(true);
                setLoading(false);
            });
    }, [token, userId]);

    if (!token || error) {
        return <Navigate to="/login" replace />;
    }

    if (loading) return <p>Loading...</p>;

    // Role-based redirection
    if (!children) {
        if (role === "TEACHER") return <Navigate to="/teacher-dashboard" replace />;
        if (role === "PARENT") return <Navigate to="/parent-dashboard" replace />;
        return <Navigate to="/student-dashboard" replace />;
    }

    return children;
}

PrivateRoute.propTypes = {
    children: PropTypes.node,
};

export default PrivateRoute;
