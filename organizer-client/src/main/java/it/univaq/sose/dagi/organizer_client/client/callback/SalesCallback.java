package it.univaq.sose.dagi.organizer_client.client.callback;

import javax.ws.rs.client.InvocationCallback;

import io.swagger.model.sales_prosumer.EventSalesReport;

public class SalesCallback implements InvocationCallback<EventSalesReport> {

	private EventSalesReport response;
	
	@Override
	public void completed(EventSalesReport response) {
		this.response = response;
		
	}

	@Override
	public void failed(Throwable throwable) {
		throwable.printStackTrace();
		
	}

	public EventSalesReport getResponse() {
		return response;
	}
	
	
}
