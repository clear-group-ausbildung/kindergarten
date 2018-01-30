package de.clearit.kindergarten.domain.entity;

import java.util.ArrayList;

import org.javalite.activejdbc.Model;

/**
 * The entity class for the Vendor resource.
 */
public class Vendor extends Model {

//	ArrayList<Integer> vendorNumbers = new ArrayList<>();
  // Validation for mandatory properties

  static {
    validatePresenceOf("last_name");
  }

}
