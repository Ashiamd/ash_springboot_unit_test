package ash.test.service.impl;

import ash.test.AshSpringMVCTest;
import ash.test.dao.DaoTableA;
import ash.test.rpc.RpcClientA;
import org.junit.After;
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

    /**
     * {@code @Before} 每个测试方法执行前执行一次
     */
    @Before
    public void init() {
        // 固定套路, 当前测试类使用到 {@code @InjectMocks} 时需要 initMocks
        MockitoAnnotations.initMocks(this);
    }

    /**
     * {@code @After} 每个测试方法执行结束后执行一次 <br/>
     * 通常在这里调用{@code Mockito.reset(Mock得到的对象)} 来重制原本对"Mock得到的对象"的所有设置(如 doReturn等) <br/>
     * 通常建议把所有 Mock 相关的对象都重置一遍, 避免一个Test方法修改了Mock对象的某些成员变量值 导致后续的 Test方法无法正常执行(或受到影响) <br/>
     */
    @After
    public void reset() {
        // 每个Test方法直接结束后, 重置Mock对象
        // @InjectMocks 不算 Mock对象, 会报错
        // Mockito.reset(serviceA);
        Mockito.reset(daoTableA);
        Mockito.reset(rpcClientA);
    }


    /**
     * <p>
     * 按照 Assert 语句执行编号 <br/>
     * (1) 因为 daoTableA 标注 @Mock, 所以默认调用其所有方法都返回null, 所以 {@code daoTableA.selectColumnA(id)} 返回 null.
     * 而 rpcClientA 标注 @Spy, 默认所有方法按照原本形式执行, 所以 getColumnAData(id) 抛出异常 <br/>
     * (2) @Spy 注释的 rpcClientA, Mock其方法, 让其不实际执行内部方法逻辑, 而是直接返回一个 null值 <br/>
     * (3) @Spy 注释的 rpcClientA, Mock其方法, 让其不实际执行内部方法逻辑, 而是直接返回 "columnA" <br/>
     * (4) @Mock 注释的 daoTableA, Mock其方法, 使其返回 "123456" <br/>
     * (5) @Mock 注释的 daoTableA, Mock其方法, 使其返回 "123" <br/>
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
        // (4) daoTableA.selectColumnA(指定Long值) 返回 "123456";
        // 这里 when(Mock对象.方法(参数)).thenReturn(指定返回值), 会先实际执行 selectColumnA 内逻辑, 然后替换返回值为指定值再返回
        Mockito.when(daoTableA.selectColumnA(Mockito.eq(2L))).thenReturn("123456");
        String case4Str = serviceA.serviceAMethod01(2L);
        Assert.assertEquals("123456, columnA.length() > 5", case4Str);
        // (5) daoTableA.selectColumnA(指定Long值) 返回 "123";
        Mockito.when(daoTableA.selectColumnA(Mockito.eq(2L))).thenReturn("123");
        String case5Str = serviceA.serviceAMethod01(2L);
        Assert.assertEquals("123 , processColumnA02", case5Str);
    }

    /**
     * 按照 Assert 语句执行编号 <br/>
     * <p>
     * (1) name 为null的情况 <br/>
     * (2) name = '' 的情况 <br/>
     * (3) name = ' ' 的情况 <br/>
     * (4) name 不为 空白的情况 <br/>
     * </p>
     */
    @Test
    public void testServiceAMethod02() {
        // (1) name 为null的情况
        String case1Str = serviceA.serviceAMethod02(null);
        Assert.assertEquals("serviceAMethod02(String name), name is null", case1Str);
        // (2) name = '' 的情况
        String case2Str = serviceA.serviceAMethod02("");
        Assert.assertEquals("serviceAMethod02(String name), name is ''", case2Str);
        // (3) name = ' ' 的情况
        String case3Str = serviceA.serviceAMethod02(" ");
        Assert.assertEquals("serviceAMethod02(String name), name is blank", case3Str);
        // (4) name 不为 空白的情况
        String case4Str = serviceA.serviceAMethod02("not Blank");
        Assert.assertEquals("not Blank", case4Str);
    }

    /**
     * 按照 Assert 语句执行编号 <br/>
     * <p>
     * (1) id = null <br/>
     * (2) name = null <br/>
     * (3) name = ' ' <br/>
     * (4) Mock rpcClientA.getColumnAData, 以覆盖 processColumnA03 方法 <br/>
     * </p>
     */
    @Test
    public void serviceAMethod03() {
        // (1) id = null
        String case1Str = serviceA.serviceAMethod03(null, "");
        Assert.assertEquals("getColumnAData(String name), name is null or ''", case1Str);
        // (2) name = null
        String case2Str = serviceA.serviceAMethod03(1L, null);
        Assert.assertEquals("getColumnAData(String name), name is null or ''", case2Str);
        // (3) name = ' '
        String case3Str = serviceA.serviceAMethod03(1L, " ");
        Assert.assertEquals("1 ", case3Str);
        // (4) Mock rpcClientA.getColumnAData, 以覆盖 processColumnA03 方法
        Mockito.doReturn("").when(rpcClientA).getColumnAData(Mockito.anyString());
        String case4Str = serviceA.serviceAMethod03(null, "123");
        Assert.assertEquals("processColumnA03, columnA is null or ''", case4Str);
    }
}
