package com.mesosphere.sdk.http.endpoints;

import com.mesosphere.sdk.http.EndpointUtils;
import com.mesosphere.sdk.http.queries.ArtifactQueries;
import com.mesosphere.sdk.specification.ServiceSpec;
import com.mesosphere.sdk.state.ConfigStore;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import java.util.UUID;

/**
 * A read-only API for accessing file artifacts (e.g. config templates) for retrieval by pods.
 */
@Path("/v1/artifacts")
public class ArtifactResource {
    private static final String SERVICE_ARTIFACT_URI_FORMAT = "http://%s/v1/artifacts/template/%s/%s/%s/%s";

    private final ConfigStore<ServiceSpec> configStore;

    /**
     * Returns a valid URL for accessing a config template artifact from a service task.
     * Must be kept in sync with {@link #getTemplate(String, String, String, String)}.
     *
     * @see JobsArtifactResource#getJobTemplateUrl
     */
    public static String getStandaloneServiceTemplateUrl(
            String serviceName, UUID configId, String podType, String taskName, String configName) {
        return String.format(SERVICE_ARTIFACT_URI_FORMAT,
                EndpointUtils.toSchedulerApiVipHostname(serviceName), configId, podType, taskName, configName);
    }

    public ArtifactResource(ConfigStore<ServiceSpec> configStore) {
        this.configStore = configStore;
    }

    /**
     * @see ArtifactQueries
     */
    @Path("/template/{configurationId}/{podType}/{taskName}/{configurationName}")
    @GET
    public Response getTemplate(
            @PathParam("configurationId") String configurationId,
            @PathParam("podType") String podType,
            @PathParam("taskName") String taskName,
            @PathParam("configurationName") String configurationName) {
        return ArtifactQueries.getTemplate(configStore, configurationId, podType, taskName, configurationName);
    }
}
