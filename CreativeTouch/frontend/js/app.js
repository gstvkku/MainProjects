/* ============================================================
   Creative Touch — App
   Hash-router SPA. Vanilla JS, no frameworks.
   ============================================================ */

/* ---------- Icons ---------- */

const ICONS = {
  sparkle:
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.6" stroke-linecap="round" stroke-linejoin="round"><path d="M12 3v4M12 17v4M3 12h4M17 12h4M5.6 5.6l2.8 2.8M15.6 15.6l2.8 2.8M18.4 5.6l-2.8 2.8M8.4 15.6l-2.8 2.8"/></svg>',
  bolt:
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M13 2 3 14h7l-1 8 10-12h-7l1-8Z"/></svg>',
  eye:
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7-11-7-11-7Z"/><circle cx="12" cy="12" r="3"/></svg>',
  edit:
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M12 20h9"/><path d="M16.5 3.5a2.1 2.1 0 0 1 3 3L7 19l-4 1 1-4Z"/></svg>',
  trash:
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M3 6h18"/><path d="M8 6V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2m3 0-1 14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2L4 6"/><path d="M10 11v6M14 11v6"/></svg>',
  gear:
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 1 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 1 1-2.83-2.83l.06-.06A1.65 1.65 0 0 0 4.68 15a1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 1 1 2.83-2.83l.06.06A1.65 1.65 0 0 0 9 4.6a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 1 1 2.83 2.83l-.06.06A1.65 1.65 0 0 0 19.4 9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1Z"/></svg>',
  logout:
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><path d="M16 17l5-5-5-5"/><path d="M21 12H9"/></svg>',
  arrowLeft:
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M19 12H5"/><path d="M12 19l-7-7 7-7"/></svg>',
  search:
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="11" cy="11" r="7"/><path d="m21 21-4.3-4.3"/></svg>',
  close:
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M18 6 6 18"/><path d="M6 6l12 12"/></svg>',
  check:
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 6 9 17l-5-5"/></svg>',
  alert:
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M12 9v4"/><path d="M12 17h.01"/><circle cx="12" cy="12" r="10"/></svg>',
  lightbulb:
    '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M9 18h6"/><path d="M10 22h4"/><path d="M12 2a7 7 0 0 0-4 12.7c.6.5 1 1.3 1 2.3h6c0-1 .4-1.8 1-2.3A7 7 0 0 0 12 2Z"/></svg>',
};
function icon(name) {
  return `<span class="icon" aria-hidden="true">${ICONS[name] || ""}</span>`;
}

/* ---------- State ---------- */

const state = {
  token: null,
  userId: null,
  email: null,
  name: null,
  ideas: null, // cache of last fetched ideas array
};

function profileKey(userId) {
  return `ct_profile_${userId}`;
}

function loadProfile(userId) {
  try {
    return JSON.parse(localStorage.getItem(profileKey(userId)));
  } catch (_) {
    return null;
  }
}

function saveProfile(userId, profile) {
  localStorage.setItem(profileKey(userId), JSON.stringify(profile));
}

function hydrateSession() {
  const token = getToken();
  if (!token || !isTokenValid(token)) {
    clearToken();
    state.token = null;
    return false;
  }
  const claims = parseJwt(token);
  state.token = token;
  state.userId = claims.sub;
  state.email = claims.email;

  const cached = loadProfile(state.userId);
  if (cached) {
    state.name = cached.name;
    if (cached.lang) setLang(cached.lang);
  } else {
    state.name = (claims.email || "").split("@")[0];
  }
  return true;
}

function isAuthed() {
  return !!state.token;
}

function logout() {
  clearToken();
  state.token = null;
  state.userId = null;
  state.email = null;
  state.name = null;
  state.ideas = null;
  setLang(DEFAULT_LANG); // public pages always start in the default language
  navigate("welcome");
}

/* ---------- Toasts ---------- */

function toast(message, type = "success") {
  const container = document.getElementById("toast-container");
  const el = document.createElement("div");
  el.className = `toast toast--${type}`;
  el.innerHTML = `${icon(type === "error" ? "alert" : "check")}<span>${escapeHtml(message)}</span>`;
  container.appendChild(el);
  requestAnimationFrame(() => el.classList.add("toast--show"));
  setTimeout(() => {
    el.classList.remove("toast--show");
    setTimeout(() => el.remove(), 250);
  }, 3800);
}

function apiErrorMessage(err, fallbackKey) {
  if (err && err.isNetworkError) return t("networkError");
  return t(fallbackKey || "genericError");
}

