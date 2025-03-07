import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import Home from "./pages/Home";
import Dashboard from "./pages/Dashboard";

function App() {
    return (
        <Router>
            <div className="container">
                <h1>Smart Home Energy Monitoring</h1>
                <nav>
                    <Link to="/">Home</Link>
                    <Link to="/dashboard">Dashboard</Link>
                </nav>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/dashboard" element={<Dashboard />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;









