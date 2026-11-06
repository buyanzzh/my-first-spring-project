package com.zzh;

import com.zzh.service.DemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// properties = 给测试环境塞一个【测试专用假密钥】(仅本测试用，不是真实密钥!)
// 解决：jwt.secret 引用环境变量 ${JWT_SECRET}，测试启动读不到 → jwtUtil Bean 创建失败
@SpringBootTest(properties = "jwt.secret=test-only-secret-key-for-demo-0123456789abcdef")
public class DemoTransactionTest {

    @Autowired
    private DemoService demoService;   // 注意：这个变量里装的是"代理壳"，不是原始对象

    // 对照组：从外面直接调 b —— 走代理壳，标签应该兑现
    @Test
    public void testA() {
        try {
            demoService.b("A-直接调");
        } catch (Exception e) {
            System.out.println("A 炸了（这是预期的）：" + e.getMessage());
        }
    }

    // 实验组：调 a —— a 内部自己 this.b，绕开代理壳
    @Test
    public void testB() {
        try {
            demoService.a();
        } catch (Exception e) {
            System.out.println("B 炸了（这是预期的）：" + e.getMessage());
        }
    }
    @Test
    public void testC() {
        demoService.demoCatch();
        System.out.println("方法正常结束了，没有炸");
    }

}
