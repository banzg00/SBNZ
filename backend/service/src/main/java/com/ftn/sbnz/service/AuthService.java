package com.ftn.sbnz.service;

import com.ftn.sbnz.model.models.Manager;
import com.ftn.sbnz.dto.LoginDTO;
import com.ftn.sbnz.repository.ManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private ManagerRepository managerRepo;


    public String login(LoginDTO dto) {
        Manager manager = managerRepo.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        return manager == null ? null : String.format("x%s&%sx", dto.getUsername(), dto.getPassword());
    }

}
