package ash.test.service;

import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 5:21 PM
 */
public interface IServiceC {
    String serviceCMethod(String name);
    String serviceCMethod(Long id,String name);
    String serviceCMethod(List<Long> idList);
}
