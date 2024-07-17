import { BasketModel } from "../models/basket";

export function getBasketFromLocalStorage(): BasketModel | null {
  const storedBasket = localStorage.getItem("basket");
  if (storedBasket) {
    try {
      const parsedBasket: BasketModel = JSON.parse(storedBasket);
      return parsedBasket;
    } catch (error) {
      console.error("Error Parsing basket from local storage: ", error);
      return null;
    }
  }
  return null;
}
