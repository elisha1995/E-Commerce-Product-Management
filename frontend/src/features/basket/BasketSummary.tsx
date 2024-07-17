import {
  Box,
  Typography,
  TableContainer,
  Paper,
  Table,
  TableBody,
  TableRow,
  TableCell,
} from "@mui/material"; // Import Material UI components for styling and layout
import { useAppSelector } from "../../app/store/configureStore"; // Import custom hook to access Redux state

export default function BasketSummary() {
  // Extract the basket state from the Redux store
  const { basket } = useAppSelector((state) => state.basket);

  // Calculate the subtotal by summing up the price of each item multiplied by its quantity
  const subTotal =
    basket?.items.reduce((sum, item) => sum + item.quantity * item.price, 0) ??
    0;

  // Define a fixed shipping cost
  const shipping = 200;

  // Function to format the price with the GHS currency symbol
  const formatPrice = (price: number): string => {
    return new Intl.NumberFormat("en-GH", {
      style: "currency",
      currency: "GHS",
      minimumFractionDigits: 2,
    }).format(price);
  };

  return (
    <Box
      mt={4} // Add margin top
      p={2} // Add padding
      bgcolor="background.default" // Set background color to the default background color
      borderRadius={8} // Add border radius for rounded corners
      boxShadow={3} // Add shadow for depth
    >
      <Typography variant="h5" gutterBottom>
        Basket Summary
      </Typography>
      <TableContainer component={Paper} variant="outlined">
        <Table>
          <TableBody>
            <TableRow>
              <TableCell>Subtotal</TableCell>
              <TableCell align="right">{formatPrice(subTotal)}</TableCell>
            </TableRow>
            <TableRow>
              <TableCell>Shipping</TableCell>
              <TableCell align="right">{formatPrice(shipping)}</TableCell>
            </TableRow>
            <TableRow>
              <TableCell>
                <strong>Total</strong>
              </TableCell>
              <TableCell align="right">
                <strong>{formatPrice(subTotal + shipping)}</strong>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}
