package org.example.laboratoryappointment.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Enumeration;
import java.util.Optional;


//雪花算法生成ID
//Configuration标识一个配置类，Spring容器将其视为一个bean定义的来源
//配置类一般仅用于产生组件，本身不作为组件使用。
//以此@Configuration(proxyBeanMethods = false)
//配置类为应用程序提供配置信息，用来配置上下文JAVA类。用于生成和管理程序中的Bean
//一个配置类内可以用多个Bean
//Bean用在方法上方，表明方法返回一个对象，告诉Spring容器(以后简称容器)某个方法返回值需要被管理为一个Bean
//可以指定Bean的初始化和销毁方法作用在方法上
// 产生一个 Bean 对象，然后这个 Bean 对象交给 Spring 管理，剩下的你就不用管了。
// 产生这个 Bean 对象的方法 Spring 只会调用一次，随后这个 Spring 将会将这个 Bean 对象放在自己的 IOC 容器中。
//在没有声明@Configuration的普通类中，@Bean修饰方法依然会被扫描，单无法注入其他依赖组件使用
//EnableJdbcAuditing启用JPA审计功能：用于自动记录实体创建、更新和删除等操作信息的功能
//AuditorAware提供一种机制，让 Spring Data JPA 在审计过程中能够自动填充实体的创建者、修改者等字段。

@Configuration
@EnableJdbcAuditing
public class SnowflakeGenerator {
    @Bean
    AuditorAware<String> auditorAware() {
        Snowflake s = new Snowflake();
        return () -> Optional.of(String.valueOf(s.nextId()));
    }

    private static class Snowflake {
        private static final int UNUSED_BITS = 1; // Sign bit, Unused (always set to 0)
        private static final int EPOCH_BITS = 41;
        private static final int NODE_ID_BITS = 10;
        private static final int SEQUENCE_BITS = 12;

        private static final long maxNodeId = (1L << NODE_ID_BITS) - 1;
        private static final long maxSequence = (1L << SEQUENCE_BITS) - 1;

        // Custom Epoch (January 1, 2015 Midnight UTC = 2015-01-01T00:00:00Z)
        private static final long DEFAULT_CUSTOM_EPOCH = 1420070400000L;

        private final long nodeId;
        private final long customEpoch;

        private volatile long lastTimestamp = -1L;
        private volatile long sequence = 0L;

        // Create Snowflake with a nodeId and custom epoch
        public Snowflake(long nodeId, long customEpoch) {
            if(nodeId < 0 || nodeId > maxNodeId) {
                throw new IllegalArgumentException(String.format("NodeId must be between %d and %d", 0, maxNodeId));
            }
            this.nodeId = nodeId;
            this.customEpoch = customEpoch;
        }

        // Create Snowflake with a nodeId
        public Snowflake(long nodeId) {
            this(nodeId, DEFAULT_CUSTOM_EPOCH);
        }

        // Let Snowflake generate a nodeId
        public Snowflake() {
            this.nodeId = createNodeId();
            this.customEpoch = DEFAULT_CUSTOM_EPOCH;
        }

        public synchronized long nextId() {
            long currentTimestamp = timestamp();

            if(currentTimestamp < lastTimestamp) {
                throw new IllegalStateException("Invalid System Clock!");
            }

            if (currentTimestamp == lastTimestamp) {
                sequence = (sequence + 1) & maxSequence;
                if(sequence == 0) {
                    // Sequence Exhausted, wait till next millisecond.
                    currentTimestamp = waitNextMillis(currentTimestamp);
                }
            } else {
                // reset sequence to start with zero for the next millisecond
                sequence = 0;
            }

            lastTimestamp = currentTimestamp;

            long id = currentTimestamp << (NODE_ID_BITS + SEQUENCE_BITS)
                    | (nodeId << SEQUENCE_BITS)
                    | sequence;

            return id;
        }


        // Get current timestamp in milliseconds, adjust for the custom epoch.
        private long timestamp() {
            return Instant.now().toEpochMilli() - customEpoch;
        }

        // Block and wait till next millisecond
        private long waitNextMillis(long currentTimestamp) {
            while (currentTimestamp == lastTimestamp) {
                currentTimestamp = timestamp();
            }
            return currentTimestamp;
        }

        private long createNodeId() {
            long nodeId;
            try {
                StringBuilder sb = new StringBuilder();
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();
                    byte[] mac = networkInterface.getHardwareAddress();
                    if (mac != null) {
                        for(byte macPort: mac) {
                            sb.append(String.format("%02X", macPort));
                        }
                    }
                }
                nodeId = sb.toString().hashCode();
            } catch (Exception ex) {
                nodeId = (new SecureRandom().nextInt());
            }
            nodeId = nodeId & maxNodeId;
            return nodeId;
        }

        public long[] parse(long id) {
            long maskNodeId = ((1L << NODE_ID_BITS) - 1) << SEQUENCE_BITS;
            long maskSequence = (1L << SEQUENCE_BITS) - 1;

            long timestamp = (id >> (NODE_ID_BITS + SEQUENCE_BITS)) + customEpoch;
            long nodeId = (id & maskNodeId) >> SEQUENCE_BITS;
            long sequence = id & maskSequence;

            return new long[]{timestamp, nodeId, sequence};
        }

        @Override
        public String toString() {
            return "Snowflake Settings [EPOCH_BITS=" + EPOCH_BITS + ", NODE_ID_BITS=" + NODE_ID_BITS
                    + ", SEQUENCE_BITS=" + SEQUENCE_BITS + ", CUSTOM_EPOCH=" + customEpoch
                    + ", NodeId=" + nodeId + "]";
        }
    }
}
