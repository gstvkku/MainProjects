import routes from "./routes.js";

async function launchController(controllerName) {
  try {
    const module = await import(`./controller/${controllerName}.js`);
    module.init();
  } catch (error) {
    console.error(error);
  }
}

function setAnchorEventListener() {
    document.body.addEventListener('click', (e) => {
        const anchor = e.target.closest('a');
        
        if (anchor && anchor.href && anchor.host === location.host) {
            if (anchor.hash) {
                e.preventDefault();
                const targetId = anchor.hash.substring(1); // get the part after '#'
                const targetElement = document.getElementById(targetId);
                if (targetElement) {
                    targetElement.scrollIntoView({ behavior: 'smooth' });
                }
            } else {
                e.preventDefault();
                navigate(anchor.pathname);
            }
        }
    });
}

function setCurrentRoute({ path, controller }) {
  routes.currentPath.path = path;
  routes.currentPath.controller = controller;
}

function handlePopState({ state }) {
  const route = state || routes.home;
  setCurrentRoute(route);
  launchController(route.controller);
}

function navigate(path, firstLoad = false) {
  if (path === routes.currentPath.path || path.includes('#')) {
    return;
  }

  const routeKey = Object.keys(routes).find((key) => routes[key].path === path);
  let route = routes[routeKey] || routes.home;

  const isLoggedIn = localStorage.getItem("userLoggedIn") === "true";
  const protectedRoutes = ["/history", "/request", "/profile"];

  if (protectedRoutes.includes(route.path) && !isLoggedIn) {
    route = routes.first;
  }

  setCurrentRoute(route);

  firstLoad
    ? history.replaceState(route, "", route.path)
    : history.pushState(route, "", route.path);

  launchController(route.controller);
}

function setLogoutListener() {
    document.body.addEventListener('click', (e) => {
        const logoutLink = e.target.closest('a.logout-link');
        if (logoutLink) {
            e.preventDefault();
            localStorage.removeItem("userLoggedIn");
            window.location.href = '/';
        }
    });
}

function init() {
  const isLoggedIn = localStorage.getItem("userLoggedIn") === "true";

  const initialPath = isLoggedIn ? routes.home.path : routes.first.path;

  navigate(initialPath, true);

  addEventListener("popstate", handlePopState);
  setAnchorEventListener();
  setLogoutListener();
}

export default { init };
