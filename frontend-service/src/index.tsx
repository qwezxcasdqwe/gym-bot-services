import React from 'react';
import ReactDOM from 'react-dom/client';


const App = () => {
  return(
    <div>Welcome to gym app!</div>
  );
};


const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
// выше делаем рендер приложения
export{};