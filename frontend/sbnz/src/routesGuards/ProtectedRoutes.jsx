import { Navigate, Outlet } from "react-router-dom";
import authService from "../services/authService";

const useAuth = () => {
    return authService.getToken() !== null && authService.getToken() !== undefined;
}

const ProtectedRoutes = () => {
  const isAuth = useAuth();
  return isAuth ? <Outlet /> : <Navigate to="/login" />;
};

export default ProtectedRoutes;
