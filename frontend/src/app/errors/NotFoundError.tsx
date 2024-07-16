import { Container, Paper, Box, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

// The NotFound component displays a 404 error page with a message and a button to navigate back to the home page.
export default function NotFound() {
  // useNavigate hook to programmatically navigate to different routes
  const navigate = useNavigate();

  // Function to handle the "Go Home" button click, navigating the user to the home page
  const handleGoHome = () => {
    navigate("/");
  };

  return (
    <Container
      component={Paper}
      sx={{
        p: 4, // Padding of 4 units
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
      }}
    >
      {/* Display an image for the 404 error */}
      <Box
        component="img"
        sx={{
          height: "auto",
          width: "100%",
          maxHeight: { xs: 233, md: 400 }, // Responsive maxHeight
          maxWidth: { xs: 350, md: 400 }, // Responsive maxWidth
          mb: 4, // Margin bottom of 4 units
        }}
        src="/images/page-not-found.png" // Path to the 404 error image
        alt="404 Not Found" // Alternative text for the image
      />

      {/* Main heading for the 404 page */}
      <Typography variant="h4" component="h1" gutterBottom>
        Oops! Page not found.
      </Typography>

      {/* Subtitle providing more information */}
      <Typography variant="subtitle1" gutterBottom>
        We can't seem to find the page you're looking for.
      </Typography>

      {/* Button to navigate back to the home page */}
      <Button variant="contained" color="primary" onClick={handleGoHome}>
        Go Home
      </Button>
    </Container>
  );
}
