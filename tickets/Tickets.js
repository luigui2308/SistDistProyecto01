function EventsList()
{
	d3.json("http://localhost:8089/rest/event", function(json) {
		var div = clear();
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
				tr.append("td").append("a").attr("href", function(d) { return "javascript:ticketsNew(" + d.code + ")"; }).text("Comprar");
			}
			else
			{
				var tr = table.append("tr");
				tr.append("td").text(json.event.name);
				tr.append("td").text(gaEventTypes[json.event.eventType]);
				tr.append("td").text(json.event.artists);
				tr.append("td").text(json.event.date);
				tr.append("td").text(json.event.place);			
				tr.append("td").append("a").attr("href", "javascript:ticketsNew(" + json.event.code + ")").text("Comprar");
			}
		}
    });
}

function ticketsNew(eventCode)
{
	d3.json("http://localhost:8089/rest/event/details?eventCode=" + eventCode, function(event)
	{
		var div = clear();
		div.append("h2").text("Compra de ticket para evento " + event.name);
		//--Cliente
		div.append("label").text("Cédula");
		div.append("br");
		div.append("input").attr("type", "text").attr("id", "id");
		div.append("br");
		div.append("label").text("Nombre");
		div.append("br");
		div.append("input").attr("type", "text").attr("id", "name");
		div.append("br");
		div.append("label").text("Dirección");
		div.append("br");
		div.append("input").attr("type", "text").attr("id", "address");
		div.append("br");
		div.append("label").text("Teléfono");
		div.append("br");
		div.append("input").attr("type", "text").attr("id", "telephone");
		div.append("br");
		div.append("label").text("Número de tarjeta");
		div.append("br");
		div.append("input").attr("type", "text").attr("id", "cardnumber");
		div.append("br");
		div.append("label").text("Tipo de tarjeta");
		div.append("br");
		div.append("input").attr("type", "text").attr("id", "cardType");
		div.append("br");
		//--Ticket
		div.append("h3").text("Ticket");
		div.append("br");
		d3.json("http://localhost:8089/rest/event/details/location?eventCode=" + event.code, function(locations)
		{
			if (locations != null)
			{
				if (locations.location instanceof Array)
				{
					div.append("select").attr("id", "locationTypeId").selectAll("option").data(locations.location).enter().append("option").attr("value", function(d) { return d.locationType; })
						.text
						(
							function(d)
							{
								return gaLocationTypes[d.locationType]
								+ (d.numbered === "true" ? " numerada" : "")
								+ " - " + d.quantity;
							}
						);
				}
				else
				{
					div.append("select").attr("id", "locationTypeId").append("option").attr("value", locations.location.locationType)
						.text
						(
							gaLocationTypes[locations.location.locationType]
							+ (locations.location.numbered === "true" ? " numerada" : "")
							+ " - " + locations.location.quantity
						);
				}
				div.append("br");
				div.append("label").text("Numerada");
				div.append("input").attr("type", "text").attr("id", "numbered");
				div.append("label").text("Cantidad");
				div.append("input").attr("type", "text").attr("id", "quantity");
				div.append("input").attr("type", "hidden").attr("id", "eventCode").attr("value", eventCode);
				//--
				div.append("br");
				div.append("button").attr("onclick", "ticketsInsert()").text("Guardar");
			}
		});
	});
}

function ticketsInsert()
{
	var queryString = "id=" + d3.select("#id").property("value")
		+ "&name=" + d3.select("#name").property("value")
		+ "&address=" + d3.select("#address").property("value")
		+ "&telephone=" + d3.select("#telephone").property("value")
		+ "&cardnumber=" + d3.select("#cardnumber").property("value")
		+ "&cardType=" + d3.select("#cardType").property("value");
	d3.xhr2("http://localhost:8089/rest/ticket/customer?" + queryString, "POST", function(json)
	{
		var ticketCode = Math.floor(Math.random() * 1000000);
		var numbered = d3.select("#numbered").property("value");
		if (numbered == "")
		{
			numbered = "0";
		}
		var queryString = "code=" + ticketCode
			+ "&locationType=" + d3.select("#locationTypeId").property("value")
			+ "&eventCode=" + d3.select("#eventCode").property("value")
			+ "&customerId=" + d3.select("#id").property("value")
			+ "&quantity=" + d3.select("#quantity").property("value")
			+ "&numbered=" + numbered;
		d3.xhr2("http://localhost:8089/rest/ticket?" + queryString, "POST", function(json)
		{
			showInvoice(ticketCode);
		});
	});
}

function showInvoice(ticketCode)
{
	var div = clear();
	div.append("br");
	div.append("br");
	div.append("br");
	var fieldset = div.append("fieldset").attr("align", "center");
	fieldset.append("legend").text("Factura de tiquete");
	var table = fieldset.append("table").attr("width", "100%");
	d3.json("http://localhost:8089/rest/ticket?ticketCode=" + ticketCode, function(ticket)
	{
		d3.json("http://localhost:8089/rest/ticket/customer?username=" + ticket.customerId, function(customer)
		{
			d3.json("http://localhost:8089/rest/ticket/numberedTickets?ticketCode=" + ticket.code, function(numberedTickets)
			{
				d3.json("http://localhost:8089/rest/event/details?eventCode=" + ticket.eventCode, function(event)
				{
					var tr = table.append("tr");
					tr.append("td").text("Nombre: " + customer.name);
					tr.append("td").text("Número de cédula: " + customer.id);
					tr = table.append("tr");
					tr.append("td").text("Teléfono: " + customer.telephone);
					tr.append("td").text("Dirección: " + customer.address);
					tr = table.append("tr");
					tr.append("td").text("Tipo de tarjeta: " + customer.cardType);
					tr.append("td").text("Número de tarjeta: " + customer.cardnumber);
					tr = table.append("tr");
					tr.append("td").text("Tiquete: " + ticket.code);
					tr.append("td").text("Evento: " + event.name);
					tr = table.append("tr");
					tr.append("td").text("Fecha de compra: " + ticket.date);
					if (numberedTickets != null && numberedTickets.numberedTicket != null)
					{
						if (numberedTickets.numberedTicket instanceof Array)
						{
							tr.append("td").selectAll("span").data(numberedTickets.numberedTicket).enter().append("span").text(function(d) { return d.locationNumber + " "; });
						}
						else
						{
							tr.append("td").text(numberedTickets.numberedTicket.locationNumber);
						}
					}
					
					tr = table.append("tr");
					tr.append("td").text("Cantidad: " + ticket.quantity);
					tr.append("td").text("Total a pagar: " + ticket.amount);
					div.append("button").attr("onclick", "EventsList()").text("OK");
				});
			});
		});
	});
}