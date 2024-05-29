package smartphone.config.usecase.similarpriced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import smartphone.application.usecase.similarpriced.SimilarPricedFinderUseCase;
import smartphone.domain.similarpriced.SimilarPricedIdsQueries;
import smartphone.domain.similarpriced.SmartphoneDetailQueries;

@Configuration
public class SimilarPricedUseCaseConfig {

    private final SimilarPricedIdsQueries similarPricedIdsQueries;

    private final SmartphoneDetailQueries smartphoneDetailQueries;

    public SimilarPricedUseCaseConfig(SimilarPricedIdsQueries similarPricedIdsQueries, SmartphoneDetailQueries smartphoneDetailQueries) {
        this.similarPricedIdsQueries = similarPricedIdsQueries;
        this.smartphoneDetailQueries = smartphoneDetailQueries;
    }

    @Bean
    public SimilarPricedFinderUseCase smartphonesSimilarPricesFinderUseCase() {
        return new SimilarPricedFinderUseCase(similarPricedIdsQueries, smartphoneDetailQueries);
    }
}
