import React from "react";
import ReactDOM from "react-dom/client";
import "@fontsource/roboto/300.css"; // Import Roboto font styles (light)
import "@fontsource/roboto/400.css"; // Import Roboto font styles (regular)
import "@fontsource/roboto/500.css"; // Import Roboto font styles (medium)
import "@fontsource/roboto/700.css"; // Import Roboto font styles (bold)

import "./app/layout/index.css"; // Import custom CSS styles for the application layout
import { RouterProvider } from "react-router-dom"; // Import RouterProvider from react-router-dom for routing
import { router } from "./app/router/Routes.tsx"; // Import the router configuration from Routes.tsx
import { Provider } from "react-redux"; // Import Provider from react-redux for Redux state management
import { store } from "./app/store/configureStore.ts"; // Import the Redux store configuration from configureStore.ts

// Render the React application into the root element
ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    {/* Wrap the entire application with React.StrictMode for enhanced error handling */}
    <Provider store={store}>
      {/* Provide the Redux store to the application */}
      <RouterProvider router={router}>
        {/* Provide the router configuration to RouterProvider for routing */}
      </RouterProvider>
    </Provider>
  </React.StrictMode>
);
