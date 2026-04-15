import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_URL,
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const authAPI = {
  login: (credentials) => api.post('/auth/login', credentials),
  register: (userData) => api.post('/auth/register', userData),
};

export const studentAPI = {
  getAll: () => api.get('/students'),
  getById: (id) => api.get(`/students/${id}`),
  create: (data) => api.post('/students', data),
  update: (id, data) => api.put(`/students/${id}`, data),
  delete: (id) => api.delete(`/students/${id}`),
};

export const teacherAPI = {
  getAll: () => api.get('/teachers'),
  create: (data) => api.post('/teachers', data),
  update: (id, data) => api.put(`/teachers/${id}`, data),
  delete: (id) => api.delete(`/teachers/${id}`),
};

export const classAPI = {
  getAll: () => api.get('/classes'),
  create: (data) => api.post('/classes', data),
};

export const feeAPI = {
  getStructures: () => api.get('/feestructures'),
  getPayments: () => api.get('/feepayments'),
  makePayment: (data) => api.post('/feepayments', data),
};

export const attendanceAPI = {
  getRecords: () => api.get('/attendance'),
  markAttendance: (data) => api.post('/attendance', data),
};

export const examAPI = {
  getExams: () => api.get('/exams'),
  getMarks: () => api.get('/marks'),
};

export default api;
