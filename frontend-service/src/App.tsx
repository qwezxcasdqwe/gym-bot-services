import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import AddProduct from './components/AddProduct';
import './styles/main.css';
import CategoryPage from './components/CategoryPage';

const App = () => {
  return (
    <Router>
      <div className="container">
        <header>
          <h1>Sports Nutrition Store</h1>
        </header>
        <main>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/add-product" element={<AddProduct />} />
            <Route path="/category/:category" element={<CategoryPage />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
};

export default App;
