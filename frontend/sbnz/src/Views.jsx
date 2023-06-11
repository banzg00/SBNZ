import { Route, Routes, Navigate } from "react-router-dom";
import Login from "./login/Login";
import Home from "./home/Home";
import ProtectedRoutes from "./routesGuards/ProtectedRoutes";
import PublicRoutes from "./routesGuards/PublicRoutes";

const Views = () => {

    return (
      <Routes>
        <Route element={<PublicRoutes />}>
            <Route index element={<Navigate to="/login" />}/>
            <Route path="/login" element={<Login/>} />
            <Route path="*" element={<Navigate to="/login" />} />
        </Route>
        
        <Route element={<ProtectedRoutes />}>
            <Route path="/home" element={<Home />} />
            <Route path="*" element={<Navigate to="/home" />} />
        </Route>
        
      </Routes>
    );
  };
  
  export default Views;