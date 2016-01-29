package ${package};

import com.dianping.csc.common.service.dao.SqlMapDao;
import ${entity};
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
* Created by csophys with template on ${.now?date}.
*/
public interface ${daoSimple} extends SqlMapDao {

    @Select("select * from ${entitySimple} where id = ${r'#{id}'}")
    ${entitySimple} get(int id);

    @Delete("delete from ${entitySimple} where id = ${r'#{id}'}")
    int delete(int id);

    int insert(${entitySimple} e);

    int update(${entitySimple} e);
}
