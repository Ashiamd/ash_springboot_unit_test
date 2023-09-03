package ash.test.service.impl;

import ash.test.AshSpringMVCTest;
import ash.test.dao.DaoTableA;
import ash.test.rpc.RpcClientA;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 6:26 PM
 */
public class ServiceImplATest extends AshSpringMVCTest {

    /**
     * {@code @InjectMocks}注释的成员变量 为我们需要测试的主体, <br/>
     * 当前测试类中 所有{@code @Mock} 和 {@code @spy} 注释的成员变量都将 替代 @InjectMocks 内原有的成员变量 <br/>
     */
    @InjectMocks
    private ServiceImplA serviceA;

    /**
     * {@code @Mocks} 所注释的类, 替代 {@code @InjectMocks} 对应的成员变量 <br/>
     * 没有mock相关的方法时, 其默认所有方法返回null
     */
    @Mock
    private DaoTableA daoTableA;

    /**
     * {@code @Spy 所注释的类, 替代 {@code @InjectMocks} 对应的成员变量 <br/>
     * 没有mock相关的方法时, 其默认所有方法按照原本的形式执行
     */
    @Spy
    private RpcClientA rpcClientA;

    @Before
    public void init() {
        // 固定套路, 当前测试类使用到 {@code @InjectMocks} 时需要 initMocks
        MockitoAnnotations.initMocks(this);
    }


    /**
     * <p>
     * 按照 Assert 语句执行编号 <br/>
     * (1) 因为 daoTableA 标注 @Mock, 所以默认调用其所有方法都返回null, 所以 {@code daoTableA.selectColumnA(id)} 返回 null.
     * 而 rpcClientA 标注 @Spy, 默认所有方法按照原本形式执行, 所以 getColumnAData(id) 抛出异常 <br/>
     * (2) @Spy 注释的 rpcClientA, Mock其方法, 让其不实际执行内部方法逻辑, 而是直接返回一个 null值 <br/>
     * (3) @Spy 注释的 rpcClientA, Mock其方法, 让其不实际执行内部方法逻辑, 而是直接返回 "columnA" <br/>
     * </p>
     */
    @Test
    public void testServiceBMethod01() {
        try {
            serviceA.serviceAMethod01(1L);
        } catch (Exception e) {
            // (1) @Spy 注释的 rpcClientA 默认不mock则执行其原本的内部方法逻辑, 所以会抛出异常
            Assert.assertTrue(e.getMessage().contains("The RPC A is unavailable in the current environment"));
        }
        // (2) rpcClientA.getColumnAData(任意Long值) 返回null
        // 这里 doReturn(指定的返回值).when(@Mock的字段或@Spy的字段).字段方法(参数)
        // 实际不会执行 @Mock或@Spy字段的方法内部逻辑, 而是遇到对应方法将要被调用时, 直接返回 指定返回值
        Mockito.doReturn(null).when(rpcClientA).getColumnAData(Mockito.anyLong());
        String case2Str = serviceA.serviceAMethod01(1L);
        Assert.assertEquals("processColumnA01, columnA is null or ''", case2Str);
        // (3) rpcClientA.getColumnAData(任意Long值) 返回 "columnA"
        Mockito.doReturn("columnA").when(rpcClientA).getColumnAData(Mockito.anyLong());
        String case3Str = serviceA.serviceAMethod01(1L);
        Assert.assertEquals("columnA , processColumnA01", case3Str);
        // (4)
    }
}
