package org.talend.dataprep.transformation.api.action.metadata.delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.talend.dataprep.api.dataset.ColumnMetadata;
import org.talend.dataprep.api.type.Type;
import org.talend.dataprep.transformation.api.action.context.ActionContext;
import org.talend.dataprep.transformation.api.action.metadata.common.ActionMetadata;
import org.talend.dataprep.transformation.api.action.metadata.common.RegexParametersHelper;
import org.talend.dataprep.transformation.api.action.parameters.Parameter;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.talend.dataprep.api.type.Type.NUMERIC;
import static org.talend.dataprep.api.type.Type.STRING;
import static org.talend.dataprep.transformation.api.action.parameters.ParameterType.REGEX;

/**
 * Delete row on a given value.
 */
@Component(DeleteOnValue.ACTION_BEAN_PREFIX + DeleteOnValue.DELETE_ON_VALUE_ACTION_NAME)
public class DeleteOnValue extends AbstractDelete {

    @Autowired
    private RegexParametersHelper regexParametersHelper;

    /**
     * The action name.
     */
    public static final String DELETE_ON_VALUE_ACTION_NAME = "delete_on_value"; //$NON-NLS-1$

    /**
     * Name of the parameter needed.
     */
    public static final String VALUE_PARAMETER = "value"; //$NON-NLS-1$

    /**
     * @see ActionMetadata#getName()
     */
    @Override
    public String getName() {
        return DELETE_ON_VALUE_ACTION_NAME;
    }

    /**
     * @see ActionMetadata#getCategory()
     */
    @Override
    @Nonnull
    public List<Parameter> getParameters() {
        final List<Parameter> parameters = super.getParameters();
        parameters.add(new Parameter(VALUE_PARAMETER, REGEX, regexParametersHelper.getEmptyParamAsString()));
        return parameters;
    }

    /**
     * @see ActionMetadata#acceptColumn(ColumnMetadata)
     */
    @Override
    public boolean acceptColumn(ColumnMetadata column) {
        return STRING.equals(Type.get(column.getType())) || NUMERIC.isAssignableFrom(Type.get(column.getType()));
    }

    /**
     * @see AbstractDelete#toDelete(ColumnMetadata, Map, String)
     */
    @Override
    public boolean toDelete(ColumnMetadata colMetadata, Map<String, String> parsedParameters, String value) {
        final RegexParametersHelper.ReplaceOnValueParameter replaceOnValueParameter = regexParametersHelper.build(parsedParameters.get(VALUE_PARAMETER));
        return replaceOnValueParameter.matches(value);
    }
}
