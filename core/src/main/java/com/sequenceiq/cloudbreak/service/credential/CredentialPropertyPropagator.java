package com.sequenceiq.cloudbreak.service.credential;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.endpoint.v4.credentials.CredentialV4Base;
import com.sequenceiq.cloudbreak.api.endpoint.v4.credentials.parameters.CredentialV4Parameters;
import com.sequenceiq.cloudbreak.api.endpoint.v4.credentials.requests.CredentialV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.credentials.responses.CredentialV4Response;

@Component
public class CredentialPropertyPropagator {

    public Optional<CredentialV4Parameters> propagateCredentialProperty(CredentialV4Base request) {
        return propagate(request);
    }

    public Optional<CredentialV4Parameters> propagateCredentialProperty(CredentialV4Request request) {
        return propagate(request);
    }

    public Optional<CredentialV4Parameters> propagateCredentialProperty(CredentialV4Response request) {
        return propagate(request);
    }

    private Optional<CredentialV4Parameters> propagate(CredentialV4Base cred) {
        if (cred.getAws() != null) {
            return Optional.of(cred.getAws());
        } else if (cred.getAzure() != null) {
            return Optional.of(cred.getAzure());
        } else if (cred.getOpenstack() != null) {
            return Optional.of(cred.getOpenstack());
        } else if (cred.getYarn() != null) {
            return Optional.of(cred.getYarn());
        } else if (cred.getCumulus() != null) {
            return Optional.of(cred.getCumulus());
        }
        return Optional.empty();
    }

}