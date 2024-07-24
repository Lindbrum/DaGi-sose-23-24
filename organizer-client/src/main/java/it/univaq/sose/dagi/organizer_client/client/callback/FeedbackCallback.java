package it.univaq.sose.dagi.organizer_client.client.callback;

import javax.ws.rs.client.InvocationCallback;

import io.swagger.model.feedback_prosumer.EventFeedbackReport;

public class FeedbackCallback implements InvocationCallback<EventFeedbackReport> {

	private EventFeedbackReport response;
	
	@Override
	public void completed(EventFeedbackReport response) {
		this.response = response;
		
	}

	@Override
	public void failed(Throwable throwable) {
		throwable.printStackTrace();
		
	}
	
	public EventFeedbackReport getResponse() {
		return response;
	}
	
}
