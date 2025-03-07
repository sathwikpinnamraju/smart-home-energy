import React, { useEffect, useState } from "react";
import { BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid, ResponsiveContainer } from "recharts";
import axios from "axios";

const Dashboard = () => {
  const [energyData, setEnergyData] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/energy-usage") // Ensure backend is running
      .then((response) => {
        console.log("Energy Data Response:", response.data); // Debug API response
        if (response.data.length > 0) {
          setEnergyData(response.data);
        }
      })
      .catch((error) => console.error("Error fetching energy usage data:", error));
  }, []);

  return (
    <div className="container">
      <h1>Smart Home Energy Monitoring</h1>
      <div className="data-section">
        <h2>Energy Usage Data</h2>

        {energyData.length > 0 ? (
          <>
            {/* Table View */}
            <table className="data-table">
              <thead>
                <tr>
                  <th>Device ID</th>
                  <th>Device Name</th>
                  <th>Energy Used (kWh)</th>
                </tr>
              </thead>
              <tbody>
                {energyData.map((device) => (
                  <tr key={device.device_id}>
                    <td>{device.device_id}</td>
                    <td>{device.device_name}</td>
                    <td>{device.energyConsumedKwh}</td> {/* Correct Key */}
                  </tr>
                ))}
              </tbody>
            </table>

            {/* Graph View */}
            <div className="chart-container">
              <h3>Energy Usage by Device</h3>
              <ResponsiveContainer width="80%" height={400}>
                <BarChart data={energyData} margin={{ top: 20, right: 30, left: 20, bottom: 10 }}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="device_name" />
                  <YAxis />
                  <Tooltip />
                  <Bar dataKey="energyConsumedKwh" fill="#4CAF50" /> {/* Correct Key */}
                </BarChart>
              </ResponsiveContainer>
            </div>
          </>
        ) : (
          <p>No energy usage data available.</p>
        )}
      </div>
    </div>
  );
};

export default Dashboard;