/* ---------- Helpers ---------- */

function escapeHtml(str) {
  const div = document.createElement("div");
  div.textContent = str == null ? "" : String(str);
  return div.innerHTML;
}

function truncate(str, n) {
  if (!str) return "";
  return str.length > n ? str.slice(0, n).trim() + "…" : str;
}

function formatDate(iso) {
  if (!iso) return "";
  try {
    const d = new Date(iso);
    const lang = getLang();
    return d.toLocaleDateString(lang === "pt" ? "pt-BR" : "en-US", {
      year: "numeric",
      month: "short",
      day: "numeric",
    });
  } catch (_) {
    return iso;
  }
}

function langWordFor(lang) {
  return lang === "pt" ? "Portuguese" : "English";
}

function initials(name) {
  if (!name) return "?";
  return name
    .trim()
    .split(/\s+/)
    .slice(0, 2)
    .map((p) => p[0].toUpperCase())
    .join("");
}

/* ---------- Router ---------- */

const app = document.getElementById("app");

const PUBLIC_ROUTES = ["welcome", "login", "register", "goodbye"];

function navigate(path) {
  location.hash = `#/${path}`;
}

function currentRoute() {
  const hash = location.hash.replace(/^#\/?/, "");
  return hash || "welcome";
}

async function route() {
  const raw = currentRoute();
  const [base, param] = raw.split("/");
  const authed = isAuthed();

  if (!authed && !PUBLIC_ROUTES.includes(base)) {
    navigate("login");
    return;
  }
  if (authed && PUBLIC_ROUTES.includes(base)) {
    navigate("dashboard");
    return;
  }

  window.scrollTo(0, 0);
  closeModal();

  switch (base) {
    case "welcome":
      return renderWelcome();
    case "login":
      return renderLogin();
    case "register":
      return renderRegister();
    case "dashboard":
      return renderDashboard();
    case "ideas":
      return param ? renderIdeaDetail(param) : renderIdeasList();
    case "settings":
      return renderSettings();
    case "goodbye":
      return renderGoodbye();
    default:
      return authed ? renderDashboard() : renderWelcome();
  }
}

window.addEventListener("hashchange", route);

/* ---------- Shell (topbar for authed views) ---------- */

function shell(contentHtml, activeNav) {
  const nav = [
    { key: "dashboard", label: "navDashboard" },
    { key: "ideas", label: "navMyIdeas" },
    { key: "settings", label: "navSettings" },
  ];

  return `
    <div class="shell">
      <header class="topbar">
        <a class="brand" href="#/dashboard">
          ${icon("sparkle")}<span>${t("appName")}</span>
        </a>
        <nav class="topnav">
          ${nav
            .map(
              (n) => `
            <a href="#/${n.key}" class="topnav__link ${activeNav === n.key ? "is-active" : ""}">${t(n.label)}</a>
          `
            )
            .join("")}
        </nav>
        <div class="topbar__user">
          <a href="#/settings" class="avatar" title="${escapeHtml(state.name || "")}">${escapeHtml(initials(state.name))}</a>
          <button class="icon-btn" id="logout-btn" title="${t("navLogout")}">${icon("logout")}</button>
        </div>
      </header>
      <main class="shell__main">${contentHtml}</main>
    </div>
  `;
}

function bindShell() {
  const logoutBtn = document.getElementById("logout-btn");
  if (logoutBtn) logoutBtn.addEventListener("click", logout);
}

/* ---------- Welcome (landing) ---------- */

function renderWelcome() {
  app.innerHTML = `
    <div class="landing">
      <div class="landing__glow"></div>
      <header class="landing__nav">
        <div class="brand brand--light">${icon("sparkle")}<span>${t("appName")}</span></div>
        <div class="landing__nav-actions">
          <a href="#/login" class="btn btn--ghost">${t("loginButton")}</a>
          <a href="#/register" class="btn btn--primary">${t("landingCtaPrimary")}</a>
        </div>
      </header>

      <section class="hero">
        <span class="eyebrow">${icon("bolt")}${t("landingEyebrow")}</span>
        <h1 class="hero__title">${t("landingTitle")} <span class="sparkle-emoji">✨</span></h1>
        <p class="hero__subtitle">${t("landingSubtitle")}</p>
        <div class="hero__actions">
          <a href="#/register" class="btn btn--primary btn--lg">${icon("sparkle")}${t("landingCtaPrimary")}</a>
          <a href="#/login" class="btn btn--ghost btn--lg">${t("landingCtaSecondary")}</a>
        </div>
      </section>

      <section class="features">
        <div class="feature-card">
          <div class="feature-card__icon">${icon("bolt")}</div>
          <h3>${t("featureSparkTitle")}</h3>
          <p>${t("featureSparkDesc")}</p>
        </div>
        <div class="feature-card">
          <div class="feature-card__icon">${icon("lightbulb")}</div>
          <h3>${t("featureSaveTitle")}</h3>
          <p>${t("featureSaveDesc")}</p>
        </div>
        <div class="feature-card">
          <div class="feature-card__icon">${icon("sparkle")}</div>
          <h3>${t("featureDashTitle")}</h3>
          <p>${t("featureDashDesc")}</p>
        </div>
      </section>

      <footer class="landing__footer">${t("landingFooter")}</footer>
    </div>
  `;
}

/* ---------- Login ---------- */

function renderLogin() {
  app.innerHTML = authShell(`
    <span class="eyebrow eyebrow--center">${t("loginEyebrow")}</span>
    <h1 class="auth-card__title">${t("loginTitle")}</h1>
    <p class="auth-card__subtitle">${t("loginSubtitle")}</p>

    <form id="login-form" class="form" novalidate>
      <label class="field">
        <span>${t("emailLabel")}</span>
        <input type="email" name="email" required placeholder="${t("emailPlaceholder")}" autocomplete="email" />
      </label>
      <label class="field">
        <span>${t("passwordLabel")}</span>
        <input type="password" name="password" required placeholder="${t("passwordPlaceholder")}" autocomplete="current-password" />
      </label>
      <p class="form-error" id="form-error" hidden></p>
      <button type="submit" class="btn btn--primary btn--block btn--lg" id="submit-btn">${t("loginButton")}</button>
    </form>

    <p class="auth-card__foot">${t("noAccount")} <a href="#/register">${t("goRegister")}</a></p>
  `);

  const form = document.getElementById("login-form");
  const errorEl = document.getElementById("form-error");
  const submitBtn = document.getElementById("submit-btn");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    errorEl.hidden = true;
    const fd = new FormData(form);
    const email = fd.get("email").trim();
    const password = fd.get("password");

    submitBtn.disabled = true;
    submitBtn.textContent = t("loginLoading");

    try {
      const res = await Api.login(email, password);
      setToken(res.token);
      hydrateSession();

      const cached = loadProfile(state.userId);
      if (!cached) {
        saveProfile(state.userId, { name: state.name, email: state.email, lang: getLang() });
      }
      toast(t("ideaSaved") ? `${t("dashboardGreeting", { name: state.name })}` : "", "success");
      navigate("dashboard");
    } catch (err) {
      errorEl.textContent = err.isNetworkError ? t("networkError") : t("loginError");
      errorEl.hidden = false;
      submitBtn.disabled = false;
      submitBtn.textContent = t("loginButton");
    }
  });
}

