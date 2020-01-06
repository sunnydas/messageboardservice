package com.sunny.service.messageboard.repository;

import com.sunny.service.messageboard.repository.domain.MessageDTO;
import org.springframework.data.repository.CrudRepository;

public interface MessageBoardRepo extends CrudRepository<MessageDTO,Long> {
}
