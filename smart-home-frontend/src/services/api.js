import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api"; // Backend URL

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// User Authentication
export const login = async (credentials) => api.post("/users/login", credentials);
export const register = async (userData) => api.post("/users/register", userData);
export const getUser = async (username) => api.get(`/users/${username}`);

// Device Management
export const getDevices = async () => api.get("/devices");
export const addDevice = async (deviceData) => api.post("/devices/create", deviceData);
export const updateDevice = async (deviceData) => api.put("/devices/update", deviceData);
export const deleteDevice = async (id) => api.delete(`/devices/delete/${id}`);

// Energy Usage
export const getEnergyUsage = async () => api.get("/energy-usage");

export default api;

