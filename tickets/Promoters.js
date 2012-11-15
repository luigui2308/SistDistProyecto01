function promotersList()
{
	d3.json("http://localhost:8089/rest/promoter", function(json)
	{
		var content = d3.select("#content");
		content.selectAll("div").remove();
		var div = content.append("div");
		div.append("h2").text("Listado de promotores");
		var table = div.append("table");
		table.append("thead").selectAll("th").data(["Nombre", "Dirección", "Telefono", "Banco", "Comisión", "Usuario", "", ""]).enter().append("th").text(function(d) { return d; });
		if (json != null)
		{
			if (json.promoter instanceof Array)
			{
				var tr = table.selectAll("tr").data(json.promoter).enter().append("tr");
				tr.append("td").text(function(d) { return d.name; });
				tr.append("td").text(function(d) { return d.address; });
				tr.append("td").text(function(d) { return d.telephone; });
				tr.append("td").text(function(d) { return d.bank; });
				tr.append("td").text(function(d) { return d.commision; });
				tr.append("td").text(function(d) { return d.username; });
				tr.append("td").append("a").attr("href", function(d) { return "javascript:promotersDetail(" + d.code + ")"; }).text("Modificar");
				tr.append("td").append("a").attr("href", function(d) { return "javascript:promotersDelete(" + d.code + ")"; }).text("Eliminar");
			}
			else
			{
				var tr = table.append("tr");
				tr.append("td").text(json.promoter.name);
				tr.append("td").text(json.promoter.address);
				tr.append("td").text(json.promoter.telephone);
				tr.append("td").text(json.promoter.bank);
				tr.append("td").text(json.promoter.commision);
				tr.append("td").text(json.promoter.username);
				tr.append("td").append("a").attr("href", "javascript:promotersDetail(" + json.promoter.code + ")").text("Modificar");
				tr.append("td").append("a").attr("href", "javascript:promotersDelete(" + json.promoter.code + ")").text("Eliminar");
			}
		}
		div.append("button").attr("onclick", "promotersNew()").text("Nuevo");
    });
}

function promotersDetail(code)
{
	d3.json("http://localhost:8089/rest/promoter/detail?code=" + code, function(promoter)
	{
		d3.json("http://localhost:8089/rest/promoter/detail/user?username=" + promoter.username, function(user)
		{
			var content = d3.select("#content");
			content.selectAll("div").remove();
			var div = content.append("div");
			div.append("input").attr("type", "hidden").attr("id", "code").attr("value", promoter.code);
			div.append("label").text("Nombre");
			div.append("br");
			div.append("input").attr("type", "text").attr("id", "name").attr("value", promoter.name);
			div.append("label").text("Direccion");
			div.append("br");
			div.append("input").attr("type", "text").attr("id", "address").attr("value", promoter.address);
			div.append("label").text("Telefono");
			div.append("br");
			div.append("input").attr("type", "text").attr("id", "telephone").attr("value", promoter.telephone);
			div.append("label").text("Banco");
			div.append("br");
			div.append("input").attr("type", "text").attr("id", "bank").attr("value", promoter.bank);
			div.append("label").text("Comision");
			div.append("br");
			div.append("input").attr("type", "text").attr("id", "commision").attr("value", promoter.commision);
			div.append("label").text("Nombre de usuario");
			div.append("br");
			div.append("input").attr("type", "text").attr("id", "username").attr("readonly", "readonly").attr("value", user.username);
			div.append("label").text("Contraseña");
			div.append("br");
			div.append("input").attr("type", "password").attr("id", "password").attr("value", user.password);
			div.append("br");
			div.append("button").text("Guardar").attr("onclick", "promotersUpdate()");
		});
	});
}

function promotersUpdate()
{
	var queryString = "code=" + d3.select("#code").property("value")
		+ "&name=" + d3.select("#name").property("value")
		+ "&address=" + d3.select("#address").property("value")
		+ "&telephone=" + d3.select("#telephone").property("value")
		+ "&bank=" + d3.select("#bank").property("value")
		+ "&commision=" + d3.select("#commision").property("value")
		+ "&username=" + d3.select("#username").property("value")
		+ "&password=" + d3.select("#password").property("value");
	d3.xhr2("http://localhost:8089/rest/promoter?" + queryString, "PUT", function(json){ promotersList(); });
}

function promotersNew()
{
	var content = d3.select("#content");
	content.selectAll("div").remove();
	var div = content.append("div");
	div.append("label").text("Nombre");
	div.append("br");
	div.append("input").attr("type", "text").attr("id", "name");
	div.append("label").text("Direccion");
	div.append("br");
	div.append("input").attr("type", "text").attr("id", "address");
	div.append("label").text("Telefono");
	div.append("br");
	div.append("input").attr("type", "text").attr("id", "telephone");
	div.append("label").text("Banco");
	div.append("br");
	div.append("input").attr("type", "text").attr("id", "bank");
	div.append("label").text("Comision");
	div.append("br");
	div.append("input").attr("type", "text").attr("id", "commision");
	div.append("label").text("Nombre de usuario");
	div.append("br");
	div.append("input").attr("type", "text").attr("id", "username");
	div.append("label").text("Contraseña");
	div.append("br");
	div.append("input").attr("type", "password").attr("id", "password");
	div.append("br");
	div.append("button").text("Guardar").attr("onclick", "promotersInsert()");
}

function promotersInsert()
{
	var queryString = "name=" + d3.select("#name").property("value")
		+ "&address=" + d3.select("#address").property("value")
		+ "&telephone=" + d3.select("#telephone").property("value")
		+ "&bank=" + d3.select("#bank").property("value")
		+ "&commision=" + d3.select("#commision").property("value")
		+ "&username=" + d3.select("#username").property("value")
		+ "&password=" + d3.select("#password").property("value");
	d3.xhr2("http://localhost:8089/rest/promoter?" + queryString, "POST", function(json) { promotersList(); });
	
}

function promotersDelete(code)
{
	var queryString = "code=" + code;
	d3.xhr2("http://localhost:8089/rest/promoter?" + queryString, "DELETE", function(json) { promotersList(); });
}