function init() {
    httpGetAsync("/field", onDsInfoReceived)

    setInterval(function (){
        httpGetAsync("/field", onDsInfoReceived)
    }, 2500)
}

function onKillswitchSelected(ip){
//send kill request to /stop with json containing ip to kill

	var request = new XMLHttpRequest();
	request.open('POST', '/stop', true);
	request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
	request.send(JSON.stringify({'ip':ip}));
	
	alert("this is where we'd kill off " + ip);
}

function onDsInfoReceived(responseText){
    var connArray = JSON.parse(responseText);
    var tableHTML = "<tr><th>IP</th><th>Ping</th></tr>";

    for (var i = 0; i < connArray.length; i++) {
        var conn = connArray[i];
        var ip = conn.ip;
        var ping = conn.ping;

        tableHTML += "<tr><th>" + ip + "</th><th>" + ping + " ms</th>";
        tableHTML += "<th class='stop-container' onclick=\"onKillswitchSelected('" + ip + "')\"><button id='stop'>Stop</button></th></tr>";
    }

    document.getElementById("dsConnTable").innerHTML = tableHTML;
}

function httpGetAsync(theUrl, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200){
            console.log("Received GET (" + theUrl +") response: " + xmlHttp.responseText)
            callback(xmlHttp.responseText);
        }else if (xmlHttp.readyState == 4) {
            console.log("Error during request: " + url + ". Response: " + xmlHttp.status)
        }
    }

    xmlHttp.open("GET", theUrl, true);
    xmlHttp.send(null);
}
