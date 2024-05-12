package com.ftn.sbnz.model.events;

import java.io.Serializable;

import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class CarFailureEvent implements Serializable{
    private static final long serialVersionUID = 1L;
    private int carId;

    public CarFailureEvent() {
    }


    public CarFailureEvent(int carId) {
        this.carId = carId;
    }

    public int getCarId() {
        return this.carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

}
