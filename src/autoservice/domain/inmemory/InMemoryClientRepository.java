package autoservice.domain.inmemory;

import autoservice.domain.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryClientRepository implements autoservice.domain.repository.ClientRepository {
    private final Map<Long, Client> clients = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Client save(Client client) {
        if (client.getClientId() == null) {
            client.setClientId(idCounter.getAndIncrement());
        }
        clients.put(client.getClientId(), client);
        return client;
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.ofNullable(clients.get(id));
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(clients.values());
    }

    @Override
    public void deleteById(Long id) {
        clients.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return clients.containsKey(id);
    }
}
