import { spring_axios } from "./axios";

async function post<T, R>(url: string, payload: T) : Promise<R | null> {
  return spring_axios
    .post(url, payload)
    .then((res: any) => {
      return res.data;
    })
    .catch((error: any) => {
      if (error.response) {
        // The request was made and the server responded with a status code
        // that falls out of the range of 2xx
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
      } else if (error.request) {
        // The request was made but no response was received
        // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
        // http.ClientRequest in node.js
        console.log(error.request);
        console.log(error.message);
      } else {
        // Something happened in setting up the request that triggered an Error
        console.log("Error", error.message);
      }

      return "error";
    });
}

export default post;
