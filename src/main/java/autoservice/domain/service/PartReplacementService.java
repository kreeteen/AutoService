package autoservice.domain.service;

import autoservice.domain.model.PartReplacement;
import autoservice.domain.repository.PartReplacementRepository;

import java.util.List;

public class PartReplacementService {
    private final PartReplacementRepository partReplacementRepository;

    public PartReplacementService(PartReplacementRepository partReplacementRepository) {
        this.partReplacementRepository = partReplacementRepository;
    }

    public PartReplacement addPartReplacement(Long orderId,
                                              String partName,
                                              String partNumber,
                                              int quantity) {
        PartReplacement replacement = new PartReplacement(null, orderId, partName, partNumber, quantity);
        return partReplacementRepository.save(replacement);
    }

    public List<PartReplacement> getReplacementsByOrder(Long orderId) {
        return partReplacementRepository.findByOrderId(orderId);
    }

    public List<PartReplacement> getAllReplacements() {
        return partReplacementRepository.findAll();
    }

    public void removeReplacementsFromOrder(Long orderId) {
        partReplacementRepository.deleteByOrderId(orderId);
    }
}