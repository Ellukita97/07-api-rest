package com.restaurante.patronesDeDisenio.Observer;

public class NotificacionPlatoObserver implements Observer {

    @Override
    public void update(String string) {
        System.out.println(string);
    }
}
