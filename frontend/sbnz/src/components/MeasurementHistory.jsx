function MeasurementHistory(props) {
  return (
    <div>
      <ul class="flex flex-col p-4 overflow-y-scroll max-h-[400px]">
        {props.measurements.map((measurement) => (
          <li class="border-gray-400 flex flex-row mb-2" key={measurement.id}>
            <div class="select-none cursor-pointer bg-gray-200 rounded-md flex flex-1 items-center p-4  transition duration-500 ease-in-out transform hover:-translate-y-1 hover:shadow-lg">
              <div className="flex w-full">
                <div class="flex-1 pl-1 mr-16">
                  <div class="font-medium">Water Speed</div>
                  <div class="text-gray-600 text-sm">
                    {measurement.waterSpeed} m/s
                  </div>
                </div>
                <div class="flex-1 pl-1 mr-16">
                  <div class="font-medium">Water Level</div>
                  <div class="text-gray-600 text-sm">
                    {measurement.waterLevel}m
                  </div>
                </div>
                <div class="flex-1 pl-1 mr-16">
                  <div class="font-medium">Wind Speed</div>
                  <div class="text-gray-600 text-sm">
                    {measurement.windSpeed} m/s
                  </div>
                </div>
                <div class="flex-1 pl-1 mr-16">
                  <div class="font-medium">Water Temperature</div>
                  <div class="text-gray-600 text-sm">
                    {measurement.waterTemperature} C
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
