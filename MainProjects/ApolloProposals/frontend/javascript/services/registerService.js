const BASE_URL = "http://localhost:8080/apollo-proposals/api/customers";

async function registerCustomer(customerData) {
  try {
    const response = await fetch(`${BASE_URL}/register`, { // assuming /register is your backend endpoint
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(customerData),
    });

    if (!response.ok) {
      const errorText = await response.text(); // capture server error message if available
      throw new Error(`Failed to register customer: ${errorText}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Registration Error:", error);
    return { success: false, message: error.message };
  }
}

export default { registerCustomer };
