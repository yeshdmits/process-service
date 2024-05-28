/** @type {import('tailwindcss').Config} */
module.exports = {
    content: ["./src/**/*.{html,jsx}", "./src/**/**/*.{html,jsx}"],
    theme: {
        extend: {
            screens: {
                'sm': '300px',
                'md': '600px'
            },
        },
    },
    plugins: [],
}

