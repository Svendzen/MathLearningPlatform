import { useState, useRef } from "react";
import PropTypes from "prop-types";

function ClassicModeExercise({ exercise, onComplete }) {
    const [currentIndex, setCurrentIndex] = useState(0); // Tracks the current problem index
    const [answers, setAnswers] = useState([]); // Tracks user's answers
    const [feedback, setFeedback] = useState(null); // Tracks feedback for the current answer
    const inputRef = useRef(null); // Ref for the input field

    const currentProblem = exercise.problems[currentIndex];

    const handleAnswerSubmit = (answer) => {
        if (answer === null || answer === undefined) return; // Prevent empty answers

        const isCorrect = answer === currentProblem.answer;
        setFeedback({
            isCorrect,
            message: isCorrect
                ? "Correct!"
                : `Incorrect. The correct answer is ${currentProblem.answer}.`,
        });

        setAnswers([...answers, { problemId: currentProblem.id, answer }]);

        // Delay before moving to the next question
        setTimeout(() => {
            setFeedback(null); // Clear feedback

            if (currentIndex < exercise.problems.length - 1) {
                setCurrentIndex(currentIndex + 1);
                inputRef.current.value = ""; // Clear the input field
                inputRef.current.focus(); // Keep focus on the input field
            } else {
                onComplete(answers); // Call onComplete when the exercise ends
            }
        }, 2000); // 2-second delay for feedback display
    };

    return (
        <div className="mt-4">
            <p className="text-lg font-medium">
                Problem {currentIndex + 1} of {exercise.problems.length}:
            </p>
            <p className="text-2xl font-bold">{currentProblem.question}</p>

            <input
                ref={inputRef} // Attach ref to the input field
                type="number"
                placeholder="Your answer"
                className="border p-2 mt-2"
                disabled={feedback !== null} // Disable input while feedback is shown
                onKeyDown={(e) => {
                    if (e.key === "Enter") {
                        handleAnswerSubmit(Number(e.target.value));
                    }
                }}
            />
            <button
                onClick={() => handleAnswerSubmit(Number(inputRef.current.value))}
                className="bg-blue-500 text-white px-4 py-2 mt-2 rounded"
                disabled={feedback !== null} // Disable button while feedback is shown
            >
                Submit
            </button>

            {/* Feedback Section */}
            {feedback && (
                <div
                    className={`mt-4 text-lg font-bold ${
                        feedback.isCorrect ? "text-green-500" : "text-red-500"
                    }`}
                >
                    {feedback.message}
                </div>
            )}
        </div>
    );
}

// PropTypes validation
ClassicModeExercise.propTypes = {
    exercise: PropTypes.shape({
        name: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired,
        problems: PropTypes.arrayOf(
            PropTypes.shape({
                id: PropTypes.number.isRequired,
                question: PropTypes.string.isRequired,
                answer: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
            })
        ).isRequired,
    }).isRequired,
    onComplete: PropTypes.func.isRequired,
};

export default ClassicModeExercise;
