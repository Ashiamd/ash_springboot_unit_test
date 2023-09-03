package ash.test.dao;

import org.springframework.stereotype.Repository;

/**
 * 模拟 数据库(数据存储介质) 操作
 *
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 5:24 PM
 */
@Repository
public class DaoTableC {
    public String selectColumnC(Long id) {
        return "selectColumnC(Long id)";
    }

    public String selectColumnC(String name) {
        return processData(name);
    }

    private String processData(String data) {
        return "processData(String data), data: " + data;
    }
}
