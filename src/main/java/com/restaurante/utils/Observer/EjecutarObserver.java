package com.restaurante.utils.Observer;

import lombok.Getter;

@Getter
public class EjecutarObserver {
    public static ClienteSubject clienteSubject = new ClienteSubject();

    public static void ejecutar(){

        Observer observer1 = new NotificacionPlatoObserver();
        clienteSubject.addObserver(observer1);


    }
}
