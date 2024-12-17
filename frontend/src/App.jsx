import { Routes, Route } from "react-router-dom";
import Dashboard from "./components/Dashboard";
import Modules from "./components/Modules";
import Topics from "./components/Topics";
import GameModes from "./components/GameModes";
import Exercise from "./components/Exercise";
import ResultScreen from "./components/ResultScreen.jsx";
import StatsPage from "./components/StatsPage.jsx";
import AchievementsPage from "./components/AchievementsPage.jsx";
import ProfilePage from "./components/ProfilePage.jsx";
import LoginPage from "./components/auth/LoginPage.jsx";
import SignupPage from "./components/auth/SignupPage.jsx";
import PrivateRoute from "./components/auth/PrivateRoute";
import RedirectIfAuthenticated from "./components/auth/RedirectIfAuthenticated.jsx";

function App() {
    return (
        <div className="min-h-screen bg-primary-50 text-gray-900">
            <Routes>
                {/* Public routes with redirect for authenticated users */}
                <Route
                    path="/login"
                    element={
                        <RedirectIfAuthenticated>
                            <LoginPage />
                        </RedirectIfAuthenticated>
                    }
                />
                <Route
                    path="/signup"
                    element={
                        <RedirectIfAuthenticated>
                            <SignupPage />
                        </RedirectIfAuthenticated>
                    }
                />

                {/* Private routes */}
                <Route
                    path="/"
                    element={
                        <PrivateRoute>
                            <Dashboard />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/modules"
                    element={
                        <PrivateRoute>
                            <Modules />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/topics/:module"
                    element={
                        <PrivateRoute>
                            <Topics />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/game-modes/:topic"
                    element={
                        <PrivateRoute>
                            <GameModes />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/exercise/:gameMode"
                    element={
                        <PrivateRoute>
                            <Exercise />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/result"
                    element={
                        <PrivateRoute>
                            <ResultScreen />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/stats"
                    element={
                        <PrivateRoute>
                            <StatsPage />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/achievements"
                    element={
                        <PrivateRoute>
                            <AchievementsPage />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/profile"
                    element={
                        <PrivateRoute>
                            <ProfilePage />
                        </PrivateRoute>
                    }
                />
            </Routes>
        </div>
    );
}

export default App;
