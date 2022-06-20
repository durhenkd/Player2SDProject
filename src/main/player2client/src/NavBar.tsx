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
    ) : isAdmin ? (
      <Link to="/clique">
        <p className="transition text-white font-bold border-transparent pl-2 border-l-2 hover:border-white">{`${name} | clique`}</p>
      </Link>
    ) : (
      <Link to="/player">
        <p className="transition text-white font-bold border-transparent pl-2 border-l-2 hover:border-white">{`${name} | player`}</p>
      </Link>
    );
  };

  const getFirstAction = () => {
    return id < 0 ? (
      <div></div>
    ) : isAdmin ? (
      <Link to="/clique/new">
        <p className="text-hover-emph">New Post</p>
      </Link>
    ) : (
      <Link to="/player/matches">
        <p className="text-hover-emph">Matches</p>
      </Link>
    );
  };

  const getSecondAction = () => {
    return id < 0 ? (
      <div></div>
    ) : isAdmin ? (
      <Link to={`/clique/${id}/posts`}>
        <p className="text-hover-emph">Posts</p>
      </Link>
    ) : (
      <Link to={`/player/cliques`}>
        <p className="text-hover-emph">Search Cliques</p>
      </Link>
    );
  };

  return (
    <div className="absolute top-0 w-full flex flex-row px-12 py-6 gap-6 self-center items-center">
      <button onClick={() => navigate("/")} className="flex flex-row">
        <svg
          className="h-9"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 342.04 539"
        >
          <defs>
            <style>.a{"{fill:#fff;}"}</style>
          </defs>
          <path
            className="a"
            d="M582.44,300.51a26.32,26.32,0,0,0-45,18.61V542.48a5.92,5.92,0,0,1-5.94,5.94H520.13a5.92,5.92,0,0,1-5.94-5.94V240.9a20.24,20.24,0,0,0-6.33-14.72,26.15,26.15,0,0,0-22.18-12.27H382.58a5.92,5.92,0,0,1-5.94-5.94V196.61a5.92,5.92,0,0,1,5.94-5.94h185.8a20.41,20.41,0,0,0,20.4-20.39v-5.94A26.36,26.36,0,0,0,562.44,138H272A19.91,19.91,0,0,0,258,143.54a20.38,20.38,0,0,0-10,17.43V495.88a26.34,26.34,0,1,0,52.67,0V272.52a5.92,5.92,0,0,1,5.94-5.94h11.42a5.92,5.92,0,0,1,5.94,5.94V574.76A26.26,26.26,0,0,0,350.3,601h.46a17.84,17.84,0,0,0,1.79.06h103a5.92,5.92,0,0,1,5.94,5.94v11.42a5.92,5.92,0,0,1-5.94,5.94H269.84a20.35,20.35,0,0,0-20.39,20.33v6A26.26,26.26,0,0,0,275.72,677H566.27a19.93,19.93,0,0,0,13.93-5.54A20.23,20.23,0,0,0,590.1,654v-335A26,26,0,0,0,582.44,300.51Zm-120.92,242a5.92,5.92,0,0,1-5.94,5.94H376.64V272.52a5.92,5.92,0,0,1,5.94-5.94h78.94Z"
            transform="translate(-248.06 -138)"
          />
        </svg>
        <p className="text-2xl text-white font-black">Player2</p>
      </button>

      <div className="flex-grow"></div>
      {getSecondAction()}
      {getFirstAction()}
      {getUsername()}
      {getLogged()}
    </div>
  );
}
