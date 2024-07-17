import { FieldValues } from "react-hook-form";
import { User } from "../../app/models/user";
import { createAsyncThunk, createSlice, isAnyOf } from "@reduxjs/toolkit";
import agent from "../../app/api/agent";
import { router } from "../../app/router/Routes";
import { toast } from "react-toastify";

// Define the initial state for the account slice
interface AccountState {
  user: User | null;
  error: string | null;
}

const initialState: AccountState = {
  user: null,
  error: null,
};

// Async thunk for signing in a user
export const signInUser = createAsyncThunk<User, FieldValues>(
  "auth/login",
  async (data, thunkAPI) => {
    try {
      const user = await agent.Account.login(data);
      localStorage.setItem("user", JSON.stringify(user));
      return user;
    } catch (error: any) {
      return thunkAPI.rejectWithValue({ error: error.data });
    }
  }
);

// Async thunk for fetching the current user from local storage
export const fetchCurrentUser = createAsyncThunk<User | null>(
  "auth/fetchCurrentUser",
  async (_, thunkAPI) => {
    try {
      const userString = localStorage.getItem("user");
      if (userString) {
        const user = JSON.parse(userString) as User;
        return user;
      }
      return null;
    } catch (error) {
      console.error("Error Fetching current User:", error);
      return null;
    }
  }
);

// Async thunk for logging out the user
export const logoutUser = createAsyncThunk<void>(
  "auth/logout",
  async (_, thunkAPI) => {
    try {
      localStorage.removeItem("user");
    } catch (error) {
      console.error("Error logging out user");
    }
  }
);

// Define the account slice
export const accountSlice = createSlice({
  name: "account",
  initialState,
  reducers: {
    logOut: (state) => {
      state.user = null;
      state.error = null;
      localStorage.removeItem("user");
      router.navigate("/store");
    },
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder.addMatcher(
      isAnyOf(signInUser.fulfilled, fetchCurrentUser.fulfilled),
      (state, action) => {
        state.user = action.payload;
        state.error = null;
        toast.success("Sign in successful");
      }
    );
    builder.addMatcher(
      isAnyOf(
        signInUser.rejected,
        fetchCurrentUser.rejected,
        logoutUser.fulfilled
      ),
      (state, action) => {
        const payload = action.payload as string | null;
        state.error = payload;
        toast.error("Sign in failed. Please try again");
      }
    );
  },
});

// Export actions and reducer from the account slice
export const { logOut, clearError } = accountSlice.actions;
export default accountSlice.reducer;
