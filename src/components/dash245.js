ui.asides = {};
ui.asides.connections = [];
ui.asides.element = document.querySelector("aside");

NetworkTables.addKeyListener('/SmartDashboard/added_object', (key, value) => {
    ui.asides.connections.push({title: value});
    console.log("Added " + value + " to the Dashboard245");

    ui.asides.element.innerHTML += `<div class="${value}"><p class="header">${value}:</p><br><p class="content">Unknown</p></div>`;

    NetworkTables.addKeyListener("/SmartDashboard/" + value, (title, val) => {

        let elem = document.querySelector(`.${value}`);;
        elem.innerHTML = `<p class="header">${value}:</p><br><p class="content">${val}</p>`;

    });

});

NetworkTables.putValue("d245_ready", true);