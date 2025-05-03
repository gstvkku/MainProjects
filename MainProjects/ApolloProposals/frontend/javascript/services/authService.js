async function authenticate(email, password) {
  try {
    const response = await fetch(
      "http://localhost:8080/apollo-proposals/api/customers/login",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      }
    );

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(errorText || "Login failed");
    }

    const result = await response.json();

    localStorage.setItem("userLoggedIn", "true");
    localStorage.setItem("companyName", result.companyName);
    localStorage.setItem("customerId", result.id);
    window.location.href = "/home";

    return {
      success: true,
      message: "Login successful!",
      user: result,
    };
  } catch (error) {
    console.error("Error authenticating:", error);
    return {
      success: false,
      message: error.message || "An error occurred. Please try again later.",
    };
  }
}

export default { authenticate };
