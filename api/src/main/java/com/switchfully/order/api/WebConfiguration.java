package com.switchfully.order.api;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Required if we want to let our frontend that's running on a different port (e.g. 4200) contact our backend which
 * is running on port 9000 (cross-domain). By default, this is not allowed by Spring.
 *
 * From https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
 *
 *      Cross-Origin Resource Sharing (CORS) is a mechanism that uses additional HTTP headers to tell browsers to give a
 *      web application running at one origin, access to selected resources from a different origin. A web application
 *      executes a cross-origin HTTP request when it requests a resource that has a different origin (domain, protocol,
 *      or port) from its own.
 *
 *      An example of a cross-origin request: the front-end JavaScript code served from https://domain-a.com
 *      uses XMLHttpRequest to make a request for https://domain-b.com/data.json.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedMethods("*");
    }
}
