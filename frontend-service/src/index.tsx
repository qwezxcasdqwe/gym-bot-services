import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';  // Импортируем ваш компонент App

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement); // Получаем элемент с id="root"
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
