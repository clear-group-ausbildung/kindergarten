package de.clearit.kindergarten;

public class Verkaeufer {
  String vorname;
  String name;
  Integer telefon;
  String zusatz;
  
  Verkaeufer(String vorname, String name, Integer telefon, String zusatz){
    this.vorname = vorname;
    this.name = name;
    this.telefon = telefon;
    this.zusatz = zusatz;
    
    System.out.println("Verkäufer angelegt mit Namen: " + this.name + " " + this.vorname + " Telefon: " + this.telefon + " Zusatz: " + this.zusatz);
  }
  
  
  public String getVorname() {
    return vorname;
  }
  
  public void setVorname(String vorname) {
    vorname = vorname;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    name = name;
  }
  
  public Integer getTelefon() {
    return telefon;
  }
  
  public void setTelefon(Integer telefon) {
    telefon = telefon;
  }
  
  public String getZusatz() {
    return zusatz;
  }
  
  public void setZusatz(String zusatz) {
    zusatz = zusatz;
  }
}
