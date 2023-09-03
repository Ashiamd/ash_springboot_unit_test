package ash.test.rpc;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 模拟 RPC 请求操作 (请求其他项目里的服务)
 *
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 5:36 PM
 */
@Component
public class RpcClientC {

    public String getColumnC(Long id) {
        // 模拟 RPC 服务在 单测运行环境不可用的场景
        throw new RuntimeException("The RPC C is unavailable in the current environment");
    }

    public String getColumnC(Long id, String name) {
        // 模拟 RPC 服务在 单测运行环境不可用的场景
        throw new RuntimeException("The RPC C is unavailable in the current environment");
    }

    public String getColumnC(List<Long> idList) {
        // 模拟 RPC 服务在 单测运行环境不可用的场景
        throw new RuntimeException("The RPC C is unavailable in the current environment");
    }
}
