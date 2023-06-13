package com.ftn.sbnz.repository;

import com.ftn.sbnz.model.models.HydroelectricPowerPlant;
import com.ftn.sbnz.model.models.Lake;
import com.ftn.sbnz.model.models.Manager;
import com.ftn.sbnz.model.models.Turbine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Component
public class Database {

    private List<Manager> managers;
    private List<Turbine> turbines;
    private Lake lake;
    private HydroelectricPowerPlant hydroelectricPowerPlant;

    public Database() {
        this.managers = List.of(new Manager("admin", "admin", "admin", "admin"));
        this.turbines = turbines();
        this.lake = lake();
        this.hydroelectricPowerPlant = hydroelectricPowerPlant(this.lake, this.turbines);
    }

    private List<Turbine> turbines() {
        Turbine t1 = new Turbine(1, true);
        Turbine t2 = new Turbine(2, false);
        Turbine t3 = new Turbine(3, false);
        return Arrays.asList(t1, t2, t3);
    }

    private Lake lake() {
        return new Lake(1);
    }

    private HydroelectricPowerPlant hydroelectricPowerPlant(Lake lake, List<Turbine> turbines) {
        return new HydroelectricPowerPlant(1, lake, turbines);
    }

    public Manager findByUsernameAndPassword(String username, String password) {
        return managers.stream()
                .filter(manager -> manager.getUsername().equals(username) && manager.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
