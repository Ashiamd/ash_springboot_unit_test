package ash.test.service;

import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 5:20 PM
 */
public interface IServiceB {
    String serviceBMethod01(Long id);
    String serviceBMethod02(String name);
    String serviceBMethod03(List<Long> idList);
}
