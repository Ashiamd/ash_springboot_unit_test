package ash.test.service.impl;

import ash.test.dao.DaoTableA;
import ash.test.rpc.RpcClientA;
import ash.test.service.IServiceA;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 5:21 PM
 */
@Service
public class ServiceImplA implements IServiceA {

    @Resource
    private DaoTableA daoTableA;

    @Resource
    private RpcClientA rpcClientA;

    @Override
    public String serviceAMethod01(Long id) {
        // 1. 查询 MySQL
        String mysqlResult = daoTableA.selectColumnA(id);
        // 2. 如果 MySQL数据为空, 则继续 RPC请求获取数据
        if (Objects.isNull(mysqlResult) || mysqlResult.length() == 0) {
            String rpcData = rpcClientA.getColumnAData(id);
            // private 方法处理后返回
            return processColumnA01(rpcData);
        }
        // 3. MySQL 有数据, static 方法处理后返回
        return processColumnA02(mysqlResult);
    }

    @Override
    public String serviceAMethod02(String name) {
        if (Objects.isNull(name)) {
            return "serviceAMethod02(String name), name is null";
        }
        if (name.length() == 0) {
            return "serviceAMethod02(String name), name is ''";
        }
        if (name.isBlank()) {
            return "serviceAMethod02(String name), name is blank";
        }
        return name;
    }

    @Override
    public String serviceAMethod03(Long id, String name) {
        try {
            if (Objects.isNull(id)) {
                throw new RuntimeException("id can't be null");
            }
            if (Objects.isNull(name)) {
                throw new RuntimeException("name can't be null");
            }
            return rpcClientA.getColumnAData(id, name);
        } catch (Exception e) {
            String rpcData = rpcClientA.getColumnAData(name);
            return processColumnA03(rpcData);
        }
    }

    private String processColumnA01(String columnA) {
        if (Objects.isNull(columnA) || columnA.length() == 0) {
            return "processColumnA01, columnA is null or ''";
        }
        return columnA + " , processColumnA01";
    }

    public static String processColumnA02(String columnA) {
        if (columnA.length() > 5) {
            return columnA + ", columnA.length() > 5";
        }
        return columnA + " , processColumnA02";
    }

    public final String processColumnA03(String columnA) {
        if (Objects.isNull(columnA) || columnA.length() == 0) {
            return "processColumnA03, columnA is null or ''";
        }
        return columnA;
    }
}
