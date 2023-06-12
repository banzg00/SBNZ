import React, { useState, useEffect } from "react";
import NavBar from "../components/NavBar";
import Measurements from "../components/Measurements";
import Alarms from "../components/Alarms";
import PopupModal from "../components/PopupModal";
import SockJsClient from "react-stomp";

const SOCKET_URL = "http://localhost:8080/ws";

const Home = () => {
  const [modalOpen, setModalOpen] = useState(false);
  const [message, setMessage] = useState("You server message here.");

  var clientRef;

  let onConnected = () => {
    console.log("Connected!!");
  };

  let onMessageReceived = (msg) => {
    console.log(msg);
  };

  useEffect(() => {
    console.log("componentDidMount");
  });

  function sendMess() {
    this.clientRef.sendMessage("/app/alarm", message);
  }
  const openModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
  };
  const alarms = [
    {
      id: 1,
      description: "Low water pressure",
      time: "10:10",
      severity: "high",
    },
    {
      id: 2,
      description: "High temperature",
      time: "10:10",
      severity: "medium",
    },
    { id: 3, description: "Power outage", time: "10:10", severity: "high" },
  ];
  let measurements = [
    {
      id: 1,
      waterSpeed: 150,
      waterLevel: 80,
      waterTemperature: 25,
      windSpeed: 50,
      electricityGenerated: 1200,
    },
    {
      id: 2,
      waterSpeed: 150,
      waterLevel: 80,
      waterTemperature: 25,
      windSpeed: 50,
      electricityGenerated: 1200,
    },
    {
      id: 3,
      waterSpeed: 130,
      waterLevel: 80,
      waterTemperature: 25,
      windSpeed: 45,
      electricityGenerated: 1200,
    },
    {
      id: 4,
      waterSpeed: 150,
      waterLevel: 80,
      waterTemperature: 25,
      windSpeed: 50,
      electricityGenerated: 1200,
    },
  ];

  function lastMeasurement() {
    return measurements[measurements.length - 1];
  }

  function beforeLastMeasurement() {
    return measurements[measurements.length - 2];
  }

  function lastAlarm() {
    return alarms[alarms.length - 1];
  }

  return (
    <div>
      <SockJsClient
        url={SOCKET_URL}
        topics={["/topic/public"]}
        onConnect={onConnected}
        onDisconnect={console.log("Disconnected!")}
        onMessage={(msg) => onMessageReceived(msg)}
        debug={false}
        ref={(client) => (this.clientRef = client)}
      />
      <PopupModal
        isOpen={modalOpen}
        onClose={closeModal}
        description={lastAlarm().description}
      />
      <NavBar />
      <button onClick={sendMess}>Open modal</button>
      <div className="mx-auto mt-8 ">
        <div className="grid grid-rows-2 gap-4 mt-14 ">
          <div className="mx-auto ">
            <Measurements />
          </div>
          <div className="bg-white p-4 mx-auto">
            <Alarms alarms={alarms} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
