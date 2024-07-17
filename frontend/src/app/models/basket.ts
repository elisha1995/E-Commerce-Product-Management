/**
 * Represents a Basket object containing items.
 */
export interface BasketModel {
  id: string; // Unique identifier for the basket.
  items: BasketItem[]; // List of items in the basket.
}

/**
 * Represents an item that can be added to a Basket.
 */
export interface BasketItem {
  id: number; // Unique identifier for the item.
  name: string; // Name or title of the item.
  description: string; // Description of the item.
  price: number; // Price of the item.
  pictureUrl: string; // URL of the item's picture.
  productBrand: string; // Brand or manufacturer of the item.
  productType: string; // Type or category of the item.
  quantity: number; // Quantity of the item in the basket.
}

/**
 * Represents the calculated totals for a Basket, including shipping and subtotal.
 */
export interface BasketTotals {
  shipping: number; // Shipping cost for the basket.
  subTotal: number; // Subtotal cost of all items in the basket.
  total: number; // Total cost including shipping.
}
