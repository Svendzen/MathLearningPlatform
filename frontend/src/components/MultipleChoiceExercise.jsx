import { useState, useEffect } from "react";
import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";
import api from "../api.js";

function MultipleChoiceExercise({ exercise }) {
    const [currentIndex, setCurrentIndex] = useState(0); // Tracks the current problem index
    const [answers, setAnswers] = useState([]); // Stores user's answers and scores
    const [score, setScore] = useState(0); // Accumulated score across problems
    const [feedback, setFeedback] = useState(null); // Feedback message
    const [timeElapsed, setTimeElapsed] = useState(0); // Total time spent
    const [options, setOptions] = useState([]); // Generated options for the current question
    const [result, setResult] = useState(null); // Stores the final result
    const [navigateNow, setNavigateNow] = useState(false); // Trigger navigation

    const navigate = useNavigate();
    const currentProblem = exercise.problems[currentIndex];

    // Timer logic to track completion time
    useEffect(() => {
        const timer = setInterval(() => {
            setTimeElapsed((prev) => prev + 1);
        }, 1000);

        return () => clearInterval(timer);
    }, []);

    // Generate options when currentProblem changes
    useEffect(() => {
        if (currentProblem) {
            setOptions(generateOptions(currentProblem.answer));
        }
    }, [currentProblem]);

    // Helper function: Generate options within +/- 10 of the correct answer
    function generateOptions(correctAnswer) {
        const optionsSet = new Set();
        optionsSet.add(correctAnswer);

        while (optionsSet.size < 4) {
            const randomOffset = Math.floor(Math.random() * 21) - 10; // Random value in [-10, 10]
            const option = Math.max(0, correctAnswer + randomOffset);
            if (option !== correctAnswer) optionsSet.add(option);
        }

        return Array.from(optionsSet).sort(() => Math.random() - 0.5);
    }

    const handleOptionSelect = (selectedOption) => {
        const isCorrect = selectedOption === currentProblem.answer;
        const earnedScore = isCorrect ? exercise.maxPointsPerQuestion : 0;

        setScore((prev) => prev + earnedScore);

        // Show feedback
        setFeedback(isCorrect ? `Correct! +${earnedScore} points` : "Incorrect. Try the next one!");

        setTimeout(() => {
            setAnswers((prevAnswers) => {
                const updatedAnswers = [
                    ...prevAnswers,
                    {
                        problemId: currentProblem.id,
                        selectedOption,
                        isCorrect,
                        earnedScore,
                    },
                ];

                // Check if this was the last question
                if (currentIndex >= exercise.problems.length - 1) {
                    const finalResult = {
                        studentId: localStorage.getItem("userId"),
                        mathTopic: exercise.problems[0].type,
                        gameMode: exercise.name,
                        totalQuestions: exercise.problems.length,
                        correctAnswers: updatedAnswers.filter((a) => a.isCorrect).length,
                        score: score + earnedScore,
                        scorePercentage: Math.round(
                            ((score + earnedScore) / (exercise.maxPointsPerQuestion * exercise.problems.length)) * 100
                        ),
                        completionTime: timeElapsed,
                        completionDate: new Date().toISOString(),
                    };

                    api.post("/content/gamemode/complete", finalResult)
                        .then(() => console.log("Result sent to backend"))
                        .catch((err) => console.error("Error sending result:", err));

                    setResult(finalResult);
                    setNavigateNow(true);
                } else {
                    // Proceed to the next question
                    setCurrentIndex((prev) => prev + 1);
                    setFeedback(null);
                }

                return updatedAnswers; // Return the updated answers state
            });
        }, 1500); // Show feedback for 1.5 seconds
    };



    // Navigation trigger after rendering
    useEffect(() => {
        if (navigateNow && result) {
            navigate("/result", { state: { result } });
        }
    }, [navigateNow, result, navigate]);

    return (
        <div className="mt-4">
            <p className="text-lg font-medium">
                Problem {currentIndex + 1} of {exercise.problems.length}:
            </p>
            <p className="text-2xl font-bold">{currentProblem.question}</p>
            <p className="text-green-500 font-medium">Score: {score}</p>
            <p className="text-gray-700 font-medium">Time Elapsed: {timeElapsed}s</p>

            {feedback && (
                <p className={`text-lg font-medium ${feedback.includes("Correct") ? "text-green-500" : "text-red-500"}`}>
                    {feedback}
                </p>
            )}

            {/* Display multiple choice options */}
            <div className="mt-4 grid grid-cols-2 gap-2">
                {options.map((option, index) => (
                    <button
                        key={index}
                        onClick={() => handleOptionSelect(option)}
                        className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded"
                        disabled={feedback !== null}
                    >
                        {option}
                    </button>
                ))}
            </div>
        </div>
    );
}

MultipleChoiceExercise.propTypes = {
    exercise: PropTypes.shape({
        name: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired,
        maxPointsPerQuestion: PropTypes.number.isRequired,
        problems: PropTypes.arrayOf(
            PropTypes.shape({
                id: PropTypes.number.isRequired,
                question: PropTypes.string.isRequired,
                answer: PropTypes.number.isRequired,
                type: PropTypes.string.isRequired,
            })
        ).isRequired,
    }).isRequired,
};

export default MultipleChoiceExercise;
