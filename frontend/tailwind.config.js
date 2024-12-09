/** @type {import('tailwindcss').Config} */
export default {
  content: [
      "./index.html",
      "./src/**/*.{js,ts,jsx,tsx}"
  ],
  theme: {
    extend: {
      // Custom colors that might be useful for a math learning platform
      colors: {
        primary: {
          50: '#f0f9ff',
          500: '#0ea5e9',
          700: '#0369a1',
        },
        success: {
          50: '#f0fdf4',
          500: '#22c55e',
        },
        error: {
          50: '#fef2f2',
          500: '#ef4444',
        },
      },
      // Custom spacing for mathematical content
      spacing: {
        'equation': '2.5rem'
      }
    },
  },
  plugins: [],
}

