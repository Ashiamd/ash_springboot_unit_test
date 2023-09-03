package ash.test.service.impl;

import ash.test.dao.DaoTableB;
import ash.test.rpc.RpcClientB;
import ash.test.service.IServiceB;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 5:21 PM
 */
@Service
public class ServiceImplB implements IServiceB {

    @Resource
    private DaoTableB daoTableB;

    @Resource
    private RpcClientB rpcClientB;

    @Override
    public String serviceBMethod01(Long id) {
        return rpcClientB.getColumnB(id);
    }

    @Override
    public String serviceBMethod02(String name) {
        String mysqlData = daoTableB.selectColumnB(name);
        return processDataB(mysqlData);
    }

    @Override
    public String serviceBMethod03(List<Long> idList) {
        List<String> dataList = daoTableB.selectColumnBList(idList);
        if (Objects.isNull(dataList)) {
            return "null";
        }
        return "dataList.size(): " + dataList.size();
    }

    private static String processDataB(String columnB) {
        if (Objects.isNull(columnB)) {
            return "null";
        }
        if (columnB.isBlank()) {
            return "blank";
        }
        return "processDataB(String columnB)";
    }
}