/* ---------- Register ---------- */

function renderRegister() {
  app.innerHTML = authShell(`
    <span class="eyebrow eyebrow--center">${t("registerEyebrow")}</span>
    <h1 class="auth-card__title">${t("registerTitle")}</h1>
    <p class="auth-card__subtitle">${t("registerSubtitle")}</p>

    <form id="register-form" class="form" novalidate>
      <label class="field">
        <span>${t("nameLabel")}</span>
        <input type="text" name="name" required placeholder="${t("namePlaceholder")}" autocomplete="name" />
      </label>
      <label class="field">
        <span>${t("emailLabel")}</span>
        <input type="email" name="email" required placeholder="${t("emailPlaceholder")}" autocomplete="email" />
      </label>
      <label class="field">
        <span>${t("passwordLabel")}</span>
        <input type="password" name="password" required minlength="6" placeholder="${t("passwordPlaceholder")}" autocomplete="new-password" />
      </label>
      <p class="form-error" id="form-error" hidden></p>
      <button type="submit" class="btn btn--primary btn--block btn--lg" id="submit-btn">${t("registerButton")}</button>
    </form>

    <p class="auth-card__foot">${t("haveAccount")} <a href="#/login">${t("goLogin")}</a></p>
  `);

  const form = document.getElementById("register-form");
  const errorEl = document.getElementById("form-error");
  const submitBtn = document.getElementById("submit-btn");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    errorEl.hidden = true;
    const fd = new FormData(form);
    const name = fd.get("name").trim();
    const email = fd.get("email").trim();
    const password = fd.get("password");

    submitBtn.disabled = true;
    submitBtn.textContent = t("registerLoading");

    try {
      await Api.register(name, email, password);
      // auto-login right after register so we can capture the userId
      // (the register endpoint doesn't return one) and cache the profile.
      const loginRes = await Api.login(email, password);
      setToken(loginRes.token);
      hydrateSession();
      state.name = name;
      saveProfile(state.userId, { name, email, lang: getLang() });

      toast(t("ideaSaved") ? "" : "");
      navigate("dashboard");
    } catch (err) {
      errorEl.textContent = err.isNetworkError ? t("networkError") : t("registerError");
      errorEl.hidden = false;
      submitBtn.disabled = false;
      submitBtn.textContent = t("registerButton");
    }
  });
}

