package com.example;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class SettingsController {

    @Value("${uaa}")
    String uaaUri;

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public Map<String, String> settings() throws IOException {
        return ImmutableMap.<String, String>of("uaa", uaaUri);
    }
}
