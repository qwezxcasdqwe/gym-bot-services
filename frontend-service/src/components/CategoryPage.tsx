import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import '../styles/main.css';

interface Product {
  id: number;
  brand: string;
  category: string;
  price: number;
}

const CategoryPage = () => {
  const { category } = useParams();
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    fetch(`http://127.0.0.1:5000/products/category/${category}`)
      .then((res) => res.json())
      .then((data) => setProducts(data))
      .catch((err) => console.error('Error fetching products:', err));
  }, [category]);

  return (
    <div className="container">
      <h2>{category} Products</h2>
      <div className="products-container">
        {products.length > 0 ? (
          products.map((product) => (
            <div key={product.id} className="product-card">
              <h3>{product.brand}</h3>
              <p className="category">{product.category}</p>
              <p className="price">${product.price}</p>
            </div>
          ))
        ) : (
          <p>No products found for this category.</p>
        )}
      </div>
    </div>
  );
};

export default CategoryPage;
