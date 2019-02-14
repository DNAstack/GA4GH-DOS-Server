package com.dnastack.dos.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Google Kubernetes Engine ingress controller requires every app to respond to {@code GET /} with HTTP 200.
 * Once we move to a more sensible ingress controller, we can remove this class from the app.
 */
@RestController
public class IngressHealthCheckAppeasementController {

    @GetMapping("/")
    public String hello() {
        return "This space intentionally left blank";
    }
}
