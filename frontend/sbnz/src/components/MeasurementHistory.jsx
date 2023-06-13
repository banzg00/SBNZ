function MeasurementHistory(props) {
  let mes = [...props.measurements].reverse();

  return (
    <div>
      <ul className="flex flex-col p-4 overflow-y-scroll max-h-[400px]">
        {mes.map((measurement) => (
          <li
            className="border-gray-400 flex flex-row mb-2"
            key={measurement.id}
          >
            <div className="select-none cursor-pointer bg-gray-200 rounded-md flex flex-1 items-center p-4  transition duration-500 ease-in-out transform hover:-translate-y-1 hover:shadow-lg">
              <div className="flex w-full">
                <div className="flex-1 pl-1 mr-16">
                  <div className="font-medium">Water Speed</div>
                  <div className="text-gray-600 text-sm">
                    {measurement.waterSpeed} m/s
                  </div>
                </div>
                <div className="flex-1 pl-1 mr-16">
                  <div className="font-medium">Water Level</div>
                  <div className="text-gray-600 text-sm">
                    {measurement.waterLvl}m
                  </div>
                </div>
                <div className="flex-1 pl-1 mr-16">
                  <div className="font-medium">Wind Speed</div>
                  <div className="text-gray-600 text-sm">
                    {measurement.windSpeed} m/s
                  </div>
                </div>
                <div className="flex-1 pl-1 mr-16">
                  <div className="font-medium">Water Temperature</div>
                  <div className="text-gray-600 text-sm">
                    {measurement.waterTemp} C
                  </div>
                </div>
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default MeasurementHistory;
