package ash.test.service.impl;

import ash.test.dao.DaoTableC;
import ash.test.rpc.RpcClientC;
import ash.test.service.IServiceC;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 5:22 PM
 */
@Service
public class ServiceImplC implements IServiceC {

    @Resource
    private DaoTableC daoTableC;

    @Resource
    private RpcClientC rpcClientC;

    @Override
    public String serviceCMethod01(String name) {
        String mysqlData = daoTableC.selectColumnC(name);
        String rpcData = rpcClientC.getColumnC(Long.parseLong(mysqlData));
        if (Objects.isNull(rpcData)) {
            return "null";
        }
        return rpcData;
    }

    @Override
    public String serviceCMethod02(Long id, String name) {
        String mysqlData = daoTableC.selectColumnC(id);
        String rpcData = rpcClientC.getColumnC(id, name);
        rpcData = processData01(rpcData);
        rpcData = processData02(rpcData);
        return id + rpcData;
    }

    @Override
    public String serviceCMethod03(List<Long> idList) {
        return rpcClientC.getColumnC(idList);
    }

    private String processData01(String rpcData) {
        throw new RuntimeException("processData01(String rpcData) is unavailable in the current environment");
    }

    private String processData02(String rpcData) {
        throw new RuntimeException("processData02(String rpcData) is unavailable in the current environment");
    }
}
