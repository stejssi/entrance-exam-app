import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./styles/theme.css";
import { AuthProvider } from "./context/AutentifikacijaContext.jsx";
import App from "./App.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <BrowserRouter>
      <AuthProvider>  {/*da bi svi dobili prijaviKor, odjavi... */}
        <App />
      </AuthProvider>
    </BrowserRouter>
  </React.StrictMode>
);