package de.clearit.kindergarten.domain.entity;

import org.javalite.activejdbc.Model;

public class Purchase extends Model {

  static {
    validatePresenceOf("vendor_id", "item_quantity", "item_number", "item_price", "profit", "payment");
  }

}
