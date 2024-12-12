import { useState, useRef } from "react";
import PropTypes from "prop-types"; // Import PropTypes for validation

function ClassicModeExercise({ exercise, onComplete }) {
    const [currentIndex, setCurrentIndex] = useState(0); // Tracks the current problem index
    const [answers, setAnswers] = useState([]); // Tracks user's answers
    const inputRef = useRef(null); // Ref for the input field

    const currentProblem = exercise.problems[currentIndex];

    const handleAnswerSubmit = (answer) => {
        if (!answer && answer !== 0) return; // Prevent empty answers

        setAnswers([...answers, { problemId: currentProblem.id, answer }]);

        if (currentIndex < exercise.problems.length - 1) {
            setCurrentIndex(currentIndex + 1);
            inputRef.current.value = ""; // Clear the input field
            inputRef.current.focus(); // Keep focus on the input field
        } else {
            onComplete(answers);
        }
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
                onKeyDown={(e) => {
                    if (e.key === "Enter") {
                        handleAnswerSubmit(Number(e.target.value));
                    }
                }}
            />
            <button
                onClick={() => handleAnswerSubmit(Number(inputRef.current.value))}
                className="bg-blue-500 text-white px-4 py-2 mt-2 rounded"
            >
                Submit
            </button>
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
