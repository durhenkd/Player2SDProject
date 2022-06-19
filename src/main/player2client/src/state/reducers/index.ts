import { combineReducers } from "redux";

import accountReducer from "./accountReduces";

const rootReducer = combineReducers({
  account: accountReducer
});

export default rootReducer;

export type State = ReturnType<typeof rootReducer>

