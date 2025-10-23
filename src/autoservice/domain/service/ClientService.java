package autoservice.domain.service;

import autoservice.domain.model.Client;
import autoservice.domain.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(String firstName, String lastName, String middleName, String phone) {
        Client client = new Client(null, firstName, lastName, middleName, phone);
        return clientRepository.save(client);
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public boolean updateClient(Long id, String firstName, String lastName, String middleName, String phone) {
        Optional<Client> existingClient = clientRepository.findById(id);
        if (existingClient.isPresent()) {
            Client client = existingClient.get();
            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setMiddleName(middleName);
            client.setPhone(phone);
            clientRepository.save(client);
            return true;
        }
        return false;
    }

    public boolean deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
