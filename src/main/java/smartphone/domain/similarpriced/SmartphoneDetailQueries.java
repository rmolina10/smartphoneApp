package smartphone.domain.similarpriced;

import reactor.core.publisher.Flux;
import smartphone.domain.similarpriced.model.SmartphoneDetail;

public interface SmartphoneDetailQueries {

    Flux<SmartphoneDetail> findSmartphoneDetail(Flux<Long> smartphoneIds);
}
