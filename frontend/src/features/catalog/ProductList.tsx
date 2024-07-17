import { Grid } from "@mui/material";
import { Product } from "../../app/models/product";
import ProductCard from "./ProductCard";

// Define the props for the ProductList component
interface Props {
  products: Product[]; // An array of Product objects
}

/**
 * ProductList component: Renders a list of products in a grid layout.
 *
 * @param products - An array of Product objects to be displayed.
 * @returns A Grid container with individual ProductCard components.
 */
export default function ProductList({ products }: Props) {
  return (
    <Grid container spacing={4}>
      {/* Iterate over the products array and render each product in a Grid item */}
      {products.map((product) => (
        <Grid item xs={4} key={product.id}>
          {/* Render a ProductCard component for each product */}
          <ProductCard product={product} />
        </Grid>
      ))}
    </Grid>
  );
}
