function EventsByPromoter()
{
	d3.json("http://localhost:8089/rest/event/byPromoter?promoterCode="+gsPromoterCode, function(json) {
		var div = clear();
		div.append("h2").text("Listado de Eventos del Promotor");
		var table = div.append("table");
		table.append("thead").selectAll("th").data(["Nombre", "Tipo Evento", "Artistas", "Fecha", "Lugar", "Promotor"]).enter().append("th").text(function(d){ return d; });
		if (json != null)
		{
			if (json.event instanceof Array)
			{
				var tr = table.selectAll("tr").data(json.event).enter().append("tr");
				tr.append("td").text(function(d) { return d.name; });
				tr.append("td").text(function(d) { return gaEventTypes[d.eventType]; });
				tr.append("td").text(function(d) { return d.artists; });
				tr.append("td").text(function(d) { return d.date; });
				tr.append("td").text(function(d) { return d.place; });			
				tr.append("td").append("a").attr("href", function(d) { return "javascript:EventDetail(" + d.code + ")"; }).text("Modificar");
				tr.append("td").append("a").attr("href", function(d) { return "javascript:EventDelete(" + d.code + ")"; }).text("Eliminar");
			}
			else
			{
				var tr = table.append("tr");
				tr.append("td").text(json.event.name);
				tr.append("td").text(gaEventTypes[json.event.eventType]);
				tr.append("td").text(json.event.artists);
				tr.append("td").text(json.event.date);
				tr.append("td").text(json.event.place);			
				tr.append("td").append("a").attr("href", "javascript:EventDetail(" + json.event.code + ")").text("Modificar");
				tr.append("td").append("a").attr("href", "javascript:EventDelete(" + json.event.code + ")").text("Eliminar");
			}
		}
		div.append("button").attr("onclick", "NewEvent()").text("Agregar");
    });
}

function EventDetail(eventCode)
{
	d3.json("http://localhost:8089/rest/event/details?eventCode=" + eventCode, function(event)
	{
		var div = clear();
		div.append("input").attr("type", "hidden").attr("id", "code").attr("value", event.code);
		div.append("input").attr("type", "hidden").attr("id", "promoterCode").attr("value", event.promoterCode);
		div.append("label").text("Nombre");
		div.append("br");
		div.append("input").attr("type", "text").attr("id", "name").attr("value", event.name);
		div.append("br");
		div.append("label").text("Tipo");
		div.append("br");
		appendSelectFromArray(div, "eventType", gaEventTypes, event.eventType);
		div.append("br");
		div.append("label").text("Artistas");
		div.append("br");
		div.append("input").attr("type", "text").attr("id", "artists").attr("value", event.artists);
		div.append("br");
		div.append("label").text("Fecha");
		div.append("br");
		div.append("input").attr("type", "text").attr("id", "date").attr("value", event.date);
		div.append("br");
		div.append("label").text("Lugar");
		div.append("br");
		div.append("input").attr("type", "text").attr("id", "place").attr("value", event.place);
		div.append("br");
		div.append("br");
		d3.json("http://localhost:8089/rest/event/details/location?eventCode=" + eventCode, function(locations)
		{
			appendLocationTypesTable(div, locations);
			div.append("br");
			div.append("button").attr("onclick", "EventUpdate()").text("Guardar");
		});
    });
}

