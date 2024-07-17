import axios from "axios";
import { BasketModel, BasketItem, BasketTotals } from "../models/basket";
import { Product } from "../models/product";
import { Dispatch } from "redux";
import { setBasket } from "../../features/basket/basketSlice";
import { createId } from "@paralleldrive/cuid2";

// BasketService class handles all operations related to the basket, such as fetching, adding, updating, and removing items.
class BasketService {
  // Base URL for the basket API
  apiUrl = "http://localhost:8082/api/baskets";

  // Fetches the basket from the API
  async getBasketFromApi() {
    try {
      const response = await axios.get<BasketModel>(`${this.apiUrl}`);
      return response.data;
    } catch (error) {
      throw new Error("Failed to retrieve the basket.");
    }
  }

  // Retrieves the basket from local storage
  async getBasket() {
    try {
      const basket = localStorage.getItem("basket");
      if (basket) {
        return JSON.parse(basket) as BasketModel;
      } else {
        throw new Error("Basket not found in local storage");
      }
    } catch (error) {
      throw new Error("Failed to retrieve the basket: " + error);
    }
  }

  // Adds an item to the basket
  async addItemToBasket(item: Product, quantity = 1, dispatch: Dispatch) {
    try {
      let basket = this.getCurrentBasket();
      if (!basket) {
        basket = await this.createBasket();
      }
      const itemToAdd = this.mapProductToBasket(item);
      basket.items = this.upsertItems(basket.items, itemToAdd, quantity);
      this.setBasket(basket, dispatch);

      // Calculate totals after adding item to the basket
      const totals = this.calculateTotals(basket);
      return { basket, totals };
    } catch (error) {
      throw new Error("Failed to add an item to the Basket.");
    }
  }

  // Removes an item from the basket
  async remove(itemId: number, dispatch: Dispatch) {
    const basket = this.getCurrentBasket();
    if (basket) {
      const itemIndex = basket.items.findIndex((p) => p.id === itemId);
      if (itemIndex !== -1) {
        basket.items.splice(itemIndex, 1);
        this.setBasket(basket, dispatch);
      }

      // Check if the basket is empty after removing the item and clear it from local storage if true
      if (basket.items.length === 0) {
        localStorage.removeItem("basket_id");
        localStorage.removeItem("basket");
      }
    }
  }

  // Increments the quantity of an item in the basket
  async incrementItemQuantity(
    itemId: number,
    quantity: number = 1,
    dispatch: Dispatch
  ) {
    const basket = this.getCurrentBasket();
    if (basket) {
      const item = basket.items.find((p) => p.id === itemId);
      if (item) {
        item.quantity += quantity;
        if (item.quantity < 1) {
          item.quantity = 1;
        }
        this.setBasket(basket, dispatch);
      }
    }
  }

  // Decrements the quantity of an item in the basket
  async decrementItemQuantity(
    itemId: number,
    quantity: number = 1,
    dispatch: Dispatch
  ) {
    const basket = this.getCurrentBasket();
    if (basket) {
      const item = basket.items.find((p) => p.id === itemId);
      if (item && item.quantity > 1) {
        item.quantity -= quantity;
        this.setBasket(basket, dispatch);
      }
    }
  }

  // Deletes the basket from the API
  async deleteBasket(basketId: string): Promise<void> {
    try {
      await axios.delete(`${this.apiUrl}/${basketId}`);
    } catch (error) {
      throw new Error("Failed to delete the basket.");
    }
  }

  // Sets the basket in the API and updates the local storage and Redux store
  async setBasket(basket: BasketModel, dispatch: Dispatch) {
    try {
      await axios.post<BasketModel>(this.apiUrl, basket);
      localStorage.setItem("basket", JSON.stringify(basket));
      dispatch(setBasket(basket));
    } catch (error) {
      throw new Error("Failed to update basket.");
    }
  }

  // Retrieves the current basket from local storage
  private getCurrentBasket() {
    const basket = localStorage.getItem("basket");
    return basket ? (JSON.parse(basket) as BasketModel) : null;
  }

  // Creates a new basket and stores its ID in local storage
  private async createBasket(): Promise<BasketModel> {
    try {
      const newBasket: BasketModel = {
        id: createId(),
        items: [],
      };
      localStorage.setItem("basket_id", newBasket.id);
      return newBasket;
    } catch (error) {
      throw new Error("Failed to create Basket.");
    }
  }

  // Maps a product to a basket item
  private mapProductToBasket(item: Product): BasketItem {
    return {
      id: item.id,
      name: item.name,
      price: item.price,
      description: item.description,
      quantity: 0,
      pictureUrl: item.pictureUrl,
      productBrand: item.productBrand,
      productType: item.productType,
    };
  }

  // Upserts an item in the basket: updates the quantity if the item exists, adds it otherwise
  private upsertItems(
    items: BasketItem[],
    itemToAdd: BasketItem,
    quantity: number
  ): BasketItem[] {
    const existingItem = items.find((x) => x.id == itemToAdd.id);
    if (existingItem) {
      existingItem.quantity += quantity;
    } else {
      itemToAdd.quantity = quantity;
      items.push(itemToAdd);
    }
    return items;
  }

  // Calculates the totals for the basket: shipping, subtotal, and total
  private calculateTotals(basket: BasketModel): BasketTotals {
    const shipping = 0;
    const subTotal = basket.items.reduce(
      (acc, item) => acc + item.price * item.quantity,
      0
    );
    const total = shipping + subTotal;
    return { shipping, subTotal, total };
  }
}

export default new BasketService();
