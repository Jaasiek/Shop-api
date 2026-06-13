package org.technischools.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
    @Bean
    @Scope("prototype")
    public InvoiceNumberGenerator invoiceNumberGenerator() {
        return new InvoiceNumberGenerator();
    }

    @Bean
    public PriceFormater priceFormatter() {
        return new PriceFormater();
    }
}
