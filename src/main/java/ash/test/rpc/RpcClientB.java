package ash.test.rpc;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 模拟 RPC 请求操作 (请求其他项目里的服务)
 *
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 5:36 PM
 */
@Component
public class RpcClientB {

    @Resource
    private RpcClientC rpcClientC;

    public String getColumnB(Long id) {
        String rpcData = rpcClientC.getColumnC(id);
        return id + rpcData;
    }
}
