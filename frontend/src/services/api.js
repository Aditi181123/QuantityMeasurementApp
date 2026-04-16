/**
 * API Service
 * Handles all HTTP requests to the microservices via API Gateway
 */

const API_BASE = 'http://localhost:8080';

// Get auth token from localStorage
const getAuthToken = () => localStorage.getItem('token');
const getUserId = () => localStorage.getItem('userId');

// Helper to make authenticated requests
const authFetch = async (url, options = {}) => {
  const token = getAuthToken();
  const headers = {
    'Content-Type': 'application/json',
    ...options.headers,
  };

  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const userId = getUserId();
  if (userId) {
    headers['X-User-Id'] = userId;
  }

  console.log(`API Request: ${options.method || 'GET'} ${API_BASE}${url}`);
  console.log('Headers:', headers);
  console.log('Body:', options.body);

  try {
    const response = await fetch(`${API_BASE}${url}`, {
      method: options.method || 'GET',
      headers,
      body: options.body,
    });

    console.log('Response status:', response.status);

    const text = await response.text();
    console.log('Response text:', text);

    if (!response.ok) {
      throw new Error(text || `Request failed with status ${response.status}`);
    }

    try {
      return JSON.parse(text);
    } catch {
      return text;
    }
  } catch (error) {
    console.error('Fetch error:', error);
    if (error.name === 'TypeError' && error.message.includes('fetch')) {
      throw new Error('Cannot connect to server. Please ensure all backend services are running on ports 8080, 8081, 8082.');
    }
    throw error;
  }
};


export const authService = {
  async login(username, password) {
    return authFetch('/api/auth/login', {
      method: 'POST',
      body: JSON.stringify({ username, password }),
    });
  },

  async signup(username, email, password) {
    return authFetch('/api/auth/signup', {
      method: 'POST',
      body: JSON.stringify({ username, email, password }),
    });
  },

  async googleAuth(googleUser) {
    return authFetch('/api/auth/google', {
      method: 'POST',
      body: JSON.stringify(googleUser),
    });
  },
};


export const qmaService = {
  async convert(value, from, to) {
    return authFetch('/api/qma/convert', {
      method: 'POST',
      body: JSON.stringify({ value, from, to }),
    });
  },

  async compare(firstValue, firstUnit, secondValue, secondUnit) {
    return authFetch('/api/qma/compare', {
      method: 'POST',
      body: JSON.stringify({
        firstValue,
        firstUnit,
        secondValue,
        secondUnit,
      }),
    });
  },

  async add(firstValue, firstUnit, secondValue, secondUnit) {
    return authFetch('/api/qma/add', {
      method: 'POST',
      body: JSON.stringify({
        firstValue,
        firstUnit,
        secondValue,
        secondUnit,
      }),
    });
  },

  async subtract(firstValue, firstUnit, secondValue, secondUnit) {
    return authFetch('/api/qma/subtract', {
      method: 'POST',
      body: JSON.stringify({
        firstValue,
        firstUnit,
        secondValue,
        secondUnit,
      }),
    });
  },

  async multiply(firstValue, firstUnit, secondValue, secondUnit) {
    return authFetch('/api/qma/multiply', {
      method: 'POST',
      body: JSON.stringify({
        firstValue,
        firstUnit,
        secondValue,
        secondUnit,
      }),
    });
  },

  async divide(firstValue, firstUnit, secondValue, secondUnit) {
    return authFetch('/api/qma/divide', {
      method: 'POST',
      body: JSON.stringify({
        firstValue,
        firstUnit,
        secondValue,
        secondUnit,
      }),
    });
  },

  async getHistory() {
    return authFetch('/api/qma/history');
  },
};


export const UNITS = {
  LENGTH: ['FEET', 'INCH', 'YARD', 'CENTIMETER', 'METER'],
  WEIGHT: ['GRAM', 'KILOGRAM'],
  VOLUME: ['LITRE', 'MILLILITRE', 'GALLON'],
  TEMPERATURE: ['CELSIUS', 'FAHRENHEIT'],
};

export const MEASUREMENT_TYPES = ['LENGTH', 'WEIGHT', 'VOLUME', 'TEMPERATURE'];
