package ash.test.dao;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * 模拟 数据库(数据存储介质) 操作
 *
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 5:23 PM
 */
@Repository
public class DaoTableB {
    public List<String> selectColumnBList(List<Long> idList) {
        return Collections.singletonList("selectColumnBList(List<Long> idList)");
    }

    public String selectColumnB(String name) {
        return "selectColumnB(String name)";
    }
}
