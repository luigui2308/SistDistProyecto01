var gbLogged = false;
var gbPromoter = false;
var gbAdministrator = false;
var gsPromoterCode = -1;
var gsUsername = "";

var gaEventTypes = ["Concierto", "Recital", "Obra de teatro", "Danza", "Otro"];
var gaLocationTypes = ["Sol", "Sombre", "Platea", "Palco", "Preferencial", "VIP"];

function appendSelectFromArray(div, id, values, selected)
{
	var vSelect = div.append("select");
	vSelect.attr("id", id);
	for (a = 0; a < values.length; a++)
	{
		vSelect.append("option").attr("value", a).text(values[a]);
	}
	if (selected != null)
	{
		vSelect.select("option[value=\"" + selected + "\"]").attr("selected", "selected");
	}
}

function firstRun()
{
	setNavigationTabs();
	EventsList();
}

function setNavigationTabs()
{
	var navigation = d3.select("#navigation");
	navigation.selectAll("ul").remove();
	var ul = navigation.append("ul");
	ul.append("li").append("a").attr("href", "javascript:EventsList()").text("Compra");
	if (gbAdministrator)
	{
		ul.append("li").append("a").attr("href", "javascript:promotersList()").text("Promotores");
	}
	if (gbPromoter)
	{
		ul.append("li").append("a").attr("href", "javascript:EventsByPromoter()").text("Eventos");
	}
	if (gsUsername != "")
	{
		ul.append("li").append("a").attr("href", "javascript:logout()").text("Salir");
	}
	else
	{
		ul.append("li").append("a").attr("href", "javascript:loginForm()").text("Entrar");
	}
}

function loginForm()
{
	var content = d3.select("#content");
	content.selectAll("div").remove();
	var div = content.append("div");
	div.append("label").text("Nombre");
	div.append("br");
	div.append("input").attr("type", "text").attr("id", "username");
	div.append("label").text("Contraseña");
	div.append("br");
	div.append("input").attr("type", "password").attr("id", "password");
	div.append("button").attr("onclick", "login()").text("Guardar");
}

function login()
{
	var username = d3.select("#username").property("value");
	var password = d3.select("#password").property("value");
	d3.json("http://localhost:8089/rest/user/credentials?username=" + username + "&password=" +  password, function(json)
	{
		if (json != null)
		{
			gsUsername = username;
			d3.json("http://localhost:8089/rest/user/role?username=" + username + "&role=1", function(json)
			{
				if (json!= null)
				{
					gbAdministrator = true;
				}
				d3.json("http://localhost:8089/rest/user/role?username=" + username + "&role=2", function(json)
				{
					if (json != null)
					{
						gbPromoter = true;
					}
					d3.json("http://localhost:8089/rest/promoter/byUsername?username=" + username, function(json)
					{
						if (json != null)
						{
							gsPromoterCode = json.code;
						}
						firstRun();
					});
				});
			});
		}
	});
}

function logout()
{
	gbPromoter = false;
	gbAdministrator = false;
	gsUsername = "";
	gsPromoterCode = -1;
	firstRun();
}

d3.xhr2 = function(url, method, callback)
{
	var req = new XMLHttpRequest();
	req.open(method, url, true);
	req.onreadystatechange = function()
	{
		if (req.readyState === 4)
		{
			var s = req.status;
			callback(s >= 200 && s < 300 || s === 304 ? req : null);
		}
    };
	req.send();
};

function clear()
{
	var content = d3.select("#content");
	content.selectAll("div").remove();
	return content.append("div");
}