function authShell(innerHtml) {
  return `
    <div class="auth">
      <div class="auth__glow"></div>
      <a href="#/welcome" class="brand brand--light auth__brand">${icon("sparkle")}<span>${t("appName")}</span></a>
      <div class="auth-card">${innerHtml}</div>
    </div>
  `;
}

/* ---------- Dashboard ---------- */

async function renderDashboard() {
  app.innerHTML = shell(`
    <section class="hero-panel">
      <div class="hero-panel__glow"></div>
      <p class="hero-panel__greeting">${t("dashboardGreeting", { name: escapeHtml(state.name) })}</p>
      <h1 class="hero-panel__tagline">${t("dashboardTagline")}</h1>
      <button class="generate-btn" id="open-generate">
        <span class="generate-btn__ring"></span>
        <span class="generate-btn__inner">${icon("sparkle")}${t("generateButton")}</span>
      </button>
    </section>

    <section class="section">
      <div class="section__head">
        <h2>${t("recentIdeasTitle")}</h2>
        <a href="#/ideas" class="link-more">${t("viewAllButton")} →</a>
      </div>
      <div id="recent-ideas" class="idea-grid idea-grid--loading">
        ${skeletonCards(3)}
      </div>
    </section>
  `, "dashboard");
  bindShell();

  document.getElementById("open-generate").addEventListener("click", () => openGenerateModal());

  try {
    const ideas = await Api.getIdeas();
    state.ideas = ideas;
    const recent = [...ideas]
      .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
      .slice(0, 3);
    const wrap = document.getElementById("recent-ideas");
    wrap.classList.remove("idea-grid--loading");
    wrap.innerHTML = recent.length
      ? recent.map((idea) => ideaCardHtml(idea)).join("")
      : emptyStateHtml("noIdeasYetTitle", "noIdeasYetDesc");
    bindIdeaCardActions(wrap);
  } catch (err) {
    const wrap = document.getElementById("recent-ideas");
    wrap.classList.remove("idea-grid--loading");
    wrap.innerHTML = errorStateHtml(apiErrorMessage(err));
  }
}

function skeletonCards(n) {
  return Array.from({ length: n })
    .map(() => `<div class="idea-card idea-card--skeleton"></div>`)
    .join("");
}

function emptyStateHtml(titleKey, descKey) {
  return `
    <div class="empty-state">
      ${icon("lightbulb")}
      <h3>${t(titleKey)}</h3>
      <p>${t(descKey)}</p>
    </div>
  `;
}

function errorStateHtml(message) {
  return `<div class="empty-state empty-state--error">${icon("alert")}<p>${escapeHtml(message)}</p></div>`;
}

/* ---------- Idea cards ---------- */

function ideaCardHtml(idea) {
  return `
    <article class="idea-card" data-id="${idea.id}">
      <div class="idea-card__top">
        <span class="chip">${escapeHtml(idea.niche)}</span>
        <span class="idea-card__date">${formatDate(idea.updatedAt || idea.createdAt)}</span>
      </div>
      <h3 class="idea-card__title">${escapeHtml(idea.title)}</h3>
      <p class="idea-card__desc">${escapeHtml(truncate(idea.description, 130))}</p>
      <div class="idea-card__actions">
        <button class="icon-btn" data-action="view" title="${t("viewButton")}">${icon("eye")}</button>
        <button class="icon-btn" data-action="edit" title="${t("editButton")}">${icon("edit")}</button>
        <button class="icon-btn icon-btn--danger" data-action="delete" title="${t("deleteButton")}">${icon("trash")}</button>
      </div>
    </article>
  `;
}

