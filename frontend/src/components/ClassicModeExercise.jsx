import { useState, useRef, useEffect } from "react";
import PropTypes from "prop-types";

function ClassicModeExercise({ exercise, onComplete }) {
    const [currentIndex, setCurrentIndex] = useState(0); // Current problem index
    const [answers, setAnswers] = useState([]); // User's answers
    const [timeLeft, setTimeLeft] = useState(exercise.secondsPerQuestion); // Timer
    const [feedback, setFeedback] = useState(null); // Feedback for answer
    const inputRef = useRef(null); // Ref for the input field
    const timerRef = useRef(null); // Ref to hold the timer interval

    const currentProblem = exercise.problems[currentIndex];

    useEffect(() => {
        // Start the timer if there's no feedback
        if (!feedback) {
            timerRef.current = setInterval(() => {
                setTimeLeft((prevTime) => Math.max(prevTime - 1, 0)); // Ensure timer stops at 0
            }, 1000);
        }

        // Clear the interval when the timer stops or feedback is shown
        return () => clearInterval(timerRef.current);
    }, [feedback]);

    useEffect(() => {
        // Handle timeout if the timer reaches zero
        if (timeLeft === 0 && !feedback) {
            handleAnswerSubmit(null); // Timeout submits a null answer
        }
    }, [timeLeft, feedback]);

    useEffect(() => {
        // Reset the timer and input field when a new question starts
        setTimeLeft(exercise.secondsPerQuestion);
        if (inputRef.current) inputRef.current.value = ""; // Clear input field
        inputRef.current?.focus(); // Keep focus on the input field
    }, [currentIndex]);

    const handleAnswerSubmit = (answer) => {
        if (feedback) return; // Prevent actions while feedback is shown

        const isCorrect = answer === currentProblem.answer;
        const answerFeedback = answer === null
            ? "Time's up! Moving to the next question."
            : isCorrect
                ? "Correct! Great job."
                : "Incorrect. Try the next one!";

        // Stop the timer while feedback is displayed
        clearInterval(timerRef.current);

        // Show feedback briefly before proceeding
        setFeedback(answerFeedback);

        setTimeout(() => {
            setAnswers([...answers, { problemId: currentProblem.id, answer }]);

            if (currentIndex < exercise.problems.length - 1) {
                setCurrentIndex(currentIndex + 1);
            } else {
                onComplete(answers);
            }

            setFeedback(null); // Clear feedback for the next question
        }, 1500); // Feedback duration
    };

    return (
        <div className="mt-4">
            <p className="text-lg font-medium">
                Problem {currentIndex + 1} of {exercise.problems.length}:
            </p>
            <p className="text-2xl font-bold">{currentProblem.question}</p>
            <p className="text-red-500 font-medium">Time left: {timeLeft}s</p>

            {feedback && (
                <p className={`text-lg font-medium ${feedback.includes("Correct") ? "text-green-500" : "text-red-500"}`}>
                    {feedback}
                </p>
            )}

            <input
                ref={inputRef}
                type="number"
                placeholder="Your answer"
                className="border p-2 mt-2"
                disabled={!!feedback} // Disable input during feedback
                onKeyDown={(e) => {
                    if (e.key === "Enter") {
                        handleAnswerSubmit(Number(e.target.value));
                    }
                }}
            />
            <button
                onClick={() => handleAnswerSubmit(Number(inputRef.current.value))}
                className="bg-blue-500 text-white px-4 py-2 mt-2 rounded"
                disabled={!!feedback} // Disable button during feedback
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
        secondsPerQuestion: PropTypes.number.isRequired,
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
