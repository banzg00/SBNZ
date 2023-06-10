import { Navigate, Outlet } from "react-router-dom";

const useAuth = () => {
    return false;
}

const ProtectedRoutes = () => {
    const isAuth = useAuth();
    return isAuth ? <Outlet /> : <Navigate to="/login" />
}

export default ProtectedRoutes;