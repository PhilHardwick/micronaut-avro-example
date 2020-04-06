package technology.wick.bank.transfers.streams;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import technology.wick.bank.transfers.MakeTransfer;
import technology.wick.bank.transfers.TransferEvent;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import static io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

@Factory
public class TransferStreamFactory {
    public static final String BANK_TRANSFER = "bank-transfer";
    public static final String INPUT = "transfer-commands";
    public static final String OUTPUT = "transfer-events";


    @Singleton
    @Named(BANK_TRANSFER)
    KStream<UUID, MakeTransfer> bankTransferStream(ConfiguredStreamBuilder builder) {
        Properties props = builder.getConfiguration();
        Map<String, Object> serdeConfig = Map.of(SCHEMA_REGISTRY_URL_CONFIG, props.get(SCHEMA_REGISTRY_URL_CONFIG));
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.UUID().getClass().getName());
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class.getName());
        SpecificAvroSerde<MakeTransfer> inputValueSerde = new SpecificAvroSerde<>();
        inputValueSerde.configure(serdeConfig, false);
        SpecificAvroSerde<TransferEvent> outputValueSerde = new SpecificAvroSerde<>();
        outputValueSerde.configure(serdeConfig, false);
        KStream<UUID, MakeTransfer> source = builder.stream(INPUT, Consumed.with(Serdes.UUID(), inputValueSerde));
        source.mapValues(value -> TransferEvent.newBuilder()
                        .setTransferId(UUID.randomUUID())
                        .setSrcAccountId(value.getSrcAccountId())
                        .setDestAccountId(value.getDestAccountId())
                        .setAmount(value.getAmount())
                        .build())
                .to(OUTPUT, Produced.with(Serdes.UUID(), outputValueSerde));
        return source;
    }
}
