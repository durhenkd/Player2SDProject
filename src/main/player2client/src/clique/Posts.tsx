import { useSelector } from "react-redux";
import { Link, useParams } from "react-router-dom";
import { State } from "../state";
import useGet from "../useGet";
import PostTitle from "./PostTitle";

export type Post = {
  id: number;
  datetime: string;
  title: string;
  contentPath: string;
  clique_id: number;
  clique_name: string;
};

const Posts = () => {
  const { isAdmin } = useSelector((state: State) => state.account);
  const { clique_id } = useParams<"clique_id">();

  const { data, isPending, error } = useGet<Post[]>(
    isAdmin ? `/clique/post` : `/player/clique/${clique_id}/posts`,
    []
  );

  return (
    <div className="w-full text-white flex flex-col p-20 items-center gap-4">
      {isAdmin ? (
        <p className="font-black text-6xl mb-10">Your Posts</p>
      ) : (
        <p className="font-black text-6xl mb-10">Clique's Posts</p>
      )}
      {isPending && <p className="font-black text-xl mb-10">Loading...</p>}
      {error && <p className="font-black text-6xl mb-10">{error}</p>}
      {data.length === 0 && !isPending ? (
        <p className="font-black text-xl mb-10">No posts yet</p>
      ) : (
        data.map((p) => (
          <Link className="w-full" to={`/clique/${clique_id}/posts/${p.id}`}>
            <PostTitle key={p.id} title={p.title} datetime={p.datetime} clique={p.clique_name} />
          </Link>
        ))
      )}
    </div>
  );
};

export default Posts;
