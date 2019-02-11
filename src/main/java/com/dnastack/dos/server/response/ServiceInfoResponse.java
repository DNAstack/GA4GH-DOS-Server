package com.dnastack.dos.server.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ServiceInfoResponse {

    private String version;
    private String name;
    private String description;
    private Map<String, String> contact;
    private Map<String, String> license;

}
