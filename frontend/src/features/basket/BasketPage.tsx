import {
  Box,
  Button,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";
import { useAppDispatch, useAppSelector } from "../../app/store/configureStore";
import agent from "../../app/api/agent";
import { Product } from "../../app/models/product";
import { Remove, Add } from "@mui/icons-material";
import DeleteIcon from "@mui/icons-material/Delete";
import { Link } from "react-router-dom";
import BasketSummary from "./BasketSummary";

export default function BasketPage() {
  const { basket } = useAppSelector((state) => state.basket);
  const dispatch = useAppDispatch();
  const { Basket: BasketActions } = agent;

  const removeItem = (productId: number) => {
    BasketActions.removeItem(productId, dispatch);
  };

  const decrementItem = (productId: number, quantity: number = 1) => {
    BasketActions.decrementItemQuantity(productId, quantity, dispatch);
  };
     
  const incrementItem = (productId: number, quantity: number = 1) => {
    BasketActions.incrementItemQuantity(productId, quantity, dispatch);
  };

  // Define the extractImageName function
  const extractImageName = (item: Product): string | null => {
    if (item && item.pictureUrl) {
      const parts = item.pictureUrl.split("/");
      if (parts.length > 0) {
        return parts[parts.length - 1];
      }
    }
    return null;
  };

  // Function to format the price with GHS currency symbol
  const formatPrice = (price: number): string => {
    return new Intl.NumberFormat("en-GH", {
      style: "currency",
      currency: "GHS",
      minimumFractionDigits: 2,
    }).format(price);
  };

  if (!basket || basket.items.length === 0)
    return (
      <Typography variant="h3">
        Your basket is empty. Please add few items!!!
      </Typography>
    );
  return (
    <>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Product Image</TableCell>
              <TableCell>Product</TableCell>
              <TableCell>Price</TableCell>
              <TableCell>Quantity</TableCell>
              <TableCell>Subtotal</TableCell>
              <TableCell>Remove</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {basket.items.map((item) => (
              <TableRow key={item.id}>
                <TableCell>
                  {item.pictureUrl && (
                    <img
                      src={"/images/products/" + extractImageName(item)}
                      alt="Product"
                      width="50"
                      height="50"
                    />
                  )}
                </TableCell>
                <TableCell>{item.name}</TableCell>
                <TableCell>{formatPrice(item.price)}</TableCell>
                <TableCell>
                  <IconButton
                    color="error"
                    onClick={() => decrementItem(item.id)}
                  >
                    <Remove />
                  </IconButton>
                  {item.quantity}
                  <IconButton
                    color="error"
                    onClick={() => incrementItem(item.id)}
                  >
                    <Add />
                  </IconButton>
                </TableCell>
                <TableCell>{formatPrice(item.price * item.quantity)}</TableCell>
                <TableCell>
                  <IconButton
                    onClick={() => removeItem(item.id)}
                    aria-label="delete"
                  >
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <Box mt={2} p={2} bgcolor="background.paper" borderRadius={4}>
        <BasketSummary />
        <Button
          component={Link}
          to="/checkout"
          variant="contained"
          size="large"
          fullWidth
        >
          Checkout
        </Button>
      </Box>
    </>
  );
}
