package de.clearit.kindergarten.domain.entity;

import org.javalite.activejdbc.Model;

public class Vendor extends Model {

  static {
    validatePresenceOf("first_name", "last_name");
  }

}
