import { authHeader } from "@/router";

const urlBase = "http://localhost:8080/";

export const userService = {
  login,
  logout,
  register,
  topUp,
  resetDeposit,
  buyProduct,
  getUser
};

function login(username, password) {
  const requestOptions = {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password })
  };

  return fetch(`${urlBase}login`, requestOptions)
    .then(handleResponse)
    .then(user => {
      // login successful if there's a jwt token in the response
      if (user.token) {
        // store user details and jwt token in local storage to keep user logged in between page refreshes
        localStorage.setItem("user", JSON.stringify(user));
      }

      return user;
    });
}

function getUser(userId) {
  const requestOptions = {
    method: "GET",
    headers: authHeader()
  };

  return fetch(`${urlBase}myUser/`+userId, requestOptions)
    .then(handleResponse)
    .then(user => {
      // login successful if there's a jwt token in the response
      if (user.token) {
        // store user details and jwt token in local storage to keep user logged in between page refreshes
        localStorage.setItem("user", JSON.stringify(user));
      }

      return user;
    });
}

function logout() {
  // remove user from local storage to log user out
  localStorage.removeItem("user");
}

function register(user) {
  const requestOptions = {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(user)
  };

  return fetch(`${urlBase}user`, requestOptions).then(
    handleResponse
  );
}

function topUp(budget) {
  const requestOptions = {
    method: "PUT",
    headers: authHeader(),
    body: JSON.stringify(budget)
  };

  return fetch(`${urlBase}deposit`, requestOptions).then(
    handleResponse
  );
}

function resetDeposit() {
  const requestOptions = {
    method: "PUT",
    headers: authHeader()
  };

  return fetch(`${urlBase}reset`, requestOptions).then(
    handleResponse
  );
}

function buyProduct(buyProduct) {
  const requestOptions = {
    method: "POST",
    headers: authHeader(),
    body: JSON.stringify(buyProduct)
  };

  return fetch(`${urlBase}buyProduct`, requestOptions).then(
    handleResponse
  );
}

function handleResponse(response) {
  return response.text().then(text => {
    const data = text && JSON.parse(text);
    if (!response.ok) {
      if (response.status === 401) {
        // auto logout if 401 response returned from api
        logout();
        location.reload(true);
      }

      const error = (data && data.message) || response.statusText;
      return Promise.reject(error);
    }

    return data;
  });
}