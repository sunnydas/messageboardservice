package com.sunny.service.messageboard.repository;

import com.sunny.service.messageboard.repository.domain.ClientDTO;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepo extends CrudRepository<ClientDTO,Long> {

}
