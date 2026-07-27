const API_URL = "http://localhost:8081/energy";

let editId = null;
let totalUsage = 0;
let totalCost = 0;
let usageChart = null;
let monthlyChart = null;
const form = document.getElementById("energyForm");
const message = document.getElementById("message");

window.onload = function () {
    loadRecords();
};

async function loadRecords() {

    try {

        const response = await fetch(API_URL);
        const result = await response.json();

        const tableBody = document.querySelector("#recordsTable tbody");
        tableBody.innerHTML = "";

        totalUsage = 0;
        totalCost = 0;

        result.data.forEach(record => {

            totalUsage += record.unitsConsumed;
            totalCost += record.cost;

            addRow(record);

        });

        document.getElementById("totalUsage").innerText =
            totalUsage + " kWh";

        document.getElementById("totalCost").innerText =
            "₹" + totalCost;

        let efficiency =
            totalCost === 0 ? 0 :
            ((totalUsage / totalCost) * 100).toFixed(1);

        document.getElementById("efficiency").innerText =
            efficiency + "%";
drawUsageChart(result.data);
updateAnalytics(result.data);
drawMonthlyChart(result.data);
    } catch (error) {

        console.log(error);

    }

}

function addRow(record) {

    const tableBody = document.querySelector("#recordsTable tbody");

    const newRow = tableBody.insertRow();

    newRow.insertCell(0).innerText = record.deviceName;
    newRow.insertCell(1).innerText = record.unitsConsumed;
    newRow.insertCell(2).innerText = "₹" + record.cost;
    newRow.insertCell(3).innerText = record.date;

    const actionCell = newRow.insertCell(4);

    const editButton = document.createElement("button");
    editButton.innerText = "Edit";
    editButton.classList.add("edit-btn");
    actionCell.appendChild(editButton);

    editButton.addEventListener("click", function () {

        document.getElementById("deviceName").value =
            record.deviceName;

        document.getElementById("energyUsage").value =
            record.unitsConsumed;

        

        document.getElementById("date").value =
            record.date;

        editId = record.id;

    });

    const deleteButton = document.createElement("button");
    deleteButton.innerText = "Delete";
    deleteButton.classList.add("delete-btn");
    actionCell.appendChild(deleteButton);

    deleteButton.addEventListener("click", async function () {

        await fetch(API_URL + "/" + record.id, {
            method: "DELETE"
        });

        loadRecords();

    });

}
form.addEventListener("submit", async function (event) {

    event.preventDefault();

    const data = {
        deviceName: document.getElementById("deviceName").value,
        unitsConsumed: Number(document.getElementById("energyUsage").value),
        
        date: document.getElementById("date").value
    };

    try {

        let response;

        if (editId == null) {

            response = await fetch(API_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });

        } else {

            response = await fetch(API_URL + "/" + editId, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    id: editId,
                    deviceName: data.deviceName,
                    unitsConsumed: data.unitsConsumed,
                    
                    date: data.date
                })
            });

            editId = null;
        }

        if (response.ok) {

            message.style.color = "green";
            message.innerText = "Record Saved Successfully!";

            form.reset();

            loadRecords();

        } else {

            message.style.color = "red";
            message.innerText = "Operation Failed!";

        }

    } catch (error) {

        console.log(error);

        message.style.color = "red";
        message.innerText = "Server Error!";

    }

});
function drawUsageChart(records) {

    const labels = records.map(record => record.deviceName);
    const usage = records.map(record => record.unitsConsumed);

    const ctx = document.getElementById("usageChart").getContext("2d");
const gradient = ctx.createLinearGradient(0, 0, 0, 400);
gradient.addColorStop(0, "rgba(52,152,219,0.6)");
gradient.addColorStop(1, "rgba(52,152,219,0)");
    if (usageChart) {
        usageChart.destroy();
    }
usageChart = new Chart(ctx, {
    type: "line",
    data: {
        labels,
       datasets: [{
    label: "Energy Usage (kWh)",
    data: usage,
    borderColor: "#3498db",
    backgroundColor: gradient,
    borderWidth: 4,
    fill: true,
    tension: 0.4,
    pointRadius: 6,
    pointHoverRadius: 8,
    pointBackgroundColor: "#3498db",
    pointBorderColor: "#ffffff",
    pointBorderWidth: 2
}]
    },

    options: {
    responsive: true,
    plugins: {
        legend: {
            display: true
        },
        title: {
            display: true,
            text: "Energy Usage Analysis",
            font: {
                size: 20
            }
        }
    },
    animation: {
        duration: 2000
    },
    scales: {
        y: {
            beginAtZero: true
        }
    }
}
});
}
function updateAnalytics(records) {
console.log(records);
    if (records.length === 0) {
        document.getElementById("highestUsage").innerText = "0 kWh";
        document.getElementById("averageCost").innerText = "₹0";
        document.getElementById("totalDevices").innerText = "0";
        return;
    }

    // Highest Usage
    let highest = Math.max(...records.map(r =>(r.unitsConsumed)));
    document.getElementById("highestUsage").innerText = highest + " kWh";

    // Average Cost
    let totalCost = records.reduce((sum, r) => sum +( r.cost), 0);
    let avgCost = (totalCost / records.length).toFixed(2);
    document.getElementById("averageCost").innerText = "₹" + avgCost;

    // Total Devices
    document.getElementById("totalDevices").innerText = records.length;
}



const themeBtn = document.getElementById("themeBtn");

themeBtn.addEventListener("click", function () {

    document.body.classList.toggle("dark-mode");

    if (document.body.classList.contains("dark-mode")) {
        themeBtn.innerText = "☀️ Light Mode";
    } else {
        themeBtn.innerText = "🌙 Dark Mode";
    }

});
async function loadEnergySuggestion() {
    try {
        const response = await fetch(window.location.origin + "/energy/suggestion/45");

        if (!response.ok) {
            throw new Error("HTTP Error: " + response.status);
        }

        const suggestion = await response.text();

        document.getElementById("energySuggestion").innerText = suggestion;

    } catch (error) {
        console.error(error);
        document.getElementById("energySuggestion").innerText = error.message;
    }
}

loadEnergySuggestion();