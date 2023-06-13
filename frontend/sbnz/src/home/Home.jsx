import React, { useState, useEffect } from "react";
import NavBar from "../components/NavBar";
import Measurements from "../components/Measurements";
import Alarms from "../components/Alarms";
import PopupModal from "../components/PopupModal";
import { StompSessionProvider, useSubscription } from "react-stomp-hooks";
import alarmService from "../services/alarmService";
import measurementService from "../services/measurementService";

const SOCKET_URL = "http://localhost:8080/ws";

const Home = () => {
  const [modalOpen, setModalOpen] = useState(false);
  const [alarms, setAlarms] = useState([]);
  const [measurements, setMeasurements] = useState([
    {
      waterSpeed: 1,
      waterLvl: 1,
      waterTemp: 1,
      windSpeed: 1,
      electricityGenerated: 1,
    },
  ]);
  const [turbines, setTurbines] = useState([1]);

  useEffect(() => {
    alarmService.getAlarms().then((response) => {
      let i = 0;
      response.data.forEach((measurement) => {
        measurement.id = i++;
      });
      setAlarms(response.data);
    });
    measurementService.getMeasurements().then((response) => {
      let i = 0;
      response.data.forEach((measurement) => {
        measurement.id = i++;
      });
      setMeasurements(response.data);
    });
  }, []);

  useSubscription("/topic/alarm", (message) => onAlarm(message));
  useSubscription("/topic/measurement", (message) => onMeasurement(message));

  const onAlarm = (message) => {
    const messageData = JSON.parse(message.body);
    let alarm = {
      id: Math.floor(Math.random() * (1000000 - 50 + 1)) + 50,
      description: messageData.description,
      time: messageData.time.slice(0, 8),
      severity: messageData.severity,
    };
    setAlarms((alarms) => [...alarms, alarm]);
    if (messageData.severity === "high") {
      openModal();
    }
  };

  const onMeasurement = (message) => {
    const messageData = JSON.parse(message.body);
    let measurement = {
      id: measurements.length * 2,
      waterSpeed: messageData.waterSpeed,
      waterLvl: messageData.waterLvl,
      waterTemp: messageData.waterTemp,
      windSpeed: messageData.windSpeed,
      electricityGenerated: messageData.electricityGenerated,
    };
    setMeasurements((measurements) => [...measurements, measurement]);
    setTurbines(messageData.turbinesActive);
  };

  const openModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
    alarmService.alarmResolved();
  };

  function lastMeasurement() {
    return measurements[measurements.length - 1];
  }

  function beforeLastMeasurement() {
    return measurements[measurements.length - 2];
  }

  function lastAlarm() {
    return alarms[alarms.length - 1];
  }

  function stopPowerPlant() {}

  return (
    <div>
      <PopupModal
        isOpen={modalOpen}
        onClose={closeModal}
        description={lastAlarm()?.description}
        stopPowerPlant={stopPowerPlant}
      />
      <NavBar />
      <div className="mx-auto">
        <div className="mt-14 mx-auto">
          <div className="mx-auto w-[1200px]">
            <Measurements measurements={measurements} turbines={turbines} />
          </div>
          <div className="bg-white p-4 mx-auto mr-9">
            <Alarms alarms={alarms} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
