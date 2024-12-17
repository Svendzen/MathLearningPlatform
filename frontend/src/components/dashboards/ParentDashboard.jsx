import LogoutButton from "../LogoutButton.jsx";
import {Link} from "react-router-dom";

function ParentDashboard() {
    return (
        <div>
            <h1 className="text-2xl font-bold">Welcome to the Parent Dashboard</h1>
            <p>Monitor your child&#39;s progress!</p>
            <Link
                to="/profile" className="inline-block bg-violet-500 hover:bg-violet-600 text-white px-4 py-2 rounded transition duration-300">
                View Profile
            </Link>
            <LogoutButton/>
        </div>
    );
}
export default ParentDashboard;
