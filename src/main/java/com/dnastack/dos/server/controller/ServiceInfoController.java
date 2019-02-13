package com.dnastack.dos.server.controller;

import com.dnastack.dos.server.response.ServiceInfoResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceInfoController {

    @RequestMapping("/service-info")
    public ServiceInfoResponse getServiceInfo() {
        return new ServiceInfoResponse("1.0.0", "DOS Server",
                "Server capable of hosting and allowing the discovery of data objects.", null, null);
    }

}
