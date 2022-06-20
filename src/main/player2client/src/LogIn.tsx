import { FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "./axios";
import React from "react";

import { actionCreators } from "./state/index";
import { bindActionCreators } from "redux";
import { useDispatch } from "react-redux";

type LogInFormState = {
  username: string;
  password: string;
};

const LogIn = () => {
  const navigate = useNavigate();

  const [error, setError] = React.useState<string>("");
  const [formState, setFormState] = React.useState<LogInFormState>({
    username: "",
    password: "",
  });

  const dispatch = useDispatch();
  const { adminLogin, userLogin } = bindActionCreators(
    actionCreators,
    dispatch
  );

  // ! ========== FORM HANDLER ==========
  const formHandler = (event: FormEvent) => {
    event.preventDefault();

    setError("");

    if (formState.password.length < 8 || formState.username === "") {
      setError("All field must be completed");
      return;
    }

    login(formState.username, formState.password).then((data) => {
      console.log(data);
      if(data.id === -1){
        setError("Wrong username or password");
        return;
      }
      if (data.isAdmin) {
        adminLogin(data.name, data.id); //! redux
        navigate("/clique");
      } else {
        userLogin(data.name, data.id); //! redux
        navigate("/player");
      }
    });
  };

  return (
    <div className="w-1/3 h-full flex flex-col flex-wrap gap-4 justify-center items-center">
      <form onSubmit={formHandler} className="w-full flex flex-col mb-3 gap-4">
        <input
          className="grow w-full outline-hover-fill-neutral"
          type="text"
          placeholder="Username"
          value={formState.username}
          onChange={(v) => {
            setFormState({ ...formState, username: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className="grow w-full outline-hover-fill-neutral"
          type="password"
          placeholder="Password"
          value={formState.password}
          onChange={(v) => {
            setFormState({ ...formState, password: v.target.value }); //! making a copy then overwriting
          }}
        />
        {error && <p className="text-red-700 text-lg">{error}</p>}
        <button
          onClick={() => {}}
          className="grow w-full fill-hover-outline"
        >
          Log In
        </button>
      </form>
      <div className="flex flex-row w-full justify-center gap-2">
        
        <button
          onClick={() => {
            navigate("/register");
          }}
          className="grow  outline-hover-fill-neutral"
        >
          Register!
        </button>
        <button
          onClick={() => {
            navigate("/register/clique");
          }}
          className="shrink text-hover-emph"
        >
          Are you a clique?
        </button>
      </div>
    </div>
  );
};

export default LogIn;
