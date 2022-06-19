module.exports = {
  content: [
    "./public/index.html",
    "./src/**/*.{js,jsx,ts,tsx}",
    "./node_modules/flowbite-react/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      backgroundImage:{
        'dark-woman': "url('/public/background.png')"
      }
    },
  },
  plugins: [require("flowbite/plugin")],
};
