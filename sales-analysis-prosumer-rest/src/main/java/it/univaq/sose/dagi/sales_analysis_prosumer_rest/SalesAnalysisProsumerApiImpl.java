package it.univaq.sose.dagi.sales_analysis_prosumer_rest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import it.univaq.sose.dagi.sales_analysis_prosumer_rest.client.CustomerRESTClient;
import it.univaq.sose.dagi.sales_analysis_prosumer_rest.client.SoldTicketsSOAPClient;
import it.univaq.sose.dagi.sales_analysis_prosumer_rest.model.Customer;
import it.univaq.sose.dagi.sales_analysis_prosumer_rest.model.EventSalesReport;
import it.univaq.sose.dagi.sales_analysis_prosumer_rest.model.SoldTicket;
import it.univaq.sose.dagi.wsdltypes.ServiceException_Exception;

@Service
public class SalesAnalysisProsumerApiImpl implements SalesAnalysisProsumerApi {

	private SoldTicketsSOAPClient ticketsClient;
	private CustomerRESTClient customerClient;
	
	public SalesAnalysisProsumerApiImpl(SoldTicketsSOAPClient ticketsClient, CustomerRESTClient customerClient) {
		super();
		this.ticketsClient = ticketsClient;
		this.customerClient = customerClient;
	}

	@Override
	public EventSalesReport getEventSalesReport(long eventId) throws ServiceException_Exception {
		try {
			//Fetch the list of tickets
			List<SoldTicket> tickets = ticketsClient.fetchEventSoldTicketsInfo(eventId);
			
			//Fetch the ages and genders of customers that bought the tickets
			Set<Long> userIdsSet = new HashSet<>();
			tickets.forEach(ticket -> {userIdsSet.add(ticket.getUserId());});
			Long[] userIds = new Long[userIdsSet.size()];
			JsonNode userInfos = customerClient.fetchUsersInfo(userIdsSet.toArray(userIds));

			EventSalesReport report = new EventSalesReport();
			float averageAge = 0.0f;
			Map<String, Integer> genderCounts = new HashMap<>();
			Map<Integer, Integer> ageCounts = new HashMap<>();
			Map<LocalDateTime, Integer> dateCounts = new HashMap<>();
			
			for(SoldTicket currentTicket : tickets) {
				//date count 
				if (dateCounts.containsKey(currentTicket.getReferenceDate())) {
					int curValue = dateCounts.get(currentTicket.getReferenceDate());
					dateCounts.put(currentTicket.getReferenceDate(), curValue +1);
				}
				else {
					dateCounts.put(currentTicket.getReferenceDate(), 1);
				}
				//retrieve customer info
				Customer customerInfo = new Customer();
				if(userInfos.isArray()) {
					for(JsonNode user : userInfos) {
						if (user.findValue(CustomerRESTClient.FIELD_ID).asInt() == currentTicket.getUserId()) {
							customerInfo.setId(currentTicket.getUserId());
							customerInfo.setAge(user.findValue(CustomerRESTClient.FIELD_AGE).asInt());
							customerInfo.setGender(user.findValue(CustomerRESTClient.FIELD_GENDER).asText());
							break;
						}
					}

				}
				//gender count
				if (genderCounts.containsKey(customerInfo.getGender())) {
					int curValue = genderCounts.get(customerInfo.getGender());
					genderCounts.put(customerInfo.getGender(), curValue +1);
				}
				else {
					genderCounts.put(customerInfo.getGender(), 1);
				}
				//age count
				if (ageCounts.containsKey(customerInfo.getAge())) {
					int curValue = ageCounts.get(customerInfo.getAge());
					ageCounts.put(customerInfo.getAge(), curValue +1);
				}
				else {
					ageCounts.put(customerInfo.getAge(), 1);
				}
				//increment average age
				averageAge += customerInfo.getAge();
			}
			
			averageAge /= tickets.size();
			report.setAverageCustomerAge(averageAge);
			report.setAgeCounts(ageCounts);
			report.setGenderCounts(genderCounts);
			report.setDateCounts(dateCounts);
			report.setEventSoldTickets(tickets);
			
			
			return report;
			
		} catch (ServiceException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

}
