
import { useState, useEffect } from "react";
import { spring_axios } from "./axios";

function useGet<T>(url: string, init: T) {
  const [data, setData] = useState<T>(init);
  const [isPending, setIsPending] = useState(true);
  const [error, setError] = useState<string>("");

  useEffect(() => {
    const abortCont = new AbortController();

    setTimeout(() => {
      spring_axios
        .get(url)
        .then((res: any) => {
          setData(res.data);
          setIsPending(false);
        })
        .catch((error: any) => {
          if (error.response) {
            // The request was made and the server responded with a status code
            // that falls out of the range of 2xx
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
            setError(error.response.status);
          } else if (error.request) {
            // The request was made but no response was received
            // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
            // http.ClientRequest in node.js
            console.log(error.request);
            console.log(error.message);
            setError(error.message);
          } else {
            // Something happened in setting up the request that triggered an Error
            console.log("Error", error.message);
          }
        });
    }, 1000);

    // abort the fetch
    return () => abortCont.abort();
  }, [url]);

  return { data, isPending, error };
}

export default useGet;
