import { Navigate, Outlet } from "react-router-dom";
import authService from "../services/authService";

const useAuth = () => {
    return authService.getToken() === null || authService.getToken() === undefined;
}

const PublicRoutes = () => {
    const isAuth = useAuth();
    return isAuth ? <Outlet /> : <Navigate to="/home" />
}

export default PublicRoutes;