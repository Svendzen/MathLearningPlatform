import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],   // Tells Vite to use React's special features
  server: {
    port: 3000,         // Which port the dev server runs on
    proxy: {            // How to handle API requests during development
      '/api/v1': {
        target: 'http://localhost:8000',  // Where to send API requests
        changeOrigin: true                // Handles CORS headers for me
      }
    }
  }
})
