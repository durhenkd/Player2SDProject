import { Link } from "react-router-dom";
import useGet from "../useGet";

type MatchInfo = {
  username: string;
  bio: string;
  postIt: string;
  player_id: number;
  match_id: number;
};

const Matches = () => {
  const { data, isPending, error } = useGet<MatchInfo[]>(`/player/match`, []);

  return (
    <div className="w-full text-white flex flex-col p-20 items-center gap-4">
      <p className="font-black text-6xl mb-10 flex flex-row gap-6">
        Your Matches so far, or...
        <Link to="/player/matches/new">
          <p className="hover:text-[#C60A5B]">Look for more</p>
        </Link>
      </p>
      {isPending && <p className="font-black text-xl mb-10">Loading...</p>}
      {error && <p className="font-black text-6xl mb-10">{error}</p>}
      {data.length === 0 && !isPending ? (
        <p className="font-black text-xl mb-10">Look like no matches yet!</p>
      ) : (
        data.map((p) => (
          <div className="w-full transition rounded-3xl p-10 gap-5 flex flex-row hover:bg-[#C60A5B]/70 items-center">
            <p className="font-black text-3xl">{p.username}</p>
            <p className="font-bold text-lg">{` | ${p.bio}`}</p>
          </div>
        ))
      )}
    </div>
  );
};

export default Matches;
