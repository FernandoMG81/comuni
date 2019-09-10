package com.example.comuni.Servicios;

import com.example.comuni.Models.Post;

public class PostService {

    //Posteos Matematica
    public Post posteo1 = new Post(1,1,1,"Recuerden que el 20/09 tenemos parcial.","No van a poder usar calculadora, deberán traer solo lápiz, goma y papel.",1,true);
    public Post posteo2 = new Post(2,1,3,"¿Alguien resolvió el ejercicio 2.A?","Me da raiz cuadrada de -2...",4,false);
    public Post posteo3 = new Post(3,1,2,"¿Se puede sumar dos matrices de distinto orden?","Hice la suma, pero no se si esta bien",1,false );
    public Post posteo4 = new Post(4,1,3,"Alguien resolvió elejercicio 4 de la guia?","Simplemente no me sale.",1,false);

    //Posteos Programación
    public Post posteo5 = new Post(5,2,1,"Hoy no voy a poder asistir a clases.","Les pido perdón por avisar a tan corto plazo, pero ocurrió un imprevisto.",0,true);
    public Post posteo6 = new Post (6,2,4,"Problemas con el fopen","¿Qué parametros toma?",3, false);
    public Post posteo7 = new Post (7,2,3,"¿Cómo era el tema del encapsulamiento?","No entiendo como accedo a un atributo private si, justamente, lo hace inaccesible...",4,false);

    //Posteos Base de datos
    public Post posteo8 = new Post(8, 3,2,"Problemas instalando SQLServer.","¿Alguno me podrá pasar un tutorial?",3,false);
}
