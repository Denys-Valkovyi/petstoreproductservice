package io.swagger.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${keyvault.connectionstringsecret}")
    private String connectionStringSecretName;

    @Value("${keyvault.url}")
    private String keyVaultUrl;

    @Bean
    public DataSource getDataSource() {
        log.info("Key Vault URL: {}, connection string secret name: {}", keyVaultUrl, connectionStringSecretName);
        SecretClient secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUrl)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
        KeyVaultSecret retrievedSecret = secretClient.getSecret(connectionStringSecretName);

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(retrievedSecret.getValue());
        return dataSourceBuilder.build();
    }
}