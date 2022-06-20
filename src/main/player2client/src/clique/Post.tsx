import ReactMarkdown from "react-markdown";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { State } from "../state";
import useGet from "../useGet";

const Post = () => {
  const { isAdmin } = useSelector((state: State) => state.account);

  const { clique_id } = useParams<"clique_id">();
  const { post_id } = useParams<"post_id">();

  const { data, isPending, error } = useGet<string>(
    isAdmin
      ? `/clique/post/${post_id}`
      : `/player/clique/${clique_id}/posts/${post_id}`,
    ""
  );

  return (
    <div className="w-full text-white flex flex-col p-20 items-center gap-4">
      {isPending && <p className="font-black text-xl mb-10">Loading...</p>}
      {error && <p className="font-black text-6xl mb-10">{error}</p>}
      {data && <ReactMarkdown
          className="grow text-white"
          children={data}
        />}
    </div>
  );
};

export default Post;
