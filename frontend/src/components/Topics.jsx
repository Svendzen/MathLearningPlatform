import { Link, useParams } from "react-router-dom";

export const validTopics = ["ADDITION", "SUBTRACTION", "MULTIPLICATION", "DIVISION"];

function Topics() {
    const { module } = useParams(); // Capture the module from the URL params

    return (
        <div className="p-4">
            <h1 className="text-xl font-bold">Select a Topic for {module.replace(/-/g, " ")}</h1>
            <ul className="mt-4 space-y-2">
                {validTopics.map((topic) => (
                    <li key={topic}>
                        <Link
                            to={`/game-modes/${topic}`} // Navigate to GameModes for the selected topic
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
