import React from "react";
import { Link } from "react-router-dom";

function Home() {
  return (
    <div>
      <h2>Home Page</h2>
      <p>Welcome to the Smart Home Energy Monitoring System.</p>
      <nav>
        <ul>
          <li><Link to="/dashboard">Go to Dashboard</Link></li>
        </ul>
      </nav>
    </div>
  );
}

export default Home;

