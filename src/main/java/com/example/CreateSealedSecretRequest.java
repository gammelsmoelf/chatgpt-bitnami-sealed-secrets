package com.example;

public class CreateSealedSecretRequest {
    private String name;
    private String namespace;
    private String yamlData;

    public CreateSealedSecretRequest() {
    }

    public CreateSealedSecretRequest(String name, String namespace, String yamlData) {
        this.name = name;
        this.namespace = namespace;
        this.yamlData = yamlData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getYamlData() {
        return yamlData;
    }

    public void setYamlData(String yamlData) {
        this.yamlData = yamlData;
    }
}
