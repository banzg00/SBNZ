import React, { useState } from "react";
import NavBar from "../components/NavBar";

const Home = () => {
  const waterFlow = 150; // Placeholder value
  const waterPressure = 80; // Placeholder value
  const waterTemperature = 25; // Placeholder value
  const weather = "Sunny"; // Placeholder value
  const electricityGenerated = 1200; // Placeholder value

  const alarms = [
    { id: 1, description: "Low water pressure" },
    { id: 2, description: "High temperature" },
    { id: 3, description: "Power outage" },
  ];
  return (
    <div>
      <NavBar isAuth={true} />
      <div className="container mx-auto mt-8">
        <div className="grid grid-cols-2 gap-8">
          <div className="bg-white p-4">
            <h2 className="text-xl font-bold mb-4">Stats</h2>
            <div>
              <p>Water Flow: {waterFlow} L/min</p>
              <p>Water Pressure: {waterPressure} psi</p>
              <p>Water Temperature: {waterTemperature} &#8451;</p>
              <p>Weather: {weather}</p>
              <p>Electricity Generated: {electricityGenerated} kW</p>
            </div>
          </div>
          <div className="bg-white p-4">
            <h2 className="text-xl font-bold mb-4">Alarms</h2>
            <ul>
              {alarms.map((alarm) => (
                <li key={alarm.id}>{alarm.description}</li>
              ))}
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
