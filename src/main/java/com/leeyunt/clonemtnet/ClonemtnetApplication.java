package com.leeyunt.clonemtnet;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableSwaggerBootstrapUI
@SpringBootApplication
@MapperScan("com.leeyunt.clonemtnet.dao")  //自动扫描路径下的所有mapper
public class ClonemtnetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClonemtnetApplication.class, args);
    }

}
