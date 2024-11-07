package jp.co.futech.module.system.job.demo;

import jp.co.futech.framework.tenant.core.job.TenantJob;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

@Component
public class DemoJob {

    @XxlJob("demoJob")
    @TenantJob
    public void execute() {
        System.out.println("美滋滋");
    }

}