function bindIdeaCardActions(container) {
  container.querySelectorAll(".idea-card[data-id]").forEach((card) => {
    const id = card.dataset.id;
    card.querySelector('[data-action="view"]')?.addEventListener("click", () => navigate(`ideas/${id}`));
    card.querySelector('[data-action="edit"]')?.addEventListener("click", () => navigate(`ideas/${id}`));
    card.querySelector('[data-action="delete"]')?.addEventListener("click", () => confirmDeleteIdea(id, container));
    card.addEventListener("click", (e) => {
      if (e.target.closest(".icon-btn")) return;
      navigate(`ideas/${id}`);
    });
  });
}

async function confirmDeleteIdea(id, refreshContainer) {
  openConfirmModal({
    title: t("deleteConfirmTitle"),
    desc: t("deleteConfirmDesc"),
    confirmLabel: t("deleteConfirmButton"),
    onConfirm: async () => {
      try {
        await Api.deleteIdea(id);
        if (state.ideas) state.ideas = state.ideas.filter((i) => i.id !== id);
        toast(t("ideaDeleted"));
        closeModal();
        route();
      } catch (err) {
        toast(apiErrorMessage(err), "error");
      }
    },
  });
}

/* ---------- Ideas list ---------- */

async function renderIdeasList() {
  app.innerHTML = shell(`
    <div class="section__head section__head--list">
      <h1>${t("ideasListTitle")}</h1>
      <div class="search-box">
        ${icon("search")}
        <input type="text" id="idea-search" placeholder="${t("searchPlaceholder")}" />
      </div>
    </div>
    <div id="ideas-list" class="idea-grid idea-grid--loading">${skeletonCards(6)}</div>
  `, "ideas");
  bindShell();

  const listEl = document.getElementById("ideas-list");
  const searchInput = document.getElementById("idea-search");

  function renderList(ideas) {
    listEl.classList.remove("idea-grid--loading");
    if (!ideas.length) {
      listEl.innerHTML = emptyStateHtml(
        searchInput.value ? "emptySearchTitle" : "emptyIdeasTitle",
        searchInput.value ? "emptySearchDesc" : "emptyIdeasDesc"
      );
      return;
    }
    listEl.innerHTML = ideas
      .sort((a, b) => new Date(b.updatedAt) - new Date(a.updatedAt))
      .map((idea) => ideaCardHtml(idea))
      .join("");
    bindIdeaCardActions(listEl);
  }

  try {
    const ideas = state.ideas || (await Api.getIdeas());
    state.ideas = ideas;
    renderList(ideas);

    searchInput.addEventListener("input", () => {
      const q = searchInput.value.trim().toLowerCase();
      const filtered = !q
        ? state.ideas
        : state.ideas.filter(
            (i) =>
              i.title.toLowerCase().includes(q) ||
              i.niche.toLowerCase().includes(q) ||
              (i.description || "").toLowerCase().includes(q)
          );
      renderList(filtered);
    });
  } catch (err) {
    listEl.classList.remove("idea-grid--loading");
    listEl.innerHTML = errorStateHtml(apiErrorMessage(err));
  }
}

/* ---------- Idea detail / edit ---------- */

async function getIdeaById(id) {
  if (state.ideas) {
    const found = state.ideas.find((i) => i.id === id);
    if (found) return found;
  }
  const ideas = await Api.getIdeas();
  state.ideas = ideas;
  return ideas.find((i) => i.id === id) || null;
}

