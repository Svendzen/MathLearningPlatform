import { Navigate } from "react-router-dom";
import PropTypes from "prop-types";

/**
 * PrivateRoute ensures only authenticated users can access certain routes.
 * Redirects to the login page if no valid token is found in localStorage.
 *
 * @param {object} children - The child components to render if authenticated.
 */
function PrivateRoute({ children }) {
    const token = localStorage.getItem("token");

    if (!token) {
        return <Navigate to="/login" replace />;
    }

    return children;
}

PrivateRoute.propTypes = {
    children: PropTypes.node.isRequired,
};


export default PrivateRoute;