import { Dispatch } from "redux";
import { AccountActionType } from "../action-types/ActionType";
import { AccountAction } from "../actions/accountActions";


// ! =========== ACCOUNT ACTIONS ===========
export const userLogin = (name: string, id: number) => {
  return (dispatch: Dispatch<AccountAction>) => {
    dispatch({
      type: AccountActionType.USER_LOGIN,
      payload: {
        name: name,
        id: id
      }
    })
  }
}

export const adminLogin = (name: string, id: number) => {
  return (dispatch: Dispatch<AccountAction>) => {
    dispatch({
      type: AccountActionType.ADMIN_LOGIN,
      payload: {
        name: name,
        id: id
      }
    })
  }
}

export const logOut = () => {
  return (dispatch: Dispatch<AccountAction>) => {
    dispatch({
      type: AccountActionType.LOGOUT,
    })
  }
}

// ! =========== CART ACTIONS ===========

