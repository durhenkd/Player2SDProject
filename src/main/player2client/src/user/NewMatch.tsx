import { useState } from "react";
import { useNavigate } from "react-router-dom";
import post from "../post";
import useGet from "../useGet";

type MatchInfo = {
  username: string;
  bio: string;
  postIt: string;
  player_id: number;
  match_id: number;
  gender: string;
};

const NewMatch = () => {
  const navigate = useNavigate();
  const [postIt, setPostIt] = useState<string>("");

  const { data, isPending, error } = useGet<MatchInfo>(`/player/match/new`, {
    username: "",
    bio: "",
    postIt: "",
    gender: "",
    player_id: -1,
    match_id: -1,
  });

  return (
    <div className="w-full h-full text-white flex flex-col p-20 items-center justify-center gap-4">
      {isPending && <p className="font-black text-xl mb-10">Loading...</p>}
      {error && <p className="font-black text-6xl mb-10">{error}</p>}
      {data.match_id === -1 && !isPending ? (
        <p className="font-black text-xl mb-10">
          Oh... Looks like you have no more to match!
        </p>
      ) : (
        <div className="transition rounded-3xl p-20 gap-10 flex flex-col hover:bg-[#C60A5B]/20 items-center">
          <p className="font-black text-5xl">{`${data.username} | ${data.gender}`}</p>
          <p className="font-bold text-2xl mb-10">{data.bio}</p>
          {data.postIt && (
            <p className="font-bold text-lg mb-10">{`Also, they have a message: ${data.postIt}`}</p>
          )}
          <input
            className="grow w-full outline-hover-fill-neutral"
            type="text"
            placeholder="A message for your player 2!"
            value={postIt}
            onChange={(v) => {
              setPostIt(v.target.value);
            }}
          />
          <div className="w-full flex flex-row gap-10">
            <button
              className=" grow text-hover-emph"
              onClick={() => {
                post(`/player/match/${data.match_id}/refuse`, "").then(
                  (res) => {
                    navigate("/player/matches");
                  }
                );
              }}
            >
              Reject
            </button>
            <button
              className=" grow outline-hover-fill"
              onClick={() => {
                post(`/player/match/${data.match_id}/accept`, postIt).then(
                  (res) => {
                    navigate("/player/matches");
                  }
                );
              }}
            >
              Accept
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default NewMatch;
