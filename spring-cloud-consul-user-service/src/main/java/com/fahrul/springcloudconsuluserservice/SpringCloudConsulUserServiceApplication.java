package com.fahrul.springcloudconsuluserservice;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class SpringCloudConsulUserServiceApplication {
	
	@Autowired
	private RestTemplate template;
	
	@Autowired
	private DiscoveryClient client;
	
	@GetMapping("/accessAPI")
	public String invokeGreetingService() {
		URI uri = client.getInstances("g-service").stream().map(si -> si.getUri()).findFirst()
				.map(s -> s.resolve("/ping")).get();
		return template.getForObject(uri, String.class);
	}
	
	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConsulUserServiceApplication.class, args);
	}

}
