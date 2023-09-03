package ash.test.service.impl;

import ash.test.AshSpringMVCTest;
import ash.test.dao.DaoTableB;
import ash.test.rpc.RpcClientB;
import ash.test.rpc.RpcClientC;
import jakarta.annotation.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 6:26 PM
 */
public class ServiceImplBTest extends AshSpringMVCTest {

    @InjectMocks
    @Resource
    private ServiceImplB serviceB;

    /**
     * 这里用 {@code @Spy} 而不是 {@code @Mock}, 确保需要用 daoTableB 原本逻辑的 方法能正常运行 <br/>
     * 用 {@code @Mock}的话, 则所有 daoTableB的方法都需要 Mock, 否则默认返回null <br/>
     */
    @Spy
    @Resource
    private DaoTableB daoTableB;

    /**
     * 注意这里 rpcClientB 要 {@code @Resource}, 否则 rpcClientC 的 {@code @Mock} 就不生效 <br/>
     * 因为 @Resource 取的是 SpringMVC中注册的 Bean实例, 此时 {@code @Mock}注解的 rpcClientC 才会替换
     * {@code @Component} 注解的 rpcClientB 中的 rpcClientC 成员变量 <br/>
     */
    @InjectMocks
    @Resource
    private RpcClientB rpcClientB;

    @Mock
    private RpcClientC rpcClientC;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void reset() {
        Mockito.reset(rpcClientC);
    }

    @Test
    public void testServiceBMethod01() {
        Mockito.doReturn("").when(rpcClientC).getColumnC(Mockito.anyLong());
        String str = serviceB.serviceBMethod01(1L);
        Assert.assertEquals("1", str);
    }

    /**
     * 按照 Assert 的顺序编号 <br/>
     * <p>
     * (1) Mock daoTableB.selectColumnB() 返回值 为 null <br/>
     * (2) Mock daoTableB.selectColumnB() 返回值 为 " " <br/>
     * (3) daoTableB.selectColumnB() 原值返回 <br/>
     * </p>
     */
    @Test
    public void testServiceBMethod02() {
        // (1) Mock daoTableB.selectColumnB() 返回值 为 null
        Mockito.doReturn(null).when(daoTableB).selectColumnB(Mockito.anyString());
        String case1Str = serviceB.serviceBMethod02("");
        Assert.assertEquals("null", case1Str);
        // (2) Mock daoTableB.selectColumnB() 返回值 为 " "
        Mockito.doReturn(" ").when(daoTableB).selectColumnB(Mockito.anyString());
        String case2Str = serviceB.serviceBMethod02("");
        Assert.assertEquals("blank", case2Str);
        // (3) daoTableB.selectColumnB() 原值返回
        // 注意这里 Mockito.reset(daoTableB), 使下面能 执行 daoTableB 原本的方法逻辑, 而不是上面mock的逻辑
        Mockito.reset(daoTableB);
        String case3Str = serviceB.serviceBMethod02("");
        Assert.assertEquals("selectColumnB(String name)", case3Str);
    }

    /**
     * 按照 Assert 的顺序编号 <br/>
     * <p>
     * (1) Mock daoTableB.selectColumnBList() 返回值 为 null <br/>
     * (2) daoTableB.selectColumnB() 原值返回 <br/>l
     * </p>
     */
    @Test
    public void testServiceBMethod03() {
        // (1) Mock daoTableB.selectColumnBList() 返回值 为 null
        Mockito.doReturn(null).when(daoTableB).selectColumnBList(Mockito.anyList());
        String case1Str = serviceB.serviceBMethod03(new ArrayList<>());
        Assert.assertEquals("null", case1Str);
        // (2) daoTableB.selectColumnB() 原值返回
        // reset 使 daoTableB 能继续执行原本的方法逻辑
        Mockito.reset(daoTableB);
        String case2Str = serviceB.serviceBMethod03(null);
        Assert.assertEquals("dataList.size(): 1", case2Str);
    }
}
