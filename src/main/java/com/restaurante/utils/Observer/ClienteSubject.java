package com.restaurante.utils.Observer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ClienteSubject {

    @Getter
    private String state;
    private List<Observer> observers = new ArrayList<>();

    public ClienteSubject() {
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update("Tipo de cliente cambiado");
        }
    }

    public void setState(String state) {
        this.state = state;
        notifyObservers();
    }

}