async function renderIdeaDetail(id) {
  app.innerHTML = shell(`<div class="detail-loading">${t("loading")}</div>`, "ideas");
  bindShell();

  let idea;
  try {
    idea = await getIdeaById(id);
  } catch (err) {
    app.querySelector(".shell__main").innerHTML = errorStateHtml(apiErrorMessage(err));
    return;
  }

  if (!idea) {
    app.querySelector(".shell__main").innerHTML = `
      <div class="empty-state empty-state--error">
        ${icon("alert")}<p>${t("ideaNotFound")}</p>
        <a href="#/ideas" class="btn btn--ghost">${icon("arrowLeft")}${t("backToIdeas")}</a>
      </div>
    `;
    return;
  }

  const main = app.querySelector(".shell__main");
  main.innerHTML = `
    <a href="#/ideas" class="back-link">${icon("arrowLeft")}${t("backToIdeas")}</a>

    <div class="detail-card">
      <div class="detail-card__meta">
        <span class="chip">${escapeHtml(idea.niche)}</span>
        <span class="detail-card__dates">
          ${t("createdLabel")}: ${formatDate(idea.createdAt)} · ${t("updatedLabel")}: ${formatDate(idea.updatedAt)}
        </span>
      </div>

      <form id="idea-form" class="form">
        <label class="field">
          <span>${t("titleLabel")}</span>
          <input type="text" name="title" required maxlength="255" value="${escapeHtml(idea.title)}" />
        </label>
        <label class="field">
          <span>${t("nicheLabel")}</span>
          <input type="text" name="niche" required maxlength="100" value="${escapeHtml(idea.niche)}" />
        </label>
        <label class="field">
          <span>${t("resultDescriptionLabel")}</span>
          <textarea name="description" required rows="5">${escapeHtml(idea.description)}</textarea>
        </label>
        <label class="field">
          <span>${t("observationLabel")}</span>
          <textarea name="observation" rows="3" placeholder="${t("observationPlaceholder")}">${escapeHtml(idea.observation || "")}</textarea>
        </label>
        <p class="form-error" id="form-error" hidden></p>
        <div class="detail-card__actions">
          <button type="submit" class="btn btn--primary" id="save-btn">${t("saveChanges")}</button>
          <button type="button" class="btn btn--danger-ghost" id="delete-btn">${icon("trash")}${t("deleteButton")}</button>
        </div>
      </form>
    </div>
  `;

  const form = document.getElementById("idea-form");
  const errorEl = document.getElementById("form-error");
  const saveBtn = document.getElementById("save-btn");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    errorEl.hidden = true;
    const fd = new FormData(form);
    const payload = {
      title: fd.get("title").trim(),
      niche: fd.get("niche").trim(),
      description: fd.get("description").trim(),
      observation: fd.get("observation").trim(),
    };
    saveBtn.disabled = true;
    try {
      const updated = await Api.updateIdea(id, payload);
      state.ideas = (state.ideas || []).map((i) => (i.id === id ? updated : i));
      toast(t("ideaUpdated"));
      renderIdeaDetail(id);
    } catch (err) {
      errorEl.textContent = apiErrorMessage(err);
      errorEl.hidden = false;
      saveBtn.disabled = false;
    }
  });

  document.getElementById("delete-btn").addEventListener("click", () => {
    confirmDeleteIdea(id, null);
  });
}

/* ---------- Settings ---------- */

function renderSettings() {
  const lang = getLang();
  app.innerHTML = shell(`
    <div class="settings">
      <h1>${t("settingsTitle")}</h1>

      <section class="settings__section">
        <h2>${t("accountSectionTitle")}</h2>
        <div class="settings__row">
          <span class="settings__label">${t("nameLabel")}</span>
          <span class="settings__value">${escapeHtml(state.name)}</span>
        </div>
        <div class="settings__row">
          <span class="settings__label">${t("emailLabel")}</span>
          <span class="settings__value">${escapeHtml(state.email)}</span>
        </div>
      </section>

      <section class="settings__section">
        <h2>${t("languageSectionTitle")}</h2>
        <p class="settings__desc">${t("languageSectionDesc")}</p>
        <div class="lang-toggle" id="lang-toggle">
          <button type="button" class="lang-option ${lang === "en" ? "is-active" : ""}" data-lang="en">🇬🇧 English</button>
          <button type="button" class="lang-option ${lang === "pt" ? "is-active" : ""}" data-lang="pt">🇧🇷 Português</button>
        </div>
        <button class="btn btn--primary" id="save-lang-btn" style="margin-top:1.25rem">${t("saveLanguageButton")}</button>
      </section>

      <section class="settings__section settings__section--danger">
        <h2>${t("dangerZoneTitle")}</h2>
        <p class="settings__desc">${t("dangerZoneDesc")}</p>
        <button class="btn btn--danger-ghost" id="delete-account-btn">${icon("trash")}${t("deleteAccountButton")}</button>
      </section>
    </div>
  `, "settings");
  bindShell();

  document.getElementById("delete-account-btn").addEventListener("click", () => {
    openConfirmModal({
      title: t("deleteAccountConfirmTitle"),
      desc: t("deleteAccountConfirmDesc"),
      confirmLabel: t("deleteAccountConfirmButton"),
      onConfirm: async (btn) => {
        btn.disabled = true;
        btn.textContent = t("deletingAccount");
        try {
          await Api.deleteAccount();
          if (state.userId) localStorage.removeItem(profileKey(state.userId));
          clearToken();
          state.token = null;
          state.userId = null;
          state.email = null;
          state.name = null;
          state.ideas = null;
          closeModal();
          navigate("goodbye");
        } catch (err) {
          toast(apiErrorMessage(err), "error");
          btn.disabled = false;
          btn.textContent = t("deleteAccountConfirmButton");
        }
      },
    });
  });

  let selectedLang = lang;
  const toggle = document.getElementById("lang-toggle");
  toggle.querySelectorAll(".lang-option").forEach((btn) => {
    btn.addEventListener("click", () => {
      selectedLang = btn.dataset.lang;
      toggle.querySelectorAll(".lang-option").forEach((b) => b.classList.remove("is-active"));
      btn.classList.add("is-active");
    });
  });

  document.getElementById("save-lang-btn").addEventListener("click", async () => {
    const btn = document.getElementById("save-lang-btn");
    btn.disabled = true;
    try {
      await Api.updateLanguagePreference(selectedLang);
      setLang(selectedLang);
      const profile = loadProfile(state.userId) || { name: state.name, email: state.email };
      profile.lang = selectedLang;
      saveProfile(state.userId, profile);
      renderSettings();
      toast(t("languageSaved"));
    } catch (err) {
      toast(apiErrorMessage(err), "error");
      btn.disabled = false;
    }
  });
}

