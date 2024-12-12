import { Link } from "react-router-dom";

const modules = [
    { name: "Grades 1-4", route: "grades-1-4" },
    { name: "Grades 5-7", route: "grades-5-7" },
    { name: "Grades 8-10", route: "grades-8-10" },
];

function Modules() {
    return (
        <div className="p-4">
            <h1 className="text-xl font-bold">Choose Your Module</h1>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mt-4">
                {modules.map((module) => (
                    <Link
                        to={`/topics/${module.route}`} // Navigates to Topics component for the module
                        key={module.route}
                        className="block bg-primary-500 text-white p-4 rounded shadow"
                    >
                        {module.name}
                    </Link>
                ))}
            </div>
        </div>
    );
}

export default Modules;
