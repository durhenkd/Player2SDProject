import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { Post } from "../clique/Posts";
import PostTitle from "../clique/PostTitle";
import { State } from "../state";
import useGet from "../useGet";

const WelcomeUser = () => {

  const { name } = useSelector((state: State) => state.account);

  const { data, isPending, error } = useGet<Post[]>(`/player/feed`, []);

  return (
    <div className="w-full  flex flex-col text-white items-center gap-7 p-20">
      <p className=" text-8xl font-black">{`Hello, ${name}!`}</p>
      <p className=" text-xl font-bold mb-5">The following posts are waiting for you:</p>
      
      {isPending && <p className="font-black text-xl mb-10">Loading...</p>}
      {error && <p className="font-black text-6xl mb-10">{error}</p>}
      {data.length === 0 && !isPending ? (
        <p className="font-black text-xl mb-10">You not following any cliques!</p>
      ) : (
        data.map((p) => (
          <Link className="w-full" to={`/clique/${p.clique_id}/posts/${p.id}`}>
            <PostTitle key={p.id} title={p.title} datetime={p.datetime} clique={p.clique_name} />
          </Link>
        ))
      )}
    
    </div>
  );
}

export default WelcomeUser;