/* ---------- Goodbye (after account deletion) ---------- */

function renderGoodbye() {
  app.innerHTML = `
    <div class="auth">
      <div class="auth__glow"></div>
      <div class="brand brand--light auth__brand">${icon("sparkle")}<span>${t("appName")}</span></div>
      <div class="auth-card goodbye-card">
        <div class="modal__spark goodbye-card__icon">${icon("lightbulb")}</div>
        <h1 class="auth-card__title">${t("goodbyeTitle")}</h1>
        <p class="goodbye-card__message">${t("goodbyeMessage")} <span class="sparkle-emoji">✨</span></p>
        <button class="btn btn--primary btn--lg btn--block" id="goodbye-cta">${t("goodbyeCta")}</button>
      </div>
    </div>
  `;

  document.getElementById("goodbye-cta").addEventListener("click", () => {
    setLang(DEFAULT_LANG);
    navigate("welcome");
  });
}

/* ---------- Modal: Generate idea ---------- */

const modalRoot = document.getElementById("modal-root");

function closeModal() {
  modalRoot.innerHTML = "";
  modalRoot.classList.remove("modal-root--open");
}

function openGenerateModal() {
  let lastNiche = "";
  let lastResult = null;

  renderStep1();

  function renderStep1(prefillNiche) {
    modalRoot.innerHTML = `
      <div class="modal-backdrop">
        <div class="modal" role="dialog" aria-modal="true">
          <button class="modal__close" id="modal-close" title="${t("close")}">${icon("close")}</button>
          <div class="modal__spark">${icon("sparkle")}</div>
          <h2>${t("generateModalTitle")}</h2>
          <form id="niche-form" class="form">
            <label class="field">
              <span>${t("nicheLabel")}</span>
              <input type="text" name="niche" required placeholder="${t("nichePlaceholder")}" value="${escapeHtml(prefillNiche || "")}" autofocus />
            </label>
            <p class="form-error" id="modal-error" hidden></p>
            <button type="submit" class="btn btn--primary btn--block btn--lg" id="generate-submit">
              ${icon("sparkle")}${t("generateSubmit")}
            </button>
          </form>
        </div>
      </div>
    `;
    modalRoot.classList.add("modal-root--open");
    bindClose();

    const form = document.getElementById("niche-form");
    form.addEventListener("submit", async (e) => {
      e.preventDefault();
      const fd = new FormData(form);
      const niche = fd.get("niche").trim();
      const errorEl = document.getElementById("modal-error");
      errorEl.hidden = true;
      if (!niche) {
        errorEl.textContent = t("nicheRequired");
        errorEl.hidden = false;
        return;
      }
      lastNiche = niche;
      await doGenerate(niche);
    });
  }

  async function doGenerate(niche) {
    modalRoot.innerHTML = `
      <div class="modal-backdrop">
        <div class="modal modal--loading" role="dialog" aria-modal="true">
          <div class="spark-loader"><span></span><span></span><span></span></div>
          <p>${t("generating")}</p>
        </div>
      </div>
    `;
    modalRoot.classList.add("modal-root--open");

    try {
      const result = await Api.generateIdea(niche, langWordFor(getLang()));
      if (result.title === "ERROR") {
        renderErrorStep(niche, result.description);
        return;
      }
      lastResult = result;
      renderStep2(result);
    } catch (err) {
      renderErrorStep(niche, apiErrorMessage(err, "genericGenerateError"));
    }
  }

  function renderErrorStep(niche, message) {
    modalRoot.innerHTML = `
      <div class="modal-backdrop">
        <div class="modal" role="dialog" aria-modal="true">
          <button class="modal__close" id="modal-close" title="${t("close")}">${icon("close")}</button>
          <div class="modal__spark modal__spark--error">${icon("alert")}</div>
          <h2>${t("aiErrorTitle")}</h2>
          <p class="modal__error-desc">${escapeHtml(message)}</p>
          <button class="btn btn--primary btn--block" id="try-again">${t("generateAnother")}</button>
        </div>
      </div>
    `;
    modalRoot.classList.add("modal-root--open");
    bindClose();
    document.getElementById("try-again").addEventListener("click", () => renderStep1(niche));
  }

  function renderStep2(result) {
    modalRoot.innerHTML = `
      <div class="modal-backdrop">
        <div class="modal modal--result" role="dialog" aria-modal="true">
          <button class="modal__close" id="modal-close" title="${t("close")}">${icon("close")}</button>
          <span class="chip chip--accent">${escapeHtml(result.niche)}</span>
          <h2 class="modal__result-title">${escapeHtml(result.title)}</h2>
          <div class="modal__result-body">
            <span class="field-label">${t("resultDescriptionLabel")}</span>
            <p>${escapeHtml(result.description)}</p>
          </div>
          <label class="field">
            <span>${t("observationLabel")}</span>
            <textarea id="observation-input" rows="3" placeholder="${t("observationPlaceholder")}"></textarea>
          </label>
          <p class="form-error" id="modal-error" hidden></p>
          <div class="modal__actions">
            <button class="btn btn--primary" id="save-idea-btn">${icon("sparkle")}${t("saveIdeaButton")}</button>
            <button class="btn btn--ghost" id="regenerate-btn">${t("regenerateButton")}</button>
            <button class="btn btn--ghost" id="discard-btn">${t("discardButton")}</button>
          </div>
        </div>
      </div>
    `;
    modalRoot.classList.add("modal-root--open");
    bindClose();

    document.getElementById("discard-btn").addEventListener("click", closeModal);
    document.getElementById("regenerate-btn").addEventListener("click", () => doGenerate(lastNiche));
    document.getElementById("save-idea-btn").addEventListener("click", async () => {
      const btn = document.getElementById("save-idea-btn");
      const observation = document.getElementById("observation-input").value.trim();
      btn.disabled = true;
      btn.textContent = t("saving");
      try {
        const created = await Api.createIdea({
          title: result.title,
          niche: result.niche,
          description: result.description,
          observation,
        });
        state.ideas = state.ideas ? [created, ...state.ideas] : [created];
        toast(t("ideaSaved"));
        closeModal();
        route();
      } catch (err) {
        const errorEl = document.getElementById("modal-error");
        errorEl.textContent = apiErrorMessage(err);
        errorEl.hidden = false;
        btn.disabled = false;
        btn.innerHTML = `${icon("sparkle")}${t("saveIdeaButton")}`;
      }
    });
  }

  function bindClose() {
    document.getElementById("modal-close")?.addEventListener("click", closeModal);
    modalRoot.querySelector(".modal-backdrop")?.addEventListener("click", (e) => {
      if (e.target.classList.contains("modal-backdrop")) closeModal();
    });
  }
}

