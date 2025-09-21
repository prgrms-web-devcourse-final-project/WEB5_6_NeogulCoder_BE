package grep.neogulcoder.global.config.mysql;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.StandardBasicTypes;

public class MySqlContributor implements FunctionContributor {

    public static final String FUNCTION_NAME = "match";
    public static final String FUNCTION_PATTERN = "match (?1, ?2) against (?3 IN NATURAL LANGUAGE MODE)";

    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        functionContributions.getFunctionRegistry().registerPattern(
                FUNCTION_NAME, FUNCTION_PATTERN,
                functionContributions.getTypeConfiguration()
                        .getBasicTypeRegistry()
                        .resolve(StandardBasicTypes.DOUBLE)
        );
    }
}
