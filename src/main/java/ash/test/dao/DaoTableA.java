package ash.test.dao;

import org.springframework.stereotype.Repository;

/**
 * 模拟 数据库(数据存储介质) 操作
 *
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 5:23 PM
 */
@Repository
public class DaoTableA {
    public String selectColumnA(Long id) {
        return "selectColumnA(Long id), param: " + id;
    }

    public String selectColumnA(String name) {
        return "selectColumnA(String name)";
    }

    public String selectColumnA(Long id, String name) {
        return "selectColumnA(Long id, String name)";
    }
}
