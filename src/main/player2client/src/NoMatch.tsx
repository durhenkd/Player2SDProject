import { Link } from "react-router-dom";


const NoMatch = () => {
  return <div className="mt-40 w-full flex flex-col items-center">

  <p className="text-7xl mb-20 text-white font-black">
    Sorry, this page has not been found :(
  </p>
  <p className="text-2xl text-white font-semibold">
    Please return to our <Link to={"/"}><b><em><u>Main Page</u></em></b></Link>.
  </p>
</div>
}

export default NoMatch;