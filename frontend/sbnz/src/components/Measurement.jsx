import React, { useState } from "react";

function Measurement(props) {
  return (
    <div className="flex items-center p-4 bg-white rounded w-96 text-xl">
      <div
        className={
          "flex flex-shrink-0 items-center justify-center h-20 w-20 rounded " +
          (props.currentMeasurement >= props.lastMeasurement
            ? "bg-green-200"
            : "bg-red-200")
        }
      >
        <svg
          className={
            "w-8 h-8 fill-current " +
            (props.currentMeasurement >= props.lastMeasurement
              ? "text-green-700"
              : "text-red-700")
          }
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 20 20"
          fill="currentColor"
        >
          <props.icon />
        </svg>
      </div>
      <div className="flex-grow flex flex-col ml-4">
        <span className="text-xl font-bold">
          {props.currentMeasurement} {props.unit}
        </span>
        <div class="flex items-center justify-between">
          <span className="text-gray-500">
            {props.title}{" "}
            {props.title !== "Electricity generated" ? "increase" : ""}
          </span>
          <span
            className={
              "text-lg font-semibold ml-2 " +
              (props.currentMeasurement >= props.lastMeasurement
                ? "text-green-500"
                : "text-red-500")
            }
          >
            {props.currentMeasurement - props.lastMeasurement > 0 ? "+" : ""}
            {(
              (props.currentMeasurement - props.lastMeasurement) /
              props.currentMeasurement
            ).toPrecision(2) * 100}{" "}
            %
          </span>
        </div>
      </div>
    </div>
  );
}

export default Measurement;
