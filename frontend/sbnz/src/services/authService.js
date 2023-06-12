import axios from "axios";
// import inMemoryJwt from "./inMemoryJwtService";

const API_URL = "http://localhost:8080/api/auth";


class AuthService {
    login(loginData) {
        return axios.post(API_URL + "/login", loginData)
    }

    setToken(token) {
        localStorage.setItem('token', token);
    }

    getToken() {
        return localStorage.getItem('token');
    }

    // getUserRole(){
    //     return axios.get(API_URL + "/getRole",
    //     {
    //         headers: {
    //             'Authorization':`Bearer ${inMemoryJwt.getToken()}`
    //         } 
    //      })
    // }

}


export default new AuthService()