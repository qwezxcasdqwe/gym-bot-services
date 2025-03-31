import React, { useState } from 'react';
import '../styles/main.css';


const AddProduct = () => {
  const [category, setCategory] = useState('');
  const [brand, setBrand] = useState('');
  const [price, setPrice] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const response = await fetch('http://127.0.0.1:5000/products', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ category, brand, price: parseFloat(price) }),
    });

    if (response.ok) {
      alert('Product added successfully');
      setCategory('');
      setBrand('');
      setPrice('');
    } else {
      alert('Failed to add product');
    }
  };

  return (
    <div className="home">
      <h2>Add a new product</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Category:</label>
          <input
            type="text"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Brand:</label>
          <input
            type="text"
            value={brand}
            onChange={(e) => setBrand(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label>Price:</label>
          <input
            type="number"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            required
          />
        </div>
        <button type="submit">Add Product</button>
      </form>
    </div>
  );
};

export default AddProduct;
