import { Link, useParams } from "react-router-dom";

export const validTopics = ["ADDITION", "SUBTRACTION", "MULTIPLICATION", "DIVISION"];

function Topics() {
    const { module } = useParams();

    return (
        <div className="p-4">
            {/* Breadcrumb navigation */}
            <nav className="mb-4">
                <Link to="/modules" className="text-blue-500 underline">Modules</Link> &gt;{" "}
                <span className="text-gray-700">Topics</span>
            </nav>

            <h1 className="text-xl font-bold">Select a Topic for {module.replace(/-/g, " ")}</h1>
            <ul className="mt-4 space-y-2">
                {validTopics.map((topic) => (
                    <li key={topic}>
                        <Link
                            to={`/game-modes/${topic}`}
                            className="block bg-primary-500 text-white px-4 py-2 rounded shadow hover:bg-primary-700"
                        >
                            {topic}
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Topics;
