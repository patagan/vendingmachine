import { authHeader } from "../router/auth-header";

const urlBase = "http://localhost:8080/";

export const productService = {
    getProducts,
    addProduct,
    updateProduct,
    deleteProduct
};

function getProducts() {
    const requestOptions = {
      method: "GET",
      headers: authHeader(),
    };
  
    return fetch(`${urlBase}getProducts`, requestOptions)
        .then(handleResponse)
        .then(products => products);
  }

  function addProduct(newProduct) {
    const requestOptions = {
      method: "POST",
      headers: authHeader(),
      body: JSON.stringify(newProduct)
    };
  
    return fetch(`${urlBase}addProduct`, requestOptions)
        .then(handleResponse)
        .then(products => products);
  }

  function updateProduct(toUpdateProduct) {
    const requestOptions = {
      method: "PUT",
      headers: authHeader(),
      body: JSON.stringify(toUpdateProduct)
    };
  
    return fetch(`${urlBase}updateProduct`, requestOptions)
        .then(handleResponse)
        .then(products => products);
  }

  function deleteProduct(id) {
    const requestOptions = {
      method: "DELETE",
      headers: authHeader(),
      body: JSON.stringify(id)
    };
  
    return fetch(`${urlBase}deleteProduct`, requestOptions)
        .then(handleResponse)
        .then(products => products);
  }


  function handleResponse(response) {
    return response.text().then(text => {
      const data = text && JSON.parse(text);
      if (!response.ok) {
        if (response.status === 401) {
          // auto logout if 401 response returned from api
          localStorage.removeItem("user");
          location.reload(true);
        }
  
        const error = (data && data.message) || response.statusText;
        return Promise.reject(error);
      }
  
      return data;
    });
  }