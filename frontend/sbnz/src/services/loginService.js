import axios from "axios";
import inMemoryJwt from "./inMemoryJwtService";

const API_URL = "http://localhost:8080/api/auth";


class LoginService {
    login(loginData){
        return axios.post(API_URL + "/login", loginData)
    }
    
    getUserRole(){
        return axios.get(API_URL + "/getRole",
        {
            headers: {
                'Authorization':`Bearer ${inMemoryJwt.getToken()}`
            } 
         })
    }

}


export default new LoginRegisterService()