function formatDate(inputDate) {
    const date = new Date(inputDate);
    let localDate = toLocalDate(date)

    const day = String(localDate.getDate()).padStart(2, '0');
    const month = String(localDate.getMonth() + 1).padStart(2, '0');
    const year = localDate.getFullYear();
    const hours = String(localDate.getHours()).padStart(2, '0');
    const minutes = String(localDate.getMinutes()).padStart(2, '0');

    return `${day}-${month}-${year} ${hours}:${minutes}`;
}

function formatDateFilter(inputDate) {
    const date = new Date(inputDate);
    let localDate = toLocalDate(date)

    const day = String(localDate.getDate()).padStart(2, '0');
    const month = String(localDate.getMonth() + 1).padStart(2, '0');
    const year = localDate.getFullYear();

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

    return `${pad(day)} ${monthName} ${year} at ${pad(hour)}:${pad(minutes)}:${pad(seconds)}`;
}

const toLocalDate = (date) => {
    return new Date(date.getTime() + 2 * 60 * 60 * 1000);
}

const formatAuthDate = (date) => {
    let localDate = toLocalDate(date)
    const months = [
        'January', 'February', 'March', 'April', 'May', 'June',
        'July', 'August', 'September', 'October', 'November', 'December'
    ];

    const day = localDate.getDate();
    const month = localDate.getMonth();
    const year = localDate.getFullYear();
    const hours = localDate.getHours();
    const minutes = localDate.getMinutes();
    const seconds = localDate.getSeconds();

    const pad = (num) => (num < 10 ? '0' : '') + num;

    const monthName = months[month];

    return `${pad(day)} ${monthName} ${year} at ${pad(hours)}:${pad(minutes)}:${pad(seconds)}`;
};

export { formatDate, formatTimestamp, formatAuthDate, formatDateFilter }