import { Backdrop, Box, CircularProgress, Typography } from "@mui/material";

interface Props {
  message?: string;
}

/**
 * Spinner is a React functional component that displays a loading spinner with an optional message.
 * It uses Material-UI components to create a full-screen backdrop with a circular progress indicator
 * and a text message.
 *
 * @param message An optional message to display below the spinner. Defaults to "Loading..." if not provided.
 * @returns A TSX element representing the loading spinner.
 */
export default function Spinner({ message = "Loading..." }: Props) {
  return (
    <Backdrop open={true} invisible={true}>
      <Box
        display="flex"
        flexDirection="column"
        justifyContent="center"
        alignItems="center"
        height="100vh"
      >
        <CircularProgress size={100} color="secondary" />
        <Typography variant="h4" sx={{ mt: 2 }}>
          {message}
        </Typography>
      </Box>
    </Backdrop>
  );
}
