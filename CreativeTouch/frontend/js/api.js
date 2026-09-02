/* ============================================================
   Creative Touch — API client
   Talks to the API Gateway (routes to auth-service / ideas-service)
   ============================================================ */

const API_BASE = "http://localhost:8080";

class ApiError extends Error {
  constructor(status, data) {
    super("API_ERROR");
    this.status = status;
    this.data = data;
  }
}

async function apiRequest(path, { method = "GET", body = null, auth = true } = {}) {
  const headers = { "Content-Type": "application/json" };
  if (auth) {
    const token = getToken();
    if (token) headers["Authorization"] = `Bearer ${token}`;
  }

  let res;
  try {
    res = await fetch(`${API_BASE}${path}`, {
      method,
      headers,
      body: body !== null ? JSON.stringify(body) : undefined,
    });
  } catch (networkErr) {
    const err = new ApiError(0, null);
    err.isNetworkError = true;
    throw err;
  }

  if (res.status === 204) return null;

  let data = null;
  try {
    data = await res.json();
  } catch (_) {
    // no JSON body
  }

  if (!res.ok) throw new ApiError(res.status, data);
  return data;
}

const Api = {
  register(name, email, password) {
    return apiRequest("/auth/register", {
      method: "POST",
      auth: false,
      body: { name, email, password },
    });
  },

  login(email, password) {
    return apiRequest("/auth/login", {
      method: "POST",
      auth: false,
      body: { email, password },
    });
  },

  updateLanguagePreference(languagePreference) {
    return apiRequest(
      `/auth/language-preference?languagePreference=${encodeURIComponent(languagePreference)}`,
      { method: "PUT" }
    );
  },

  deleteAccount() {
    return apiRequest("/auth/account", { method: "DELETE" });
  },

  getIdeas() {
    return apiRequest("/idea");
  },

  createIdea(payload) {
    return apiRequest("/idea", { method: "POST", body: payload });
  },

  updateIdea(id, payload) {
    return apiRequest(`/idea/${id}`, { method: "PUT", body: payload });
  },

  deleteIdea(id) {
    return apiRequest(`/idea/${id}`, { method: "DELETE" });
  },

  generateIdea(niche, language) {
    return apiRequest("/ai/generate-idea", {
      method: "POST",
      body: { niche, language },
    });
  },
};

/* ---------- Token helpers ---------- */

function getToken() {
  return localStorage.getItem("ct_token");
}

function setToken(token) {
  localStorage.setItem("ct_token", token);
}

function clearToken() {
  localStorage.removeItem("ct_token");
}

function parseJwt(token) {
  try {
    const payload = token.split(".")[1];
    const base64 = payload.replace(/-/g, "+").replace(/_/g, "/");
    const json = decodeURIComponent(
      atob(base64)
        .split("")
        .map((c) => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
        .join("")
    );
    return JSON.parse(json);
  } catch (_) {
    return null;
  }
}

function isTokenValid(token) {
  const claims = parseJwt(token);
  if (!claims || !claims.exp) return false;
  return claims.exp * 1000 > Date.now();
}
