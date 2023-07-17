package com.example;

// This class is used as the request body for the POST /sealedSecret endpoint.
public class CreateSealedSecretRequest {

    // This field is used to hold the YAML data for the SealedSecret resource
    // that will be created.
    private String yamlData;

    public CreateSealedSecretRequest() {
        // No-argument constructor required by Jackson deserialization
    }
    }

    public CreateSealedSecretRequest(String yamlData) {
        this.yamlData = yamlData;
    }

    public String getYamlData() {
        return yamlData;
    }

    public void setYamlData(String yamlData) {
        this.yamlData = yamlData;
    }

}
