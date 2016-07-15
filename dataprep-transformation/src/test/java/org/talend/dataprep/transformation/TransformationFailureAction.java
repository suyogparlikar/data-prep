//  ============================================================================
//
//  Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
//  This source code is available under agreement available at
//  https://github.com/Talend/data-prep/blob/master/LICENSE
//
//  You should have received a copy of the agreement
//  along with this program; if not, write to Talend SA
//  9 rue Pages 92150 Suresnes, France
//
//  ============================================================================

package org.talend.dataprep.transformation;

import org.springframework.stereotype.Component;
import org.talend.dataprep.api.dataset.ColumnMetadata;
import org.talend.dataprep.api.dataset.DataSetRow;
import org.talend.dataprep.transformation.actions.common.AbstractActionMetadata;
import org.talend.dataprep.transformation.actions.common.CellAction;
import org.talend.dataprep.transformation.actions.common.ColumnAction;
import org.talend.dataprep.transformation.actions.common.DataSetAction;
import org.talend.dataprep.transformation.api.action.context.ActionContext;

import java.util.EnumSet;
import java.util.Set;

/**
 * A unit test action: only use to test unexpected action failures.
 */
@Component(AbstractActionMetadata.ACTION_BEAN_PREFIX + TransformationFailureAction.TRANSFORMATION_FAILURE_ACTION)
public class TransformationFailureAction extends AbstractActionMetadata implements ColumnAction, CellAction, DataSetAction {

    public static final String TRANSFORMATION_FAILURE_ACTION = "testtransformationfailure";

    @Override
    public String getName() {
        return TRANSFORMATION_FAILURE_ACTION;
    }

    @Override
    public String getLabel() {
        return "Transformation failure test action label";
    }

    @Override
    public String getDescription() {
        return "Transformation failure test action description";
    }

    @Override
    public String getCategory() {
        return "TEST";
    }

    @Override
    public boolean acceptColumn(ColumnMetadata column) {
        return true;
    }

    @Override
    public void applyOnCell(DataSetRow row, ActionContext context) {
        throw new Error("On purpose unchecked exception");
    }

    @Override
    public void applyOnColumn(DataSetRow row, ActionContext context) {
        throw new Error("On purpose unchecked exception");
    }

    @Override
    public void applyOnDataSet(DataSetRow row, ActionContext context) {
        throw new Error("On purpose unchecked exception");
    }

    @Override
    public Set<Behavior> getBehavior() {
        return EnumSet.allOf(Behavior.class);
    }
}
