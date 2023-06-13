import axios from "axios";

const API_URL = "http://localhost:8080/api/measurements";

class MeasurementService {
  getMeasurements() {
    return axios.get(API_URL + "/all");
  }
}

export default new MeasurementService();
