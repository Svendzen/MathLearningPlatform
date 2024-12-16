import { Navigate } from "react-router-dom";
import PropTypes from "prop-types";

/**
 * RedirectIfAuthenticated ensures authenticated users are redirected to the dashboard
 * when trying to access login or signup pages.
 *
 * @param {object} children - The child components to render if not authenticated.
 */
function RedirectIfAuthenticated({ children }) {
    const token = localStorage.getItem("token");

    if (token) {
        return <Navigate to="/" replace />; // Redirect to dashboard
    }

    return children; // Render the original page if not authenticated
}

RedirectIfAuthenticated.propTypes = {
    children: PropTypes.node.isRequired,
};

export default RedirectIfAuthenticated;
