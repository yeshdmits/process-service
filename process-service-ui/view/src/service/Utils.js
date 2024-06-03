function formatDate(inputDate) {
    const date = new Date(inputDate);

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${day}-${month}-${year} ${hours}:${minutes}`;
}

function formatDateFilter(inputDate) {
    const date = new Date(inputDate);

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();

    return `${day}-${month}-${year}`;
}

function formatTimestamp(timestamp) {
    const months = [
        'January', 'February', 'March', 'April', 'May', 'June',
        'July', 'August', 'September', 'October', 'November', 'December'
    ];

    const [year, month, day, hour, minutes, seconds] = timestamp;

    const monthName = months[parseInt(month) - 1]; // Month is 0-based in JavaScript Date object

    const pad = (num) => (num < 10 ? '0' : '') + num;

    const formattedDate = `${pad(day)} ${monthName} ${year} at ${pad(hour)}:${pad(minutes)}:${pad(seconds)}`;

    return formattedDate;
}

const formatAuthDate = (date) => {
    const months = [
        'January', 'February', 'March', 'April', 'May', 'June',
        'July', 'August', 'September', 'October', 'November', 'December'
    ];

    const day = date.getDate();
    const month = date.getMonth();
    const year = date.getFullYear();
    const hours = date.getHours();
    const minutes = date.getMinutes();
    const seconds = date.getSeconds();

    const pad = (num) => (num < 10 ? '0' : '') + num;

    const monthName = months[month];

    const formattedDate = `${pad(day)} ${monthName} ${year} at ${pad(hours)}:${pad(minutes)}:${pad(seconds)}`;

    return formattedDate;
};

export { formatDate, formatTimestamp, formatAuthDate, formatDateFilter }