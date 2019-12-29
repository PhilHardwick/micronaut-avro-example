package technology.wick.bank.transfers.registry;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Replaces;

import javax.inject.Singleton;

@Factory
@Replaces(factory = SchemaRegistryClientFactory.class)
public class MockSchemaRegistryFactory {

    @Singleton
    SchemaRegistryClient schemaRegistryClient() {
        return new MockSchemaRegistryClient();
    }

}
