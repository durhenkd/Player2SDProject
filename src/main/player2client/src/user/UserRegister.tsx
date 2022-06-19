import * as React from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { bindActionCreators } from "redux";
import { spring_axios } from "../axios";
import { actionCreators } from "../state";

type UserRegisterFormState = {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  gender: string;
  telephone: string;
  picPath: string;
};

const UserRegister = () => {
  const navigate = useNavigate();

  const dispatch = useDispatch();
  const { userLogin } = bindActionCreators(actionCreators, dispatch);

  const [error, setError] = React.useState<string>("");
  const [confPass, setConfPass] = React.useState<string>("");
  const [formState, setFormState] = React.useState<UserRegisterFormState>({
    username: "",
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    gender: "Unspecified",
    telephone: "",
    picPath: "",
  });

  const formHandler = (event: React.FormEvent) => {
    event.preventDefault();

    setError("");

    //* checking for validity
    if (
      formState.username === "" ||
      formState.firstName === "" ||
      formState.lastName === "" ||
      formState.telephone === "" ||
      formState.email === "" ||
      formState.password === "" ||
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

    spring_axios
      .post("/register/player", formState)
      .then((res: any) => {
        userLogin(res.data.name, res.data.id);
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
        className="w-full flex flex-col mb-9 gap-4"
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
          type="text"
          placeholder="First Name"
          value={formState.firstName}
          onChange={(v) => {
            setFormState({ ...formState, firstName: v.target.value }); //! making a copy then overwriting
          }}
        />
        <input
          className="grow w-full outline-hover-fill-neutral"
          type="text"
          placeholder="Last Name"
          value={formState.lastName}
          onChange={(v) => {
            setFormState({ ...formState, lastName: v.target.value }); //! making a copy then overwriting
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
          <h4 className="text-red-700 text-lg">Passwords don't match!</h4>
        )}
        {error && <h4 className="text-red-700 text-lg ">{error}</h4>}
        <div className="flex flex-row text-white font-bold text-lg my-2 gap-6 self-center">
          <label className="grow ">
            <input
              className="mr-1"
              type="radio"
              value="Male"
              name="gender"
              onChange={(v) => {
                setFormState({ ...formState, gender: v.target.value }); //! making a copy then overwriting
              }}
            />
            Male
          </label>
          <label className="grow">
            <input
              className="mr-1"
              type="radio"
              value="Female"
              name="gender"
              onChange={(v) => {
                setFormState({ ...formState, gender: v.target.value }); //! making a copy then overwriting
              }}
            />
            Female
          </label>
          <label className="grow">
            <input
              className="mr-1"
              type="radio"
              value="Other"
              name="gender"
              onChange={(v) => {
                setFormState({ ...formState, gender: v.target.value }); //! making a copy then overwriting
              }}
            />
            Other
          </label>
        </div>
        <button onClick={() => {}} className="grow w-full fill-hover-outline">
          Register
        </button>
      </form>
      <div className="flex flex-row w-full justify-center gap-2">
        <button
          onClick={() => {
            navigate("/login");
          }}
          className="grow outline-hover-fill-neutral"
        >
          Log In instead
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

export default UserRegister;
