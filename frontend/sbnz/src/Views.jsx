import { Route, Routes, Navigate } from "react-router-dom";
import Login from "./login/Login";
import NotFound from "./notFound/NotFound";
import Home from "./home/Home";
import ProtectedRoutes from "./protectedRoutes/ProtectedRoutes";

const Views = () => {

    const isAuthenticated = true;

    return (
      <Routes>
        <Route index element={<Navigate to="/login" />}/>
        <Route path="/login" element={<Login/>} />

        <Route element={<ProtectedRoutes />}>
            <Route path="/home" element={<Home />} />
            <Route path="*" element={<Navigate to="/home" />} />
        </Route>
        
        <Route path="*" element={<Navigate to="/login" />} />
      </Routes>
    );
  };
  
  export default Views;