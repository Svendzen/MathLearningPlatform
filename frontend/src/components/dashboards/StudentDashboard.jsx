import { Link } from "react-router-dom";
import LogoutButton from "../LogoutButton.jsx";

function StudentDashboard() {
    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold text-primary-700">Welcome to Math Learning!</h1>
            <p className="mt-2 text-gray-700">Improve your math skills with interactive exercises.</p>

            <div className="mt-4 space-y-2">
                <Link to="/modules" className="inline-block bg-primary-500 hover:bg-primary-700 text-white px-4 py-2 rounded">
                    Start Learning
                </Link>
                <Link
                    to="/profile" className="inline-block bg-violet-500 hover:bg-violet-600 text-white px-4 py-2 rounded transition duration-300">
                    View Profile
                </Link>
                <Link to="/stats" className="inline-block bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded">
                    Check Stats
                </Link>
                <Link to="/achievements" className="inline-block bg-yellow-500 hover:bg-yellow-600 text-white px-4 py-2 rounded">
                    View Achievements
                </Link>
                <LogoutButton />
            </div>
        </div>
    );
}

export default StudentDashboard;
