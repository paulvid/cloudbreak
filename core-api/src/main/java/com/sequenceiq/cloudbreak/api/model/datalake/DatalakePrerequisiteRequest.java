package com.sequenceiq.cloudbreak.api.model.datalake;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sequenceiq.cloudbreak.api.endpoint.v4.database.requests.DatabaseV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.kerberos.requests.KerberosV4Request;
import com.sequenceiq.cloudbreak.api.model.ParametersQueryRequest;
import com.sequenceiq.cloudbreak.api.model.ldap.LdapConfigRequest;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DatalakePrerequisiteRequest extends ParametersQueryRequest {

    @NotNull
    @ApiModelProperty(value = ModelDescriptions.LdapConfigModelDescription.REQUEST, required = true)
    private LdapConfigRequest ldap;

    @ApiModelProperty(value = ModelDescriptions.RDSConfigModelDescription.REQUEST, required = true)
    private Set<DatabaseV4Request> databases = new HashSet<>();

    @NotNull
    @ApiModelProperty(value = ModelDescriptions.KerberosConfigModelDescription.REQUEST, required = true)
    private KerberosV4Request kerberos;

    public LdapConfigRequest getLdap() {
        return ldap;
    }

    public void setLdap(LdapConfigRequest ldap) {
        this.ldap = ldap;
    }

    public Set<DatabaseV4Request> getDatabases() {
        return databases;
    }

    public void setDatabases(Set<DatabaseV4Request> databases) {
        this.databases = databases;
    }

    public KerberosV4Request getKerberos() {
        return kerberos;
    }

    public void setKerberos(KerberosV4Request kerberos) {
        this.kerberos = kerberos;
    }
}