function appendLocationTypesTable(div, values)
{
	var table = div.append("table");
	table.append("thead").selectAll("th").data(["Nombre", "", "Numerada", "Precio", "Cantidad"]).enter().append("th").text(function(d) { return d; });
	for (a = 0; a < gaLocationTypes.length; a++)
	{
		var tr = table.append("tr");
		tr.append("td").text(gaLocationTypes[a]);
		tr.append("td").append("input").attr("type", "checkbox").attr("id", "chkLocation" + a);
		tr.append("td").append("input").attr("type", "checkbox").attr("id", "chkLocationNumbered" + a);
		tr.append("td").append("input").attr("type", "text").attr("id", "txtLocationPrice" + a);
		tr.append("td").append("input").attr("type", "text").attr("id", "txtLocationQuantity" + a);
	}
	if (values != null && values.location != null)
	{
		if (values.location instanceof Array)
		{
			for (a = 0; a < values.location.length; a++)
			{
				d3.select("#chkLocation" + values.location[a].locationType).attr("checked", "checked");
				if (values.location[a].numbered == "true")
				{
					d3.select("#chkLocationNumbered" + values.location[a].locationType).attr("checked", "checked");
				}
				d3.select("#txtLocationPrice" + values.location[a].locationType).attr("value", values.location[a].price);
				d3.select("#txtLocationQuantity" + values.location[a].locationType).attr("value", values.location[a].quantity);
			}
		}
		else
		{
			d3.select("#chkLocation" + values.location.locationType).attr("checked", "checked");
			if (values.location.numbered == "true")
			{
				d3.select("#chkLocationNumbered" + values.location.locationType).attr("checked", "checked");
			}
			d3.select("#txtLocationPrice" + values.location.locationType).attr("value", values.location.price);
			d3.select("#txtLocationQuantity" + values.location.locationType).attr("value", values.location.quantity);
		}
	}
}

function NewEvent()
{
	var div = clear();
	div.append("input").attr("type", "hidden").attr("id", "promoterCode").attr("value", gsPromoterCode);
	div.append("label").text("Nombre");
	div.append("br");
	div.append("input").attr("type", "text").attr("id", "name");
	div.append("br");
	div.append("label").text("Tipo");
	div.append("br");
	appendSelectFromArray(div, "eventType", gaEventTypes, null);
	div.append("br");
	div.append("label").text("Artistas");
	div.append("br");
	div.append("input").attr("type", "text").attr("id", "artists");
	div.append("br");
	div.append("label").text("Fecha");
	div.append("br");
	div.append("input").attr("type", "text").attr("id", "date");
	div.append("br");
	div.append("label").text("Lugar");
	div.append("br");
	div.append("input").attr("type", "text").attr("id", "place");
	div.append("br");
	div.append("br");
	appendLocationTypesTable(div, null);
	div.append("br");
	div.append("button").attr("onclick", "EventInsert()").text("Guardar");
}

function EventDelete(code)
{
	d3.xhr2("http://localhost:8089/rest/event?code=" + code,"DELETE", function(json) { EventsByPromoter(); });
}

function EventInsert()
{
	var code = Math.floor(Math.random() * 1000);
	var queryString =
		"code=" + code +
		"&name=" + d3.select("#name").property("value") +
		"&eventType=" + d3.select("#eventType").property("value") +
		"&artists=" + d3.select("#artists").property("value") +
		"&date=" + d3.select("#date").property("value") +
		"&place=" + d3.select("#place").property("value") +
		"&promoterCode=" + d3.select("#promoterCode").property("value");
    d3.xhr2("http://localhost:8089/rest/event?" + queryString, "POST", function(json)
	{
		UpdateLocations(code);
		EventsByPromoter();
	});
}

function EventUpdate()
{
	var code = d3.select("#code").property("value");
	var queryString =
		"code=" + code +
		"&name=" + d3.select("#name").property("value") +
		"&eventType=" + d3.select("#eventType").property("value") +
		"&artists=" + d3.select("#artists").property("value") +
		"&date=" + d3.select("#date").property("value") +
		"&place=" + d3.select("#place").property("value") +
		"&promoterCode=" + d3.select("#promoterCode").property("value");
    d3.xhr2("http://localhost:8089/rest/event?" + queryString, "PUT", function(json)
	{
		UpdateLocations(code);
		EventsByPromoter();
	});
}

function UpdateLocations(eventCode)
{
	d3.xhr2("http://localhost:8089/rest/event/details/location?eventCode=" + eventCode, "DELETE", function(json)
	{
		for (a = 0; a < gaLocationTypes.length; a++)
		{
			if (d3.select("#chkLocation" + a).property("checked") == true)
			{
				var queryString = 
					"locationType=" + a +
					"&eventCode=" + eventCode +
					"&price=" + d3.select("#txtLocationPrice" + a).property("value") +
					"&quantity=" + d3.select("#txtLocationQuantity" + a).property("value") +
					"&numbered=" + d3.select("#chkLocationNumbered" + a).property("checked");
				d3.xhr2("http://localhost:8089/rest/event/details/location?" + queryString, "POST", function(json) { });
			}
		}
	});
}