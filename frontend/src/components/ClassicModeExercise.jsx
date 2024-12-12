import { useState, useRef, useEffect } from "react";
import PropTypes from "prop-types";

function ClassicModeExercise({ exercise, onComplete }) {
    const [currentIndex, setCurrentIndex] = useState(0); // Current problem index
    const [answers, setAnswers] = useState([]); // User's answers
    const [timeLeft, setTimeLeft] = useState(exercise.millisecondsPerQuestion); // Timer in milliseconds
    const [feedback, setFeedback] = useState(null); // Feedback for answer
    const [score, setScore] = useState(0); // Accumulated score
    const inputRef = useRef(null); // Ref for the input field
    const timerRef = useRef(null); // Ref to hold the timer interval

    const currentProblem = exercise.problems[currentIndex];

    useEffect(() => {
        // Start the timer if there's no feedback
        if (!feedback) {
            const startTime = Date.now();
            timerRef.current = setInterval(() => {
                const elapsed = Date.now() - startTime;
                const remaining = Math.max(exercise.millisecondsPerQuestion - elapsed, 0);
                setTimeLeft(remaining); // Update timer
                if (remaining === 0) clearInterval(timerRef.current);
            }, 50); // Frequent updates for smoother timing
        }

        // Clear the interval when the timer stops or feedback is shown
        return () => clearInterval(timerRef.current);
    }, [feedback, exercise.millisecondsPerQuestion]);

    useEffect(() => {
        // Handle timeout if the timer reaches zero
        if (timeLeft === 0 && !feedback) {
            handleAnswerSubmit(null); // Timeout submits a null answer
        }
    }, [timeLeft, feedback]);

    useEffect(() => {
        // Reset the timer and input field when a new question starts
        setTimeLeft(exercise.millisecondsPerQuestion);
        if (inputRef.current) inputRef.current.value = ""; // Clear input field
        inputRef.current?.focus(); // Keep focus on the input field
    }, [currentIndex, exercise.millisecondsPerQuestion]);

    const handleAnswerSubmit = (answer) => {
        if (feedback) return; // Prevent actions while feedback is shown

        // Calculate remaining time in ms
        const remainingMs = Math.max(timeLeft, 0);

        // Determine if the answer is correct and calculate score
        const isCorrect = answer === currentProblem.answer;
        const earnedScore = isCorrect
            ? Math.floor(
                (exercise.maxPointsPerQuestion * remainingMs) /
                exercise.millisecondsPerQuestion
            )
            : 0;

        setScore((prev) => prev + earnedScore); // Update accumulated score

        const answerFeedback = answer === null
            ? "Time's up! Moving to the next question."
            : isCorrect
                ? `Correct! +${earnedScore} points.`
                : "Incorrect. Try the next one!";

        // Stop the timer while feedback is displayed
        clearInterval(timerRef.current);

        // Show feedback briefly before proceeding
        setFeedback(answerFeedback);

        setTimeout(() => {
            setAnswers([...answers, { problemId: currentProblem.id, answer, earnedScore }]);

            if (currentIndex < exercise.problems.length - 1) {
                setCurrentIndex(currentIndex + 1);
            } else {
                onComplete({ answers, totalScore: score + earnedScore });
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
            <p className="text-red-500 font-medium">
                Time left: {(timeLeft / 1000).toFixed(1)}s
            </p>
            <p className="text-green-500 font-medium">Score: {score}</p>

            {feedback && (
                <p
                    className={`text-lg font-medium ${
                        feedback.includes("Correct") ? "text-green-500" : "text-red-500"
                    }`}
                >
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
        millisecondsPerQuestion: PropTypes.number.isRequired, // Updated to match schema
        maxPointsPerQuestion: PropTypes.number.isRequired, // Updated to match schema
        problems: PropTypes.arrayOf(
            PropTypes.shape({
                id: PropTypes.number.isRequired,
                question: PropTypes.string.isRequired,
                answer: PropTypes.oneOfType([PropTypes.string, PropTypes.number])
                    .isRequired,
            })
        ).isRequired,
    }).isRequired,
    onComplete: PropTypes.func.isRequired,
};

export default ClassicModeExercise;