/* ---------- Modal: confirm (delete) ---------- */

function openConfirmModal({ title, desc, confirmLabel, onConfirm }) {
  modalRoot.innerHTML = `
    <div class="modal-backdrop">
      <div class="modal modal--confirm" role="dialog" aria-modal="true">
        <div class="modal__spark modal__spark--error">${icon("trash")}</div>
        <h2>${escapeHtml(title)}</h2>
        <p class="modal__error-desc">${escapeHtml(desc)}</p>
        <div class="modal__actions">
          <button class="btn btn--danger" id="confirm-btn">${escapeHtml(confirmLabel)}</button>
          <button class="btn btn--ghost" id="cancel-btn">${t("cancelButton")}</button>
        </div>
      </div>
    </div>
  `;
  modalRoot.classList.add("modal-root--open");
  document.getElementById("cancel-btn").addEventListener("click", closeModal);
  const confirmBtn = document.getElementById("confirm-btn");
  confirmBtn.addEventListener("click", () => onConfirm(confirmBtn));
  modalRoot.querySelector(".modal-backdrop").addEventListener("click", (e) => {
    if (e.target.classList.contains("modal-backdrop")) closeModal();
  });
}

/* ---------- Init ---------- */

document.addEventListener("keydown", (e) => {
  if (e.key === "Escape") closeModal();
});

function init() {
  hydrateSession();
  route();
}

init();
