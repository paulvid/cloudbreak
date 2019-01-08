package com.sequenceiq.it.cloudbreak.newway;

import static com.sequenceiq.it.cloudbreak.newway.log.Log.logJSON;

import java.io.IOException;

import com.sequenceiq.cloudbreak.api.endpoint.v4.database.filter.DatabaseV4ListFilter;
import com.sequenceiq.cloudbreak.api.endpoint.v4.database.requests.DatabaseV4TestRequest;
import com.sequenceiq.it.IntegrationTestContext;

public class RdsConfigAction {
    private RdsConfigAction() {
    }

    static void post(IntegrationTestContext integrationTestContext, Entity entity) throws Exception {
        RdsConfigEntity rdsconfigEntity = (RdsConfigEntity) entity;
        CloudbreakClient client;
        client = integrationTestContext.getContextParam(CloudbreakClient.CLOUDBREAK_CLIENT,
                CloudbreakClient.class);
        rdsconfigEntity.setResponse(
                client.getCloudbreakClient()
                        .databaseV4Endpoint()
                        .create(client.getWorkspaceId(), rdsconfigEntity.getRequest()));
        logJSON("Rds config post request: ", rdsconfigEntity.getRequest());
    }

    public static void get(IntegrationTestContext integrationTestContext, Entity entity) throws IOException {
        RdsConfigEntity rdsconfigEntity = (RdsConfigEntity) entity;
        CloudbreakClient client;
        client = integrationTestContext.getContextParam(CloudbreakClient.CLOUDBREAK_CLIENT,
                CloudbreakClient.class);
        rdsconfigEntity.setResponse(
                client.getCloudbreakClient()
                        .databaseV4Endpoint()
                        .get(client.getWorkspaceId(), rdsconfigEntity.getRequest().getName()));
        logJSON(" get rds config response: ", rdsconfigEntity.getResponse());
    }

    public static void getAll(IntegrationTestContext integrationTestContext, Entity entity) throws IOException {
        RdsConfigEntity rdsconfigEntity = (RdsConfigEntity) entity;
        CloudbreakClient client;
        client = integrationTestContext.getContextParam(CloudbreakClient.CLOUDBREAK_CLIENT,
                CloudbreakClient.class);
        DatabaseV4ListFilter listRequest = new DatabaseV4ListFilter();
        listRequest.setAttachGlobal(false);
        rdsconfigEntity.setResponses(
                client.getCloudbreakClient()
                        .databaseV4Endpoint()
                        .list(client.getWorkspaceId(), listRequest)
                        .getDatabases());
        logJSON(" get all rds config response: ", rdsconfigEntity.getResponse());
    }

    public static void delete(IntegrationTestContext integrationTestContext, Entity entity) {
        RdsConfigEntity rdsconfigEntity = (RdsConfigEntity) entity;
        CloudbreakClient client;
        client = integrationTestContext.getContextParam(CloudbreakClient.CLOUDBREAK_CLIENT,
                CloudbreakClient.class);
        client.getCloudbreakClient()
                .databaseV4Endpoint()
                .delete(client.getWorkspaceId(), rdsconfigEntity.getName());
    }

    static void testConnect(IntegrationTestContext integrationTestContext, Entity entity) throws Exception {

        RdsConfigEntity rdsConfigEntity = (RdsConfigEntity) entity;

        DatabaseV4TestRequest databaseV4TestRequest = new DatabaseV4TestRequest();
        databaseV4TestRequest.setRdsConfig(rdsConfigEntity.getRequest());

        CloudbreakClient client;
        client = integrationTestContext.getContextParam(CloudbreakClient.CLOUDBREAK_CLIENT,
                CloudbreakClient.class);
        rdsConfigEntity.setResponseTestResult(
                client.getCloudbreakClient()
                        .databaseV4Endpoint()
                        .test(client.getWorkspaceId(), databaseV4TestRequest));
        logJSON("Rds test post request: ", rdsConfigEntity.getRequest());
    }

    public static void createInGiven(IntegrationTestContext integrationTestContext, Entity entity) throws Exception {
        try {
            get(integrationTestContext, entity);
        } catch (Exception e) {
            post(integrationTestContext, entity);
        }
    }

    static void createDeleteInGiven(IntegrationTestContext integrationTestContext, Entity entity) throws Exception {
        post(integrationTestContext, entity);
        delete(integrationTestContext, entity);
    }
}