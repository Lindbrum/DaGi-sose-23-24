package it.univaq.sose.dagi.sales_analysis_prosumer_rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.JsonNode;

@FeignClient(name="authentication-rest")
public interface CustomerRESTFeignClient {

	@GetMapping("/api/authentication-rest/auth/customer/infos/{userIds}")
	public JsonNode fetchUsersInfo(@PathVariable Long[] userIds);
}
