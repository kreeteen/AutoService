package autoservice.domain.inmemory;

import autoservice.domain.model.PartReplacement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryPartReplacementRepository implements autoservice.domain.repository.PartReplacementRepository {
    private final Map<Long, PartReplacement> replacements = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public PartReplacement save(PartReplacement partReplacement) {
        if (partReplacement.getReplacementId() == null) {
            partReplacement.setReplacementId(idCounter.getAndIncrement());
        }
        replacements.put(partReplacement.getReplacementId(), partReplacement);
        return partReplacement;
    }

    @Override
    public List<PartReplacement> findAll() {
        return new ArrayList<>(replacements.values());
    }

    @Override
    public List<PartReplacement> findByOrderId(Long orderId) {
        return replacements.values().stream()
                .filter(pr -> orderId.equals(pr.getOrderId()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        replacements.entrySet().removeIf(entry -> orderId.equals(entry.getValue().getOrderId()));
    }
}
