package org.talend.dataprep.transformation.api.action.metadata.delete;

import static org.talend.dataprep.transformation.api.action.metadata.category.ActionScope.EMPTY;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.talend.dataprep.api.dataset.ColumnMetadata;
import org.talend.dataprep.transformation.api.action.context.ActionContext;
import org.talend.dataprep.transformation.api.action.metadata.common.ActionMetadata;
import org.talend.dataprep.transformation.api.action.metadata.common.ColumnAction;

/**
 * Delete row when value is empty.
 */
@Component(DeleteEmpty.ACTION_BEAN_PREFIX + DeleteEmpty.DELETE_EMPTY_ACTION_NAME)
public class DeleteEmpty extends AbstractDelete implements ColumnAction {

    /**
     * The action name.
     */
    public static final String DELETE_EMPTY_ACTION_NAME = "delete_empty"; //$NON-NLS-1$

    /**
     * @see ActionMetadata#getName()
     */
    @Override
    public String getName() {
        return DELETE_EMPTY_ACTION_NAME;
    }

    /**
     * @see ActionMetadata#getActionScope()
     */
    @Override
    public List<String> getActionScope() {
        return Collections.singletonList(EMPTY.getDisplayName());
    }

    /**
     * @see ActionMetadata#acceptColumn(ColumnMetadata)
     */
    @Override
    public boolean acceptColumn(ColumnMetadata column) {
        return true;
    }

    @Override
    public void compile(ActionContext actionContext) {
        // This action is able to deal with missing column, overrides default behavior
        actionContext.setActionStatus(ActionContext.ActionStatus.OK);
    }

    /**
     * @see AbstractDelete#toDelete(ColumnMetadata, Map, String)
     */
    @Override
    public boolean toDelete(final ColumnMetadata colMetadata, final Map<String, String> parsedParameters, final String value) {
        return value == null || value.trim().length() == 0;
    }
}
