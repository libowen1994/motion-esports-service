package one.motion.esports.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    public final static String ESPORTS_EXCHANGE_TOPIC = "esports_exchange_topic";
    public final static String ESPORTS_EXCHANGE_NOTIFY_TOPIC = "esports_exchange_notify_topic";

    @Value("${spring.kafka.bootstrap-servers}")
    private String brokerAddresses;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddresses);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(ESPORTS_EXCHANGE_TOPIC, 10, (short) 2);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(ESPORTS_EXCHANGE_NOTIFY_TOPIC, 10, (short) 2);
    }
}
