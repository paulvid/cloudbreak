package com.sequenceiq.cloudbreak.api.endpoint.v4.ldaps.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.sequenceiq.cloudbreak.doc.ModelDescriptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class LdapMinimalV4Request {

    @NotNull
    @ApiModelProperty(value = ModelDescriptions.LdapConfigModelDescription.SERVER_HOST, required = true)
    private String host;

    @NotNull
    @Max(65535)
    @Min(1)
    @ApiModelProperty(value = ModelDescriptions.LdapConfigModelDescription.SERVER_PORT, required = true)
    private Integer port;

    @ApiModelProperty(ModelDescriptions.LdapConfigModelDescription.PROTOCOL)
    private String protocol = "ldap";

    @NotNull
    @ApiModelProperty(value = ModelDescriptions.LdapConfigModelDescription.BIND_DN, required = true)
    private String bindDn;

    @NotNull
    @ApiModelProperty(value = ModelDescriptions.LdapConfigModelDescription.BIND_PASSWORD, required = true)
    private String bindPassword;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getBindDn() {
        return bindDn;
    }

    public void setBindDn(String bindDn) {
        this.bindDn = bindDn;
    }

    public String getBindPassword() {
        return bindPassword;
    }

    public void setBindPassword(String bindPassword) {
        this.bindPassword = bindPassword;
    }
}
