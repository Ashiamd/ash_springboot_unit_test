package ash.test.rpc;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 模拟 RPC 请求操作 (请求其他项目里的服务)
 *
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 5:36 PM
 */
@Component
public class RpcClientA {
    public String getColumnAData(Long id) {
        // 模拟 RPC 服务在 单测运行环境不可用的场景
        throw new RuntimeException("The RPC A is unavailable in the current environment");
    }

    public String getColumnAData(String name) {
        if (Objects.isNull(name) || name.length() == 0) {
            return "getColumnAData(String name), name is null or ''";
        }
        return name;
    }

    public String getColumnAData(Long id, String name) {
        return id + name;
    }
}
