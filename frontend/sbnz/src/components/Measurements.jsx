import React, { useState } from "react";
import Measurement from "./Measurement";
import MeasurementHistory from "./MeasurementHistory";
import { FaWind, FaWater, FaTemperatureHigh, FaBolt } from "react-icons/fa";
import { TbEngine } from "react-icons/tb";

function Measurements(props) {
  const totalTurbines = 3;

  let [showMeasurements, setShowMeasurements] = useState(false);

  function currentMeasurement() {
    const current =
      props.measurements.length - 1 < 0 ? 0 : props.measurements.length - 1;
    return props.measurements[current];
  }

  function lastMeasurement() {
    const last =
      props.measurements.length - 2 < 0 ? 0 : props.measurements.length - 2;
    return props.measurements[last];
  }

  return (
    <div className="mx-auto">
      <div className="bg-white">
        <div className="flex">
          <Measurement
            lastMeasurement={lastMeasurement().waterSpeed}
            currentMeasurement={currentMeasurement().waterSpeed}
            title="Water speed"
            unit="m/s"
            icon={FaWater}
          />
          <Measurement
            lastMeasurement={lastMeasurement().waterLvl}
            currentMeasurement={currentMeasurement().waterLvl}
            title="Water level"
            unit="m"
            icon={FaWater}
          />
          <Measurement
            lastMeasurement={lastMeasurement().waterTemp}
            currentMeasurement={currentMeasurement().waterTemp}
            title="Water Temp"
            unit="C"
            icon={FaTemperatureHigh}
          />
        </div>
        <div className="flex">
          <Measurement
            lastMeasurement={lastMeasurement().windSpeed}
            currentMeasurement={currentMeasurement().windSpeed}
            title="Wind speed"
            unit="m/s"
            icon={FaWind}
          />
          <Measurement
            lastMeasurement={lastMeasurement().electricityGenerated}
            currentMeasurement={currentMeasurement().electricityGenerated}
            title="Electricity generated"
            unit="W/h"
            icon={FaBolt}
          />
          <div className="flex items-center p-4 bg-white rounded">
            <div className="flex flex-shrink-0 items-center justify-center h-20 w-20 rounded bg-green-200">
              <svg
                className="w-10 h-10 fill-current text-green-700"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 20 20"
                fill="currentColor"
              >
                <TbEngine />
              </svg>
            </div>
            <div className="flex-grow flex flex-col ml-4">
              <span className="text-xl font-bold">
                {props.turbines}/{totalTurbines}
              </span>
              <div className="flex items-center justify-between">
                <span className="text-gray-500">Turbines activated</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="mt-5">
        <div>
          <button
            className="justify-center ml-[500px] bg-gray-700 text-white p-3 rounded-xl"
            onClick={() => setShowMeasurements(!showMeasurements)}
          >
            {showMeasurements ? "Hide" : "Show"} measurements
          </button>
        </div>
        {showMeasurements ? (
          <MeasurementHistory measurements={props.measurements} />
        ) : (
          <div></div>
        )}
      </div>
    </div>
  );
}

export default Measurements;
