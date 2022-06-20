

const PostTitle = (props: {title:string, datetime:string}) => {

  return <div className="w-full transition rounded-3xl p-10 gap-5 flex flex-row hover:bg-[#C60A5B]/70 items-center">
    <p className="font-black text-3xl">{props.title}</p>
    <div className="grow"></div>
    <p className="font-bold">{props.datetime}</p>
  </div>
}

export default PostTitle;