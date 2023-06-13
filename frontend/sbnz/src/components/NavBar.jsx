import React, { useState } from "react";

function NavBar(props) {
  return (
    <nav className="bg-gray-800 py-4">
      <div className="mx-auto">
        <ul className="flex justify-end">
          <li className="text-white mr-5">
            <a href="/login" onClick={() => localStorage.clear()}>
              Logout
            </a>
          </li>
        </ul>
      </div>
    </nav>
  );
}

export default NavBar;
