import { Link } from "react-router-dom";
import useGet from "../useGet";

type Clique = {
  id: number;
  name: string;
};

const Cliques = () => {
  const { data, isPending, error } = useGet<Clique[]>(`/player/clique`, []);

  return (
    <div className="w-full text-white flex flex-col p-20 items-center gap-4">
      <p className="font-black text-6xl mb-10">Cliques</p>
      {isPending && <p className="font-black text-xl mb-10">Loading...</p>}
      {error && <p className="font-black text-6xl mb-10">{error}</p>}
      {data.length === 0 && !isPending ? (
        <p className="font-black text-xl mb-10">No posts yet</p>
      ) : (
        data.map((p) => (
          <div className="w-full transition flex flex-row items-center">
            <Link className="w-full" to={`/clique/${p.id}/posts`}>
              <p className="p-10 h-full hover:bg-[#C60A5B]/70 rounded-l-3xl text-xl font-black rounded-r-none outline-hover-fill outline-0">
                {p.name}
              </p>
            </Link>
            <button
              className={`p-10 h-full hover:bg-[#C60A5B]/70 rounded-r-3xl text-xl font-black rounded-l-none outline-hover-fill outline-0` }
              onClick={() => {}}
            >
              Follow
            </button>
          </div>
        ))
      )}
    </div>
  );
};

export default Cliques;
