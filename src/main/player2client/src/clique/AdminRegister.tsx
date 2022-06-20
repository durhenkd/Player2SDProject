import React from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { bindActionCreators } from "redux";
import { spring_axios } from "../axios";
import { actionCreators } from "../state";


type AdminRegisterFormState = {
  username: string;
  email: string;
  password: string;
  telephone: string;
  name: string;
  category: string;
};

const CliqueRegister = () => {
  const navigate = useNavigate();
  
  const dispatch = useDispatch();
  const { adminLogin } = bindActionCreators(actionCreators, dispatch);

  const [error, setError] = React.useState<string>("");
  const [confPass, setConfPass] = React.useState<string>("");
  const [formState, setFormState] = React.useState<AdminRegisterFormState>({
    email: "",
    password: "",
    name: "",
    telephone: "",
    username: "",
    category: "",
  });

  const formHandler = (event: React.FormEvent) => {
    event.preventDefault();
    setError("");

    //* checking for validity
    if (
      formState.name === "" ||
      formState.telephone === "" ||
      formState.username === "" ||
      formState.email === "" ||
      formState.password === "" ||
      formState.category === "" ||
      confPass === ""
    ) {
      setError("Please fill out every field of the form");
      return;
    }

    if (
      formState.password.length < 8 ||
      formState.password.match(`[0-9]`) == null ||
      formState.password.match("[~!@#$%^&*()_=+,/;'<>?:{}]") == null ||
      formState.password.match(`[A-Za-z]`) == null
    ) {
      setError(
        "Password must have at least 8 characters, contain a special character, and number."
      );
      return;
    }

    if (formState.password !== confPass) {
      return;
    }

    console.log(formState);

    spring_axios
      .post("/register/clique", formState)
      .then((res: any) => {
        adminLogin(res.data.name, res.data.id);
        navigate("/player");
      })
      .catch((err: any) => {
        setError(err.message);
        console.log(err);
      });
  };

  return (
    <div className="w-1/3 h-full flex flex-col flex-wrap justify-center items-center">
      <form
        onSubmit={formHandler}
        className="w-full  flex flex-col mb-9 gap-4"
        onChange={() => {
          setError("");
        }}
      >
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
          type="email"
          placeholder="Email"
          value={formState.email}
          onChange={(v) => {
            setFormState({ ...formState, email: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className="grow w-full outline-hover-fill-neutral"
          type="tel"
          placeholder="Telephone number"
          value={formState.telephone}
          onChange={(v) => {
            setFormState({ ...formState, telephone: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className="grow w-full outline-hover-fill-neutral"
          type="text"
          placeholder="Clique Name"
          value={formState.name}
          onChange={(v) => {
            setFormState({ ...formState, name: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className="grow w-full outline-hover-fill-neutral"
          type="text"
          placeholder="Topic"
          value={formState.category}
          onChange={(v) => {
            setFormState({ ...formState, category: v.target.value }); //! making a copy then overwriting
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
        <input
          className="grow w-full outline-hover-fill-neutral"
          type="password"
          placeholder="Confirm Password"
          value={confPass}
          onChange={(v) => {
            setConfPass(v.target.value);
          }}
        />
        {confPass !== formState.password && (
          <p className="text-red-700 text-lg">Passwords don't match!</p>
        )}
        {error && <p className="text-red-700 text-lg">{error}</p>}
        <button onClick={() => {}} className="grow w-full fill-hover-outline">
          Register
        </button>
      </form>
      <div className="flex flex-row w-full justify-center gap-2">
        <button
          onClick={() => {
            navigate("/login");
          }}
          className="grow w-full outline-hover-fill-neutral"
        >
          Log In instead
        </button>
        <button
          onClick={() => {
            navigate("/register");
          }}
          className="grow w-full text-hover-emph"
        >
          Register as a user insead
        </button>
      </div>
    </div>
  );
};

export default CliqueRegister;
