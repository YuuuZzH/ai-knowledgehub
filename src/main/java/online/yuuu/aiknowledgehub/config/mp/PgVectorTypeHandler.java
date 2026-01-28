package online.yuuu.aiknowledgehub.config.mp;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

/**
 * @author yuuu
 */
@MappedJdbcTypes(JdbcType.OTHER)
@MappedTypes(Object.class)
public class PgVectorTypeHandler extends BaseTypeHandler<float[]> {

    @Override
    public void setNonNullParameter(
            PreparedStatement ps,
            int i,
            float[] parameter,
            JdbcType jdbcType
    ) throws SQLException {

        StringBuilder sb = new StringBuilder("[");
        for (int idx = 0; idx < parameter.length; idx++) {
            if (idx > 0) {
                sb.append(",");
            }
            sb.append(parameter[idx]);
        }
        sb.append("]");

        ps.setObject(i, sb.toString(), Types.OTHER);
    }

    @Override
    public float[] getNullableResult(ResultSet rs, String columnName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float[] getNullableResult(ResultSet rs, int columnIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float[] getNullableResult(CallableStatement cs, int columnIndex) {
        throw new UnsupportedOperationException();
    }
}
