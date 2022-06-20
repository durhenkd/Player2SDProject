import LogIn from "./LogIn";
import NavigationBar from "./NavBar";
import "./index.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import NoMatch from "./NoMatch";
import UserRegister from "./user/UserRegister";
import CliqueRegister from "./clique/AdminRegister";
import Welcome from "./Welcome";
import NewPost from "./clique/NewPost";
import Posts from "./clique/Posts";
import WelcomeUser from "./user/WelcomeUser";
import WelcomeAdmin from "./clique/WelcomeAdmin";
import Post from "./clique/Post";
import Cliques from "./user/Cliques";
import Matches from "./user/Matches";


const App = () => {
  return (
    <BrowserRouter>
      <div className="flex flex-col w-screen h-screen overflow-y-scroll overflow-x-hidden bg-dark-woman">
      <NavigationBar />
      <div className="pt-20 pb-20 w-full h-full flex flex-col items-center">
          <Routes>
            
            <Route path="/" element={<Welcome/>}/>

            <Route path="/player" element={ <WelcomeUser/> }/>
            <Route path="/player/cliques" element={ <Cliques/> }/>
            <Route path="/player/matches" element={ <Matches/> }/>
            <Route path="/login" element={<LogIn />} />
            <Route path="/register" element={<UserRegister />} />

            <Route path="/clique" element={<WelcomeAdmin/>}/>
            <Route path="/clique/new" element={<NewPost/>}/>
            <Route path="/clique/:clique_id/posts/" element={ <Posts/> } />
            <Route path="/clique/:clique_id/posts/:post_id" element={ <Post/> } />
            <Route path="/register/clique" element={<CliqueRegister />} />

            <Route path="*" element={<NoMatch />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default App;
