

import { useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { State } from "./state";

import { actionCreators } from "./state/index";
import { bindActionCreators } from "redux";
import { useDispatch } from "react-redux";

export default function NavigationBar() {
  const { id, name, isAdmin } = useSelector((state: State) => state.account);

  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { logOut } = bindActionCreators(actionCreators, dispatch);

  const getLogged = () => {
    return id < 0 ? (
      <button
        className="outline-hover-fill hover:scale-110"
        onClick={() => {
          navigate("/login");
        }}
      >
        Log In
      </button>
    ) : (
      <button
        className="text-hover-emph"
        onClick={() => {
          logOut();
          //! cookie deletion
          var cookies = document.cookie.split("; ");
          for (var c = 0; c < cookies.length; c++) {
            var d = window.location.hostname.split(".");
            while (d.length > 0) {
              var cookieBase =
                encodeURIComponent(cookies[c].split(";")[0].split("=")[0]) +
                "=; expires=Thu, 01-Jan-1970 00:00:01 GMT; domain=" +
                d.join(".") +
                " ;path=";
              // eslint-disable-next-line no-restricted-globals
              var p = location.pathname.split("/");
              document.cookie = cookieBase + "/";
              while (p.length > 0) {
                document.cookie = cookieBase + p.join("/");
                p.pop();
              }
              d.shift();
            }
          }
          //! cookie deletion
          navigate("/");
        }}
      >
        Log Out
      </button>
    );
  };

  const getUsername = () => {
    return id < 0 ? (
      <div></div>
    ) : (
      <p className="text-white font-bold ">
        {`${name} | ${isAdmin ? "clique" : "player"}`}
      </p>
    );
  };

  return (
    <div className="absolute top-0 w-full flex flex-row px-12 py-6 gap-6 self-center items-center">
      <button onClick={() => navigate("/")} className="flex flex-row">
        <img
          src="player2logo.svg"
          className="mr-3 h-6 sm:h-9"
          alt="Player 2 Logo"
        />
        <p className="text-2xl text-white font-black">Player2</p>
      </button>

      <div className="flex-grow"></div>
      {getUsername()}
      {getLogged()}
    </div>
  );
}
