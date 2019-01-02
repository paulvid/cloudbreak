package com.sequenceiq.cloudbreak.api.endpoint.v4.util.responses;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sequenceiq.cloudbreak.api.model.JsonEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class StructuredParameterQueryV4Response implements JsonEntity {

    private String propertyName;

    private String description;

    private String defaultPath;

    private Set<String> relatedServices;

    private String propertyFile;

    private String protocol;

    private String propertyDisplayName;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    public Set<String> getRelatedServices() {
        return relatedServices;
    }

    public void setRelatedServices(Set<String> relatedServices) {
        this.relatedServices = relatedServices;
    }

    public String getPropertyFile() {
        return propertyFile;
    }

    public void setPropertyFile(String propertyFile) {
        this.propertyFile = propertyFile;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getPropertyDisplayName() {
        return propertyDisplayName;
    }

    public void setPropertyDisplayName(String propertyDisplayName) {
        this.propertyDisplayName = propertyDisplayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StructuredParameterQueryV4Response)) {
            return false;
        }
        StructuredParameterQueryV4Response that = (StructuredParameterQueryV4Response) o;
        return Objects.equals(getPropertyName(), that.getPropertyName())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getDefaultPath(), that.getDefaultPath())
                && Objects.equals(getRelatedServices(), that.getRelatedServices())
                && Objects.equals(getPropertyFile(), that.getPropertyFile())
                && Objects.equals(getProtocol(), that.getProtocol())
                && Objects.equals(getPropertyDisplayName(), that.getPropertyDisplayName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPropertyName(), getDescription(), getDefaultPath(), getRelatedServices(), getPropertyFile(), getProtocol(),
                getPropertyDisplayName());
    }

}
