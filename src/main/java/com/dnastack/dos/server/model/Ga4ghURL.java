package com.dnastack.dos.server.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Embeddable
@Entity
public class Ga4ghURL {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String url;

    // TODO change to Map<String, ?>
    @ElementCollection
    @MapKeyColumn(name = "system_metadata_key")
    @Column(name = "system_metadata_value")
    @NotNull
    private Map<String, String> system_metadata = new HashMap<String, String>();

    @ElementCollection
    @MapKeyColumn(name = "user_metadata_key")
    @Column(name = "user_metadata_value")
    @NotNull
    private Map<String, String> user_metadata = new HashMap<String, String>();

    // Custom Constructors

    public Ga4ghURL() {

    }

    public Ga4ghURL(String url, Map<String, String> system_metadata, Map<String, String> user_metadata) {
        super();
        this.url = url;
        this.system_metadata = system_metadata;
        this.user_metadata = user_metadata;
    }

    public Ga4ghURL(Long id, String url, Map<String, String> system_metadata, Map<String, String> user_metadata) {
        super();
        this.id = id;
        this.url = url;
        this.system_metadata = system_metadata;
        this.user_metadata = user_metadata;
    }

    public Ga4ghURL(URL url) {
        super();
        this.url = url.getUrl();
        this.system_metadata = new HashMap<>(url.getSystem_metadata());
        this.user_metadata = new HashMap<>(url.getUser_metadata());
    }

}
