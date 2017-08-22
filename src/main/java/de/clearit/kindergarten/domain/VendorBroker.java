package de.clearit.kindergarten.domain;

import java.util.List;

import javax.swing.ListModel;

import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.ObservableList;

/**
 * The broker for the Vendor entity.
 */
public final class VendorBroker {

  public static final VendorBroker INSTANCE = new VendorBroker();

  private final ObservableList<Vendor> customers;

  // Instance Creation ******************************************************

  private VendorBroker() {
    customers = new ArrayListModel<Vendor>();
  }

  // Public API *************************************************************

  public List<Vendor> getList() {
    return customers;
  }

  public ListModel<?> getListModel() {
    return customers;
  }

  public Vendor findByCode(String code) {
    for (Vendor customer : customers) {
      if (customer.getCode().equals(code)) {
        return customer;
      }
    }
    return null;
  }

  public Vendor findByName(String name) {
    for (Vendor customer : customers) {
      if (customer.getName().equals(name)) {
        return customer;
      }
    }
    return null;
  }

  public void add(Vendor customer) {
    customers.add(customer);
  }

  public void remove(Vendor customer) {
    customers.remove(customer);
  }

}
