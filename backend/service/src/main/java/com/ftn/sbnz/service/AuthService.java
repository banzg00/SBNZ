package com.ftn.sbnz.service;

import com.ftn.sbnz.model.Manager;
import com.ftn.sbnz.dto.LoginDTO;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
public class AuthService {

//    @Autowired
//    private ManagerRepository managerRepo;

    private Manager getManager(String username, String password) {
//        Optional<Manager> managerOptional = managerRepo.findByUsernameAndPassword(username, password);
        return null;
    }

    public String login(LoginDTO dto) {
        Manager manager = this.getManager(dto.getUsername(), dto.getPassword());
        return manager == null ? null : String.format("x%s&%sx", dto.getUsername(), dto.getPassword());
    }

}
