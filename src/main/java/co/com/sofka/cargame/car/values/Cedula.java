package co.com.sofka.cargame.car.values;

import co.com.sofka.domain.generic.Identity;


public class Cedula extends Identity {
  public Cedula(String value){
      super(value);
  }
  public static Cedula of(String value){
      return new Cedula(value);
  }
}
