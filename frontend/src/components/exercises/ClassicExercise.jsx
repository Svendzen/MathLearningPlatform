import { useState, useRef, useEffect, useCallback } from "react";
import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";
import api from "../../api.js";

function ClassicExercise({ exercise }) {
    const [currentIndex, setCurrentIndex] = useState(0); // Tracks the current problem index
    const [answers, setAnswers] = useState([]); // Stores user's answers and scores
    const [timeLeft, setTimeLeft] = useState(exercise.millisecondsPerQuestion); // Timer in milliseconds
    const [feedback, setFeedback] = useState(null); // Feedback message for the user
    const [score, setScore] = useState(0); // Accumulated score across problems
    const [questionSubmitted, setQuestionSubmitted] = useState(false); // Prevent double submissions
    const [navigateNow, setNavigateNow] = useState(false); // New state to trigger navigation
    const [result, setResult] = useState(null); // Store result for navigation

    const inputRef = useRef(null); // Ref for the input field
    const timerRef = useRef(null); // Ref for the timer interval
    const navigate = useNavigate();

    // Total time for each question, derived from the GameMode settings
    const totalMilliseconds = exercise.millisecondsPerQuestion;
    const currentProblem = exercise.problems[currentIndex]; // Current math problem
    const [startTime] = useState(Date.now()); // Tracks the exercise start time

    // Navigation trigger after rendering
    useEffect(() => {
        if (navigateNow && result) {
            navigate("/result", { state: { result } });
        }
    }, [navigateNow, result, navigate]);

    const handleAnswerSubmit = useCallback(
        (answer) => {
            if (feedback || questionSubmitted) return; // Prevent user actions while feedback is being shown or already submitted

            setQuestionSubmitted(true); // Mark question as submitted

            // Calculate remaining time in milliseconds
            const elapsed = totalMilliseconds - timeLeft;
            const remainingMs = Math.max(totalMilliseconds - elapsed, 0);

            // Check if the user's answer is correct and calculate score
            const isCorrect = answer === currentProblem.answer;
            const earnedScore = isCorrect
                ? Math.floor(
                    (exercise.maxPointsPerQuestion * remainingMs) /
                    totalMilliseconds
                )
                : 0;

            setScore((prev) => prev + earnedScore); // Update total score

            // Feedback message for the user based on their input
            const answerFeedback =
                answer === null
                    ? "Time's up! Moving to the next question."
                    : isCorrect
                        ? `Correct! +${earnedScore} points.`
                        : "Incorrect. Try the next one!";

            // Stop the timer while feedback is displayed
            clearInterval(timerRef.current);

            // Show feedback briefly before proceeding to the next question
            setFeedback(answerFeedback);

            setTimeout(() => {
                setAnswers((prevAnswers) => {
                    const updatedAnswers = [
                        ...prevAnswers,
                        {
                            problemId: currentProblem.id,
                            answer,
                            earnedScore,
                        },
                    ];

                    if (currentIndex < exercise.problems.length - 1) {
                        setCurrentIndex((prevIndex) => prevIndex + 1); // Move to the next problem
                    } else {
                        // If this was the last question, calculate final stats and complete the exercise
                        const completionTime = Date.now() - startTime; // Total elapsed time in milliseconds

                        const result = {
                            studentId: localStorage.getItem("userId"), // Mocked value; replace with actual student ID
                            mathTopic: exercise.problems[0].type,
                            gameMode: exercise.name,
                            totalQuestions: exercise.problems.length,
                            correctAnswers: updatedAnswers.filter(
                                (a) =>
                                    Number(a.answer) ===
                                    Number(
                                        exercise.problems.find(
                                            (p) => p.id === a.problemId
                                        ).answer
                                    )
                            ).length,
                            score: score + earnedScore, // Add current score to total
                            scorePercentage: Math.round(
                                ((score + earnedScore) /
                                    (exercise.maxPointsPerQuestion *
                                        exercise.problems.length)) *
                                100
                            ),
                            completionTime: Math.floor(completionTime / 1000), // Convert to seconds
                            completionDate: new Date().toISOString(),
                        };

                        // Send result to the backend
                        api.post("/content/gamemode/complete", result)
                            .then(() => console.log("Result sent to backend"))
                            .catch((err) =>
                                console.error("Error sending result:", err)
                            );

                        // Navigate to the ResultScreen with the exercise results
                        navigate("/result", { state: { result } });
                    }

                    return updatedAnswers; // Return updated answers state
                });

                setFeedback(null); // Clear feedback for the next question
                setQuestionSubmitted(false); // Reset submission status
            }, 1500); // Duration of feedback display in milliseconds
        },
        [
            feedback,
            questionSubmitted,
            totalMilliseconds,
            timeLeft,
            currentProblem,
            currentIndex,
            exercise,
            score,
            navigate,
            startTime,
        ]
    );


    useEffect(() => {
        // Starts the timer if no feedback is being shown
        if (!feedback) {
            const startTime = Date.now();
            timerRef.current = setInterval(() => {
                const elapsed = Date.now() - startTime;
                const remaining = Math.max(totalMilliseconds - elapsed, 0);
                setTimeLeft(remaining); // Update time left in milliseconds
                if (remaining === 0) clearInterval(timerRef.current); // Stop timer if no time remains
            }, 100); // Frequent updates ensure smoother countdown
        }

        // Clears the interval when the timer stops or feedback is shown
        return () => clearInterval(timerRef.current);
    }, [feedback, totalMilliseconds]);

    useEffect(() => {
        // Automatically handles timeout when the timer reaches zero
        if (timeLeft === 0 && !feedback) {
            handleAnswerSubmit(null); // Timeout submits a `null` answer
        }
    }, [timeLeft, feedback, handleAnswerSubmit]);

    useEffect(() => {
        // Resets the timer and clears the input field for the next question
        setTimeLeft(exercise.millisecondsPerQuestion); // Reset time for the new question
        if (inputRef.current) inputRef.current.value = ""; // Clear previous input
        inputRef.current?.focus(); // Refocus input field for better UX
    }, [currentIndex, exercise.millisecondsPerQuestion]);

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
                        feedback.includes("Correct")
                            ? "text-green-500"
                            : "text-red-500"
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
                disabled={!!feedback || questionSubmitted} // Disable input during feedback or processing
                onKeyDown={(e) => {
                    if (e.key === "Enter") {
                        handleAnswerSubmit(Number(e.target.value));
                    }
                }}
            />
            <button
                onClick={() =>
                    handleAnswerSubmit(Number(inputRef.current.value))
                }
                className="bg-blue-500 text-white px-4 py-2 mt-2 rounded"
                disabled={!!feedback || questionSubmitted} // Disable button during feedback or processing
            >
                Submit
            </button>
        </div>
    );
}

// PropTypes validation
ClassicExercise.propTypes = {
    exercise: PropTypes.shape({
        name: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired,
        millisecondsPerQuestion: PropTypes.number.isRequired,
        maxPointsPerQuestion: PropTypes.number.isRequired,
        problems: PropTypes.arrayOf(
            PropTypes.shape({
                id: PropTypes.number.isRequired,
                question: PropTypes.string.isRequired,
                answer: PropTypes.oneOfType([
                    PropTypes.string,
                    PropTypes.number,
                ]).isRequired,
                type: PropTypes.string.isRequired,
            })
        ).isRequired,
    }).isRequired,
};

export default ClassicExercise;
