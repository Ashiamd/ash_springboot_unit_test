package ash.test.service;

import java.util.List;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 5:21 PM
 */
public interface IServiceC {
    String serviceCMethod01(String name);
    String serviceCMethod02(Long id,String name);
    String serviceCMethod03(List<Long> idList);
}
