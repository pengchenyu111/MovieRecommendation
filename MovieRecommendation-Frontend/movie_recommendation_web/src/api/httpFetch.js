import axios from 'axios';

const httpFetch = axios.create({
  baseURL: 'http://127.0.0.1:8090/api/recommendation/',
  timeout: 30000,
});

export default httpFetch
