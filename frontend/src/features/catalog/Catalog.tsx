import { useEffect, useState } from "react";
import { Product } from "../../app/models/product";
import ProductList from "./ProductList";

export default function Catalog() {
  // Define a state variable products, using useState
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    fetch("http://localhost:8082/api/products")
      .then((response) => response.json())
      .then((data) => setProducts(data.content));
  }, []);

  return (
    <>
      <ProductList products={products} />
    </>
  );
}
