import { Link } from "react-router-dom";

function Dashboard() {
    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold text-primary-700">Welcome to Math Learning!</h1>
            <p className="mt-2 text-gray-700">Improve your math skills with interactive exercises.</p>
            <Link to="/modules" className="mt-4 inline-block bg-primary-500 text-white px-4 py-2 rounded">
                Start Learning
            </Link>
        </div>
    );
}

export default Dashboard;
