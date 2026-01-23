package online.yuuu.aiknowledgehub.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author yuuu
 */
@MappedJdbcTypes(JdbcType.OTHER)
@MappedTypes(Object.class)
public class PgVectorTypeHandler extends BaseTypeHandler<float[]> {

    @Override
    public void setNonNullParameter(
            PreparedStatement ps, int i, float[] parameter, JdbcType jdbcType
    ) throws SQLException {
        ps.setObject(i, Arrays.toString(parameter));
    }

    @Override public float[] getNullableResult(ResultSet rs, String columnName) { return null; }
    @Override public float[] getNullableResult(ResultSet rs, int columnIndex) { return null; }
    @Override public float[] getNullableResult(CallableStatement cs, int columnIndex) { return null; }
}
