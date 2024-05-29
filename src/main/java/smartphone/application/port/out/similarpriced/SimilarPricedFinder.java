package smartphone.application.port.out.similarpriced;

import lombok.NonNull;
import smartphone.domain.similarpriced.model.SmartphoneDetail;

import java.util.Set;

public interface SimilarPricedFinder {

    Set<SmartphoneDetail> findSimilarPriced(@NonNull String id);
}
