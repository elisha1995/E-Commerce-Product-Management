import {
  Avatar,
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  CardMedia,
  CircularProgress,
  Link,
  Typography,
} from "@mui/material";
import { Product } from "../../app/models/product";
import { useState } from "react";

interface Props {
  product: Product;
}

export default function ProductCard({ product }: Props) {
  const extractImageName = (item: Product): string | null => {
    if (item && item.pictureUrl) {
      const parts = item.pictureUrl.split("/");
      if (parts.length > 0) {
        return parts[parts.length - 1];
      }
    }
    return null;
  };

  const formatPrice = (price: number): string => {
    return new Intl.NumberFormat("en-GH", {
      style: "currency",
      currency: "GHS",
      minimumFractionDigits: 2,
    }).format(price);
  };

  //   const [loading, setLoading] = useState(false);
  //   const dispatch = useAppDispatch();
  //   function addItem() {
  //     setLoading(true);
  //     agent.Basket.addItem(product, dispatch)
  //       .then((response) => {
  //         console.log("New Basket:", response.basket);
  //         dispatch(setBasket(response.basket));
  //       })
  //       .catch((error) => console.log(error))
  //       .finally(() => setLoading(false));
  //   }

  return (
    <Card>
      <CardHeader
        avatar={
          <Avatar sx={{ bgcolor: "secondary.main" }}>
            {product.name.charAt(0).toUpperCase()}
          </Avatar>
        }
        title={product.name}
        titleTypographyProps={{
          sx: { fontWeight: "bold", color: "primary.main" },
        }}
      />
      <CardMedia
        sx={{ height: 140, backgroundSize: "contain" }}
        image={"/images/products/" + extractImageName(product)}
        title={product.name}
      />
      <CardContent>
        <Typography gutterBottom color="secondary" variant="h5">
          {formatPrice(product.price)}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          {product.productBrand} / {product.productType}
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small">Add to Cart</Button>
        <Button size="small">View</Button>
      </CardActions>
    </Card>
  );
}
