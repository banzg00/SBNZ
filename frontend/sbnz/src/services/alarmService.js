import axios from "axios";

const API_URL = "http://localhost:8080/api/alarms";

class AlarmService {
  getAlarms() {
    return axios.get(API_URL + "/all");
  }
}

export default new AlarmService();
