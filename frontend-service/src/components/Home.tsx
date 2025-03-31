import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/main.css';

const Home = () => {
  const categories = ['Protein', 'Creatine', 'Pre-workout', 'BCAAs'];

  return (
    <div className="home">
      <h2>Welcome to our store!</h2>
      <p className="category-heading">Choose a category to explore our products:</p>
      <div className="category-list">
        {categories.map((category) => (
          <div key={category} className="category-item">
            <Link to={`/category/${category.toLowerCase()}`} className="category-link">
              {category}
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Home;
