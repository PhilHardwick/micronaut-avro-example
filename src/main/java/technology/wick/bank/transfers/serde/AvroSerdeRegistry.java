package technology.wick.bank.transfers.serde;

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.micronaut.configuration.kafka.serde.SerdeRegistry;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.serialization.Serde;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Map;

public class AvroSerdeRegistry implements SerdeRegistry {

    @Inject
    private SchemaRegistryClient schemaRegistryClient;

    @SuppressWarnings("unchecked")
    @Override
    public <T> Serde<T> getSerde(Class<T> type) {
        if (Arrays.asList(type.getInterfaces()).contains(SpecificRecord.class)) {
            SpecificAvroSerde serde = new SpecificAvroSerde(schemaRegistryClient);
            Map<String, String> config = Map.of(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true",
                    AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "");
            serde.configure(config, false);
            return (Serde<T>) serde;
        }
        return null;
    }

    @Override
    public int getOrder() {
        // Before JSON Serde
        return -1;
    }
}
