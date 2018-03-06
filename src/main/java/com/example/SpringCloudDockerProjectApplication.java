package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@EnableZuulProxy
@Controller
public class SpringCloudDockerProjectApplication {

	@Value("${test-ui}")
	String redirectUrl;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void index(HttpServletResponse response) throws IOException {
		response.sendRedirect(redirectUrl);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudDockerProjectApplication.class, args);
	}
}
