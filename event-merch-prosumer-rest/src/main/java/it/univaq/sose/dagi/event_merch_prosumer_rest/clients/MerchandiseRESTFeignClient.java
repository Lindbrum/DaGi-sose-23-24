package it.univaq.sose.dagi.event_merch_prosumer_rest.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.JsonNode;

@FeignClient(name="merchandising-rest")
public interface MerchandiseRESTFeignClient {
	@GetMapping("/api/merchandise-rest/merch/event/{eventId}")
	public JsonNode findEventMerch(@PathVariable Long eventId);
}
