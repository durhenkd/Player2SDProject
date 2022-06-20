import { useSelector } from "react-redux";
import { State } from "../state";

const WelcomeAdmin = () => {
  const { name } = useSelector((state: State) => state.account);

  return (
    <div className="w-full flex flex-col text-white items-center gap-5 pt-6">
      <p className=" text-8xl font-black">{`Hello, ${name}!`}</p>
      <p className=" text-xl font-bold">Can't wait to see you bringing together more players!</p>
    </div>
  );
};

export default WelcomeAdmin;
