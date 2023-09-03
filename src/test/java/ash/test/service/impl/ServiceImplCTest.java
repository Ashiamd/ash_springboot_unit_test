package ash.test.service.impl;

import ash.test.AshSpringMVCTest;
import ash.test.dao.DaoTableC;
import ash.test.rpc.RpcClientC;
import jakarta.annotation.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.cglib.core.ReflectUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author : Ashiamd email: ashiamd@foxmail.com
 * @date : 2023/9/3 6:26 PM
 */
// 使用 PowerMockito 完成 private, static, final 方法 mock时
// 固定套路 @RunWith(PowerMockRunner.class) 和 @PrepareForTest(value = {需要mock的类.class})
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(value = {DaoTableC.class})
public class ServiceImplCTest extends AshSpringMVCTest {

    @InjectMocks
    @Resource
    private ServiceImplC serviceC;

    /**
     * RpcClientC 内所有方法不能正常执行, 直接 {@code @Mock} 使其所有方法默认返回null
     */
    @Mock
    private RpcClientC rpcClientC;

    /**
     * 用 {@code @Spy} 确保原本方法逻辑能执行，仅我们手动 Mock 时才有变化
     */
    @Spy
    @Resource
    private DaoTableC daoTableC;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void reset() {
        Mockito.reset(rpcClientC);
        Mockito.reset(daoTableC);
    }

    /**
     * 按照 Assert 的顺序 编号 <br/>
     * <p>
     * (1) return "null" <br/>
     * (2) return rpcData <br/>
     * </p>
     */
    @Test
    public void testServiceCMethod01() throws Exception {
        // (1) return "null"
        // PowerMockito 来 mock private 方法
//        PowerMockito.when(DaoTableC.class, "processData").thenReturn("1");
        Mockito.doReturn("1").when(daoTableC).selectColumnC(Mockito.anyString());
        Mockito.doReturn(null).when(rpcClientC).getColumnC(Mockito.anyLong());
        String case1Str = serviceC.serviceCMethod01("");
        Assert.assertEquals("null", case1Str);
        // (2) return rpcData
        Mockito.doReturn(" ").when(rpcClientC).getColumnC(Mockito.anyLong());
        String case2Str = serviceC.serviceCMethod01("");
        Assert.assertEquals(" ", case2Str);
    }

    @Test
    public void testServiceCMethod02() throws ClassNotFoundException, NoSuchMethodException {

    }

    @Test
    public void testServiceCMethod03() {
        Mockito.doReturn("1").when(rpcClientC).getColumnC(Mockito.anyList());
        String str = serviceC.serviceCMethod03(new ArrayList<>());
        Assert.assertEquals("1", str);
    }
}
