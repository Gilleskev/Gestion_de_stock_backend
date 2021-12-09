package com.gilles.gestionDeStock.servicesImpl;

import com.gilles.gestionDeStock.dto.ClientDto;
import com.gilles.gestionDeStock.exception.EntityNotFoundException;
import com.gilles.gestionDeStock.exception.ErrorCodes;
import com.gilles.gestionDeStock.exception.InvalidEntityException;
import com.gilles.gestionDeStock.model.Client;
import com.gilles.gestionDeStock.repository.ClientRepository;
import com.gilles.gestionDeStock.services.ClientService;
import com.gilles.gestionDeStock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto save(ClientDto dto) {
        List<String> erros = ClientValidator.validate(dto);
        if (!erros.isEmpty()){
            log.error("Le client n'est pas valide", dto);
            throw new InvalidEntityException("Le client n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, erros);

        }
        return ClientDto.fromEntity(
                clientRepository.save(
                        ClientDto.toEntity(dto))
        );
    }

    @Override
    public ClientDto findById(Integer id) {
        if (id == null){
            log.error("L'ID du client est null");
            return null;
        }
        Optional<Client> client = clientRepository.findById(id);

        return  clientRepository.findById(id)
                .map(ClientDto::fromEntity)
                .orElseThrow(()->
                new EntityNotFoundException("Aucun client avec l'ID = "+ id +
                        " n'a été trouvé dans la DB ", ErrorCodes.ARTICLE_NOT_FOUND));
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDto:: fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("L'ID du client est null");
            return;
        }
        clientRepository.deleteById(id);

    }
}
