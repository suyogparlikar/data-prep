package org.talend.dataprep.api.service.command.preparation;

import static org.talend.dataprep.api.service.command.common.Defaults.asNull;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.talend.dataprep.api.service.APIService;
import org.talend.dataprep.api.service.command.common.PreparationCommand;

@Component
@Scope("request")
public class PreparationDeleteAction extends PreparationCommand<Void> {

    private PreparationDeleteAction(final HttpClient client, final String preparationId, final String stepId) {
        super(APIService.PREPARATION_GROUP, client);
        execute(() -> new HttpDelete(preparationServiceUrl + "/preparations/" + preparationId + "/actions/" + stepId)); //$NON-NLS-1$ //$NON-NLS-2$
        on(HttpStatus.OK).then(asNull());
    }

}
