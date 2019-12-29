package technology.wick.bank.transfers.registry;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;

@Factory
public class SchemaRegistryClientFactory {

    @Property(name = "kafka.schema.registry.url")
    private String schemaRegistryUrl;
    @Value("${kafka.schema.registry.schema-versions:1000}")
    private int maxSchemaVersions;

    @Singleton
    public SchemaRegistryClient schemaRegistryClient() {
        return new CachedSchemaRegistryClient(schemaRegistryUrl, maxSchemaVersions);
    }

}