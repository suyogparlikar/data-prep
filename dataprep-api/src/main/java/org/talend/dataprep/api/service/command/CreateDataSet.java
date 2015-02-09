package org.talend.dataprep.api.service.command;

import com.netflix.hystrix.HystrixCommand;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.talend.dataprep.api.service.DataPreparationAPI;

import java.io.InputStream;

public class CreateDataSet extends HystrixCommand<String> {

    private final String contentServiceUrl;

    private final String name;

    private final InputStream dataSetContent;

    private final HttpClient client;

    public CreateDataSet(HttpClient client, String contentServiceUrl, String name, InputStream dataSetContent) {
        super(DataPreparationAPI.DATASET_GROUP);
        this.contentServiceUrl = contentServiceUrl;
        this.name = name;
        this.dataSetContent = dataSetContent;
        this.client = client;
    }

    @Override
    protected String getFallback() {
        throw new RuntimeException("Fallback not supported in this command.");
    }

    @Override
    protected String run() throws Exception {
        HttpPost contentCreation = new HttpPost(contentServiceUrl + "/?name=" + name); //$NON-NLS-1$
        contentCreation.setEntity(new InputStreamEntity(dataSetContent));
        HttpResponse response = client.execute(contentCreation);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode >= 200) {
            if (statusCode == HttpStatus.SC_NO_CONTENT) {
                return StringUtils.EMPTY;
            } else if (statusCode == HttpStatus.SC_OK) {
                return IOUtils.toString(response.getEntity().getContent());
            }
        }
        throw new RuntimeException("Unable to create content.");
    }
}
