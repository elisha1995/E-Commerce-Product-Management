import { useEffect, useState } from "react";
import { Product } from "../models/product";

function App() {
  // Define a state variable products, using useState
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    fetch("http://localhost:8082/api/products")
      .then((response) => response.json())
      .then((data) => setProducts(data.content));
  }, []);

  return (
    <div>
      <h1>GES Sports Store</h1>
      {products.map((product) => (
        <div key={product.id}>
          <p>Name: {product.name}</p>
          <p>Description: {product.description}</p>
          <p>Price: ${product.price}</p>
          <p>Brand: {product.productBrand}</p>
          <p>Type: {product.productType}</p>
        </div>
      ))}
    </div>
  );
}

export default App;
