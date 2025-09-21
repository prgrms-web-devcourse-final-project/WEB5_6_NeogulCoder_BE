package grep.neogulcoder.global.config.mysql;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.query.ReturnableType;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.spi.SqlAppender;
import org.hibernate.sql.ast.tree.SqlAstNode;
import org.hibernate.type.BasicTypeReference;
import org.hibernate.type.SqlTypes;

import java.util.List;

public class FullTextFunction extends StandardSQLFunction {

    private static final BasicTypeReference<Double> RETURN_TYPE = new BasicTypeReference<>("double", Double.class, SqlTypes.DOUBLE);

    public FullTextFunction(String name) {
        super(name, true, RETURN_TYPE);
    }

    @Override
    public void render(SqlAppender sqlAppender, List<? extends SqlAstNode> sqlAstArguments, ReturnableType<?> returnType, SqlAstTranslator<?> translator) {
        if (sqlAstArguments.size() != 3) {
            throw new IllegalArgumentException("requires exactly 3 arguments");
        }

        sqlAppender.append("MATCH(");
        sqlAstArguments.get(0).accept(translator); // 첫번째 인자: 검색 대상 컬럼
        sqlAppender.append(", ");
        sqlAstArguments.get(1).accept(translator); // 두번째 인자: 또 다른 컬럼
        sqlAppender.append(") AGAINST (");
        sqlAstArguments.get(2).accept(translator); // 세번째 인자: 검색 키워드
        sqlAppender.append(" IN NATURAL LANGUAGE MODE)");
    }
}
