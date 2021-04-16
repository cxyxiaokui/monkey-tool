package cn.zqmy.monkeytool.mybatis.db;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;

import java.sql.*;

/**
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
public class ConvertTypeHandler extends BaseTypeHandler<Object> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object obj, JdbcType jdbcType) throws SQLException {
        if (jdbcType == JdbcType.INTEGER || jdbcType == JdbcType.BIGINT) {
            long value;
            try {
                value = Long.parseLong(obj.toString());
            } catch (NumberFormatException e) {
                throw new TypeException(e);
            }
            ps.setLong(i, value);
        } else if (jdbcType == JdbcType.DATE) {
            Date date = new Date(Long.parseLong(obj.toString()));
            ps.setDate(i, date);
        } else {
            ps.setObject(i, obj);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int i) throws SQLException {
        return rs.getObject(i);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int i) throws SQLException {
        return cs.getObject(i);
    }
}
