import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { BasketModel } from "../../app/models/basket";

// Define the shape of the Basket state
interface BasketState {
  basket: BasketModel | null; // Holds the current basket state or null if no basket is set
}

// Initial state for the Basket slice
const initialState: BasketState = {
  basket: null, // Initially no basket is set
};

// Create a Redux slice for managing the Basket state
export const basketSlice = createSlice({
  name: "basket", // Slice name
  initialState, // Initial state
  reducers: {
    // Reducer function to set or update the basket state
    setBasket: (state, action: PayloadAction<BasketModel | null>) => {
      console.log("New basket state:", action.payload); // Log the new basket state to the console
      state.basket = action.payload; // Update the basket state with the payload
    },
  },
});

// Export the action creator for setting the basket state
export const { setBasket } = basketSlice.actions;
