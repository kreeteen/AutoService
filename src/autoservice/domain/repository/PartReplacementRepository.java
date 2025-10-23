package autoservice.domain.repository;

import autoservice.domain.model.PartReplacement;

import java.util.List;

public interface PartReplacementRepository {
    PartReplacement save(PartReplacement partReplacement);
    List<PartReplacement> findAll();
    List<PartReplacement> findByOrderId(Long orderId);
    void deleteByOrderId(Long orderId);
}
