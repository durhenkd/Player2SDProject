import { useState } from "react";
import ReactMarkdown from "react-markdown";
import { useNavigate } from "react-router-dom";
import post from "../post";

const NewPost = () => {
  const navigate = useNavigate();
  const [text, setText] = useState<string>("");

  const getSel = (
    insert_front: string,
    insert_back: string // JavaScript
  ) => {
    // Obtain the object reference for the <textarea>
    let txtarea: HTMLTextAreaElement = document.getElementById(
      "editor"
    ) as HTMLTextAreaElement;

    let start = txtarea.selectionStart;
    let finish = txtarea.selectionEnd;

    // Obtain the selected text
    let origString = txtarea.value.split("");
    origString.splice(start, 0, insert_front);
    origString.splice(finish + 1, 0, insert_back);
    let news = origString.join("");
    txtarea.value = news;

    txtarea.focus();
    txtarea.selectionEnd = finish + insert_back.length + insert_front.length;

    setText(news);
  };

  const submit = () => {
    let lines = text.split("\n");
    lines[0] = lines[0]
      .replace("#", "")
      .replace(RegExp("[*]+"), "")
      .replace(RegExp("[*]+"), "")
      .trim();

    if (lines[0] === "") return;

    post("/clique/post", { title: lines[0], content: text }).then((res) => {
      if (res !== "error") navigate("/clique");
      else console.log(res);
    });
  };

  return (
    <div className="w-full h-full flex mt-5 flex-row gap-10">
      <div className="w-1/2 h-full flex flex-col gap-4">
        <p className="text-hover-emph">Markdown:</p>
        <div className="mb-4 w-full rounded-xl bg-gray-700/25 border-gray-600/50">
          <div className="flex justify-between items-center py-2 px-3 border-b border-gray-600">
            <div className="flex flex-wrap items-center  sm:divide-x divide-gray-600">
              <div className="flex items-center space-x-1 sm:pr-4">
                <button
                  type="button"
                  className="p-2  rounded cursor-pointer  text-gray-400 hover:text-white hover:bg-gray-600"
                  onClick={() => {
                    getSel("**", "**");
                  }}
                >
                  <svg
                    className="w-4 h-4"
                    fill="currentColor"
                    viewBox="0 0 8 8"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fillRule="evenodd"
                      d="M0 0v1c.55 0 1 .45 1 1v4c0 .55-.45 1-1 1v1h5.5c1.38 0 2.5-1.12 2.5-2.5 0-1-.59-1.85-1.44-2.25.27-.34.44-.78.44-1.25 0-1.1-.89-2-2-2h-5zm3 1h1c.55 0 1 .45 1 1s-.45 1-1 1h-1v-2zm0 3h1.5c.83 0 1.5.67 1.5 1.5s-.67 1.5-1.5 1.5h-1.5v-3z"
                      clip-rule="evenodd"
                    ></path>
                  </svg>
                </button>
                <button
                  type="button"
                  className="p-2  rounded cursor-pointer  text-gray-400 hover:text-white hover:bg-gray-600"
                  onClick={() => {
                    getSel("*", "*");
                  }}
                >
                  <svg
                    className="w-4 h-4"
                    fill="currentColor"
                    viewBox="0 0 18 18"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M14 1.5a.5.5 0 0 1-.5.5 2.413 2.413 0 0 0-1.56.4 2.046 2.046 0 0 0-.64 1.19L8.96 14.5a4.764 4.764 0 0 0-.1.62c0 .59.55.88 1.64.88a.5.5 0 0 1 0 1h-6a.5.5 0 0 1-.02-1h.02a1.97 1.97 0 0 0 2.2-1.59L9.05 3.5a3.136 3.136 0 0 0 .09-.62c0-.59-.55-.88-1.64-.88a.5.5 0 0 1-.5-.5.5.5 0 0 1 .5-.5h6a.5.5 0 0 1 .5.5z"
                      clip-rule="evenodd"
                    ></path>
                  </svg>
                </button>

                <button
                  type="button"
                  className="p-2  rounded cursor-pointer text-gray-400 hover:text-white hover:bg-gray-600"
                  onClick={() => {
                    getSel("***", "***");
                  }}
                >
                  <svg
                    className="w-5 h-5"
                    fill="currentColor"
                    viewBox="0 0 256 256"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M203.99414,55.99512a12,12,0,0,1-12,12H160.64331l-40,120h23.35083a12,12,0,0,1,0,24h-39.918c-.027,0-.05359.00293-.08056.00293-.02649,0-.05323-.00293-.07984-.00293H63.99414a12,12,0,0,1,0-24H95.345l40-120H111.99414a12,12,0,0,1,0-24H152.2002c.01489,0,.02954-.00049.04443,0h39.74951A12,12,0,0,1,203.99414,55.99512Z"
                      clip-rule="evenodd"
                    ></path>
                  </svg>
                </button>
              </div>
              <div className="flex flex-wrap items-center space-x-1 sm:pl-4">
                <button
                  type="button"
                  className="p-2  rounded cursor-pointer  text-gray-400 hover:text-white hover:bg-gray-600"
                  onClick={() => {
                    getSel("# ", "");
                  }}
                >
                  <svg
                    className="w-4 h-4"
                    fill="currentColor"
                    viewBox="0 0 31.29 31.29"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M18.585,31.226v-1.833h0.577c0.497,0,0.961-0.036,1.395-0.105c0.436-0.072,0.812-0.219,1.131-0.438
                      s0.576-0.537,0.769-0.948c0.192-0.412,0.287-0.959,0.287-1.643V16.03H8.547v10.229c0,0.683,0.095,1.229,0.287,1.644
                      c0.192,0.41,0.448,0.729,0.768,0.947c0.32,0.221,0.699,0.365,1.14,0.438c0.442,0.07,0.902,0.106,1.386,0.106h0.577v1.833H0v-1.833
                      h0.555c0.497,0,0.961-0.036,1.396-0.106c0.434-0.071,0.813-0.217,1.141-0.438c0.326-0.221,0.582-0.537,0.769-0.947
                      c0.184-0.412,0.275-0.961,0.275-1.644v-21.4c0-0.639-0.096-1.153-0.287-1.545c-0.194-0.392-0.454-0.693-0.78-0.906
                      C2.742,2.195,2.363,2.056,1.927,1.992c-0.431-0.065-0.89-0.096-1.373-0.096H0V0.063h12.705v1.833h-0.577
                      c-0.483,0-0.943,0.035-1.386,0.107c-0.44,0.07-0.819,0.217-1.14,0.437C9.283,2.66,9.027,2.976,8.834,3.389
                      C8.642,3.8,8.547,4.348,8.547,5.03v8.823h14.195V5.03c0-0.683-0.096-1.23-0.287-1.642s-0.448-0.729-0.768-0.948
                      c-0.32-0.22-0.696-0.366-1.131-0.437c-0.435-0.072-0.897-0.107-1.396-0.107h-0.577V0.063h12.705v1.833h-0.553
                      c-0.498,0-0.961,0.035-1.396,0.107c-0.434,0.07-0.812,0.217-1.141,0.437c-0.326,0.22-0.582,0.536-0.768,0.948
                      c-0.186,0.411-0.277,0.959-0.277,1.642v21.442c0,0.64,0.097,1.155,0.287,1.545c0.192,0.392,0.453,0.687,0.779,0.884
                      c0.326,0.2,0.705,0.331,1.141,0.396c0.434,0.064,0.892,0.096,1.373,0.096h0.556v1.833H18.585z"
                      clip-rule="evenodd"
                    ></path>
                  </svg>
                </button>
                <button
                  type="button"
                  className="p-2  rounded cursor-pointer text-gray-400 hover:text-white hover:bg-gray-600"
                  onClick={() => {
                    getSel("\n&nbsp;\n\n", "");
                  }}
                >
                  <svg
                    className="w-5 h-5"
                    fill="currentColor"
                    viewBox="0 0 18 18"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M16 4H2c-.6 0-1-.4-1-1s.4-1 1-1h14c.6 0 1 .4 1 1s-.4 1-1 1zm0 4H2c-.6 0-1-.4-1-1s.4-1 1-1h14c.6 0 1 .4 1 1s-.4 1-1 1zm1 4v2c0 1-1 2-2 2h-2c0 .3 0 .5-.3.7-.2.2-.4.3-.7.3s-.5 0-.7-.3l-1-1c-.4-.4-.4-1 0-1.4l1-1c.4-.4 1-.4 1.4 0 .2.2.3.4.3.7h2v-2H2c-.6 0-1-.5-1-1s.5-1 1-1h13c1 0 2 1 2 2z"
                      clip-rule="evenodd"
                    ></path>
                  </svg>
                </button>
              </div>
            </div>
          </div>
          <div className="py-2 px-4 rounded-b-lg bg-gray-800/25">
            <textarea
              id="editor"
              className="block px-0 w-full h-full text-sm border-0 bg-transparent focus:ring-0 text-white placeholder-gray-400"
              placeholder="Write an article..."
              required
              onChange={(t) => setText(t.target.value)}
            ></textarea>
          </div>
        </div>
      </div>
      <div className="w-1/2 flex flex-col gap-4">
        <p className="text-hover-emph">Render:</p>
        <ReactMarkdown
          className="grow text-white overflow-y-scroll"
          children={text}
        />
        <button className="fill-hover-outline" onClick={submit}>
          Submit New Post
        </button>
      </div>
    </div>
  );
};

export default NewPost;
