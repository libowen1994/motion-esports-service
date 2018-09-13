package one.motion.esports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "one.motion.esports.mapper")
public class MotionApplication {
    public static void main(String[] args) {
        SpringApplication.run(MotionApplication.class, args);
    }
}
