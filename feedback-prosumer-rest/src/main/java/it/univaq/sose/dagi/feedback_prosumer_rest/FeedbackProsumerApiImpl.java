package it.univaq.sose.dagi.feedback_prosumer_rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.univaq.sose.dagi.feedback_prosumer_rest.client.FeedbackSOAPClient;
import it.univaq.sose.dagi.feedback_prosumer_rest.model.EventFeedbackReport;
import it.univaq.sose.dagi.feedback_prosumer_rest.model.Feedback;
import it.univaq.sose.dagi.wsdltypes.ObjectFactory;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;

@Service
public class FeedbackProsumerApiImpl implements FeedbackProsumerApi {

	private FeedbackSOAPClient feedbackClient;
	private ObjectFactory factory;
	
	
	public FeedbackProsumerApiImpl(FeedbackSOAPClient feedbackClient) {
		super();
		this.feedbackClient = feedbackClient;
		this.factory = new ObjectFactory();
	}



	@Override
	public ResponseEntity<EventFeedbackReport> getEventFeedbackReport(long eventId, String keywords) throws ServiceException_Exception {
		try {
			List<Feedback> feedbacks = feedbackClient.requestEventFeedbacks(eventId);
			//TODO fetch ages of users that left feedbacks
			//(userId, age)
			Map<Long, Integer> buyersAges = new HashMap<>();
			EventFeedbackReport report = new EventFeedbackReport();
			float averageRating = 0.0f;
			float averageAge = 0.0f;
			Map<String, Integer> keywordCounts = null;
			
			//If keywords are provided, initialize the map
			if(keywords != null && !keywords.isBlank()) {
				keywordCounts = new HashMap<>();
				for(String keyword : keywords.split("[,;]")) {
					keywordCounts.put(keyword, 0);
				}
			}
			
			for(Feedback feedback : feedbacks) {
				averageRating += feedback.getRating();
				//averageAge += buyersAges.get(feedback.getUserId());
				if(keywordCounts != null) {
					for(String keyword : keywordCounts.keySet()) {
						Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
				        Matcher matcher = pattern.matcher(feedback.getBody());
				        int count = 0;
				        while (matcher.find()) {
				            System.out.println(matcher.group());  // Output: Ciao, cIAo
				            count++;
				        }
						int previousKeywordCount = keywordCounts.get(keyword);
						keywordCounts.put(keyword, previousKeywordCount + count);
					}
				}
			}
			averageAge /= feedbacks.size();
			averageRating /= feedbacks.size();
			report.setAverageCustomerAge(averageAge);
			report.setAverageRating(averageRating);
			report.setEventFeedbacks(feedbacks);
			report.setKeywordsCount(keywordCounts);
			
			return new ResponseEntity<EventFeedbackReport>(report, HttpStatus.OK);
			
		} catch (ServiceException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

}
