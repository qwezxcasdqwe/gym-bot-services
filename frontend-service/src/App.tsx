import React, { useEffect, useState } from 'react';
import './styles/main.css';

interface Product {
  id: number;
  name: string;
  category: string;
}

const App = () => {
  const [products, setProducts] = useState<Product[]>([]);

  // Получаем данные о продуктах с бэкенда с использованием fetch
  useEffect(() => {
    fetch('http://127.0.0.1:5000/products')  // Примерный адрес API
      .then((response) => response.json())
      .then((data: Product[]) => {
        setProducts(data); 
      })
      .catch((error) => {
        console.error('Error fetching products:', error);
      });
  }, []);

  return (
    <div className="container">
      <h1>Sports Nutrition Products</h1>
      <ul>
        {products.map((product) => (
          <li key={product.id}>
            <div>
              <div className="product-name">{product.name}</div>
              <div className="product-category">{product.category}</div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default App;
