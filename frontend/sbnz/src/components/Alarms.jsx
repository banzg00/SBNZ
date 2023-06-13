import React, { useState } from "react";
function Alarms(props) {
  function alarmColor(severity) {
    switch (severity) {
      case "high":
        return "bg-red-500";
      case "medium":
        return "bg-yellow-500";
      case "low":
        return "bg-green-500";
      default:
        return "bg-gray-200";
    }
  }
  return (
    <div>
      <h2 className="text-xl font-bold mb-4 mx-auto w-full text-center">
        Alarms
      </h2>
      <ul className="flex flex-col p-4 overflow-y-scroll max-h-[400px] w-[800px] mx-auto">
        {props.alarms.map((alarm) => (
          <li className="border-gray-400 flex flex-row mb-2" key={alarm.id}>
            <div
              className={
                "select-none rounded-md flex flex-1 items-center p-4  transition duration-500 ease-in-out transform hover:-translate-y-1 hover:shadow-lg " +
                alarmColor(alarm.severity)
              }
            >
              <div className="flex w-full">
                <div className="flex-1 pl-1 mr-16">
                  <div className="font-medium">{alarm.description}</div>
                </div>
                <div className="dmr-10">
                  <div className="font-medium">Time: {alarm.time}</div>
                </div>
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Alarms;
