function toggleInput() {
    const type = document.getElementById("filterType").value;
    const box = document.getElementById("inputBox");
    const form = document.getElementById("filterForm");

    box.innerHTML = ""; // clear previous inputs

    if (type === "product") {
        form.action = "/logs/filter";
        box.innerHTML = `
            <input type="number" name="productId"
                   class="form-control form-control-sm"
                   placeholder="Enter Product ID" required>
        `;
    } else if (type === "date") {
        form.action = "/logs/filter";
        box.innerHTML = `
            <input type="date" name="date" class="form-control form-control-sm" required>
        `;
    } else {
        form.action = "/logs"; // default all logs
        box.innerHTML = "";
    }

    attachEnterSubmit(); // attach Enter listener
}

function attachEnterSubmit() {
    const input = document.querySelector("#inputBox input");
    const form = document.getElementById("filterForm");

    if (input) {
        input.addEventListener("keydown", function (e) {
            if (e.key === "Enter") {
                e.preventDefault();
                form.submit();
            }
        });
        input.focus();
    }
}

document.getElementById("filterType").addEventListener("change", toggleInput);
toggleInput();