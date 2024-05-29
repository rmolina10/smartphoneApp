package smartphone.infrastructure.rest.spring.mapper;


import org.mapstruct.Mapper;
import smartphone.domain.similarpriced.model.SmartphoneDetail;
import smartphone.smartphone.infrastucture.rest.spring.spring.dto.SmartphoneDetailWebDto;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface SimilarPricedMapper {

    SmartphoneDetailWebDto toDto(SmartphoneDetail smartphoneDetail);

    Set<SmartphoneDetailWebDto> toDto(Set<SmartphoneDetail> smartphoneDetailList);
}
