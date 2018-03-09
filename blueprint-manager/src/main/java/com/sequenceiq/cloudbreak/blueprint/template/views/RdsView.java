package com.sequenceiq.cloudbreak.blueprint.template.views;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.sequenceiq.cloudbreak.domain.RDSConfig;

public class RdsView {

    private final String connectionURL;

    private final String connectionUserName;

    private final String connectionPassword;

    private final String databaseName;

    private final String host;

    private final String connectionHost;

    private final String databaseType;

    //TODO check a real hive RDS config to validate the existence of com.sequenceiq.cloudbreak.api.model.RDSDatabase
    private String connectionDriverName;

    private String ambariDbOption;

    private String databaseTypeDbName;

    private Map<String, Object> properties = new HashMap<>();

    public RdsView(RDSConfig rdsConfig) {
        connectionURL = rdsConfig.getConnectionURL();
        connectionUserName = rdsConfig.getConnectionUserName();
        connectionPassword = rdsConfig.getConnectionPassword();

        String port = "";
        String[] split = connectionURL.split("//");
        String withoutJDBCPrefix = split[split.length - 1];
        String hostWithPort = withoutJDBCPrefix.split("/")[0];
        int portDelimiterIndex = hostWithPort.indexOf(':');
        if (portDelimiterIndex > 0) {
            host = hostWithPort.substring(0, portDelimiterIndex);
            port = hostWithPort.substring(portDelimiterIndex + 1);
        } else {
            host = hostWithPort;
        }

        databaseName = getDatabaseName(connectionURL);
        connectionHost = createConnectionHost(port);
        if (rdsConfig.getAttributes() != null) {
            properties = rdsConfig.getAttributes().getMap();
        }
        if (rdsConfig.getDatabaseType() != null) {
            connectionDriverName = rdsConfig.getDatabaseType().getDbDriver();
            ambariDbOption = rdsConfig.getDatabaseType().getAmbariDbOption();
            databaseTypeDbName = rdsConfig.getDatabaseType().getDbName();
        }
        databaseType = getDatabaseType(connectionURL);
    }

    public String getConnectionURL() {
        return connectionURL;
    }

    public String getConnectionUserName() {
        return connectionUserName;
    }

    public String getConnectionPassword() {
        return connectionPassword;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public String getConnectionHost() {
        return connectionHost;
    }

    public String getHost() {
        return host;
    }

    public String getConnectionDriverName() {
        return connectionDriverName;
    }

    public String getAmbariDbOption() {
        return ambariDbOption;
    }

    public String getDatabaseTypeDbName() {
        return databaseTypeDbName;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    private String getDatabaseName(String connectionURL) {
        String databaseName = "";
        String[] split = connectionURL.split("/");
        if (split.length > 1) {
            databaseName = split[split.length - 1];
        }
        return databaseName;
    }

    private String createConnectionHost(String port) {
        String result = host;
        if (!StringUtils.isEmpty(port)) {
            result = host + ':' + port;
        }
        return result;
    }

    private String getDatabaseType(String connectionURL) {
        String databaseType = "";
        String[] split = connectionURL.split(":");
        if (split.length > 1) {
            databaseType = split[1];
        }
        return databaseType;
    }
}