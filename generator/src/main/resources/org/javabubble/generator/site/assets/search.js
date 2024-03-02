document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.getElementById("search-input");
    const rows = document.querySelectorAll("tbody tr");

    const onSearchInputKeyPress = () => {
        const searchTextLower = searchInput.value.toLowerCase();
        rows.forEach((row) => {
            const namesLower = row.children[0].textContent.toLowerCase();
            const handlesLower = row.children[1].textContent.toLowerCase();
            const rowText = namesLower + "\n" + handlesLower;
            if (rowText.includes(searchTextLower)) {
                row.style.display = "table-row";
            } else {
                row.style.display = "none";
            }
        });
    };

    searchInput.addEventListener(
        "keyup",
        debounce((e) => onSearchInputKeyPress())
    );

    function debounce(func, timeout = 300) {
        let timer;
        return (...args) => {
            clearTimeout(timer);
            timer = setTimeout(() => {
                func.apply(this, args);
            }, timeout);
        };
    }
});
