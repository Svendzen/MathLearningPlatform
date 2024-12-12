import PropTypes from "prop-types";
import {Link, useLocation, useNavigate} from "react-router-dom";

function ResultScreen() {
    const navigate = useNavigate();
    const location = useLocation();
    const { result } = location.state || {}; // Safely access result

    if (!result) {
        return <p>Result not available. Please complete an exercise first.</p>;
    }

    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold">Exercise Completed!</h1>
            <p className="mt-2">Game Mode: {result.gameMode}</p>
            <p className="mt-2">Math Topic: {result.mathTopic}</p>
            <p className="mt-2">Total Questions: {result.totalQuestions}</p>
            <p className="mt-2">Correct Answers: {result.correctAnswers}</p>
            <p className="mt-2">Score: {result.score}</p>
            <p className="mt-2">Score Percentage: {result.scorePercentage}%</p>
            <p className="mt-2">Completion Time: {result.completionTime}s</p>

            <div className="mt-4 space-y-2">
                <button
                    onClick={() => navigate(0)} // Reload the page to retry
                    className="bg-blue-500 text-white px-4 py-2 rounded block"
                >
                    Retry
                </button>
                <Link
                    to="/modules"
                    className="bg-gray-500 text-white px-4 py-2 rounded block text-center"
                >
                    Back to Modules
                </Link>
                <Link
                    to="/"
                    className="bg-green-500 text-white px-4 py-2 rounded block text-center"
                >
                    Dashboard
                </Link>
            </div>
        </div>
    );
}

ResultScreen.propTypes = {
    result: PropTypes.shape({
        gameMode: PropTypes.string.isRequired,
        mathTopic: PropTypes.string.isRequired,
        totalQuestions: PropTypes.number.isRequired,
        correctAnswers: PropTypes.number.isRequired,
        score: PropTypes.number.isRequired,
        scorePercentage: PropTypes.number.isRequired,
        completionTime: PropTypes.number.isRequired,
    }).isRequired,
};

export default ResultScreen;
