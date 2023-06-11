import React, { useState } from "react";

function NavBar(props) {
  const [isAuth, setIsAuth] = useState(props.isAuth);

  return (
    <nav className="bg-gray-800 py-4">
      <div className="mx-auto">
        <ul className="flex justify-end">
          <li className="text-white mr-5">
            <a href="/login">Logout</a>
          </li>
        </ul>
      </div>
    </nav>
  );
}

export default NavBar;
