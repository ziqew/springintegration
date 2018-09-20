package com.sgi.springintegrationapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringintegrationAppApplication {

	/*public static final String TEST_TOPIC_ID = "event-stream";

	@Component
	public static class KafkaConfig {

		@Value("${kafka.topic:" + TEST_TOPIC_ID + "}")
		private String topic;

		@Value("${kafka.address:localhost:9092}")
		private String brokerAddress;

		@Value("${zookeeper.address:localhost:2181}")
		private String zookeeperAddress;

		KafkaConfig() {
		}

		public KafkaConfig(String t, String b, String zk) {
			this.topic = t;
			this.brokerAddress = b;
			this.zookeeperAddress = zk;
		}

		public String getTopic() {
			return topic;
		}

		public String getBrokerAddress() {
			return brokerAddress;
		}

		public String getZookeeperAddress() {
			return zookeeperAddress;
		}
	}

	@Configuration
	public static class ProducerConfiguration {

		@Autowired
		private KafkaConfig kafkaConfig;

		private static final String OUTBOUND_ID = "outbound";

		private Log log = LogFactory.getLog(getClass());

		@Bean
		@DependsOn(OUTBOUND_ID)
		CommandLineRunner kickOff(
				@Qualifier(OUTBOUND_ID + ".input") MessageChannel in) {
			return args -> {
				for (int i = 0; i < 1000; i++) {
					in.send(new GenericMessage<>("#" + i));
					log.info("sending message #" + i);
				}
			};
		}

		@Bean(name = OUTBOUND_ID)
		IntegrationFlow producer() {

			log.info("starting producer flow..");
			return flowDefinition -> {

				Consumer<KafkaProducerMessageHandlerSpec.ProducerMetadataSpec> spec =
						(KafkaProducerMessageHandlerSpec.ProducerMetadataSpec metadata)->
								metadata.async(true)
										.batchNumMessages(10)
										.valueClassType(String.class)
										.<String>valueEncoder(String::getBytes);

				KafkaProducerMessageHandlerSpec messageHandlerSpec =
						Kafka.outboundChannelAdapter(
								props -> props.put("queue.buffering.max.ms", "15000"))
								.messageKey(m -> m.getHeaders().get(IntegrationMessageHeaderAccessor.SEQUENCE_NUMBER))
								.addProducer(this.kafkaConfig.getTopic(),
										this.kafkaConfig.getBrokerAddress(), spec);
				flowDefinition
						.handle(messageHandlerSpec);
			};
		}
	}

	@Configuration
	public static class ConsumerConfiguration {

		@Autowired
		private KafkaConfig kafkaConfig;

		private Log log = LogFactory.getLog(getClass());

		@Bean
		IntegrationFlow consumer() {

			log.info("starting consumer..");

			KafkaHighLevelConsumerMessageSourceSpec messageSourceSpec = Kafka.inboundChannelAdapter(
					new ZookeeperConnect(this.kafkaConfig.getZookeeperAddress()))
					.consumerProperties(props ->
							props.put("auto.offset.reset", "smallest")
									.put("auto.commit.interval.ms", "100"))
					.addConsumer("myGroup", metadata -> metadata.consumerTimeout(100)
							.topicStreamMap(m -> m.put(this.kafkaConfig.getTopic(), 1))
							.maxMessages(10)
							.valueDecoder(String::new));

			Consumer<SourcePollingChannelAdapterSpec> endpointConfigurer = e -> e.poller(p -> p.fixedDelay(100));

			return IntegrationFlows
					.from(messageSourceSpec, endpointConfigurer)
					.<Map<String, List<String>>>handle((payload, headers) -> {
						payload.entrySet().forEach(e -> log.info(e.getKey() + '=' + e.getValue()));
						return null;
					})
					.get();
		}
	}
*/
	public static void main(String[] args) {
		SpringApplication.run(SpringintegrationAppApplication.class, args);
	}
}
