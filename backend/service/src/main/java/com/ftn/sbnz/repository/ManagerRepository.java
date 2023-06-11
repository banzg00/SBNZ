package com.ftn.sbnz.repository;

import com.ftn.sbnz.model.models.Manager;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class ManagerRepository {

    private List<Manager> managers;

    @Autowired
    public ManagerRepository(List<Manager> managers) {
        this.managers = managers;
    }

    public Manager findByUsernameAndPassword(String username, String password) {
        return managers.stream()
                .filter(manager -> manager.getUsername().equals(username) && manager.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
