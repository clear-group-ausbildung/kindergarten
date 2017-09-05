package de.clearit.kindergarten.domain;

import java.util.List;

import javax.swing.ListModel;

import org.javalite.activejdbc.Base;

import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.ObservableList;

import de.clearit.kindergarten.domain.entity.Vendor;

/**
 * The broker for the Vendor entity.
 */
public final class VendorBroker {

  public static final VendorBroker INSTANCE = new VendorBroker();

  private final ObservableList<VendorBean> vendors;

  // Instance Creation ******************************************************

  private VendorBroker() {
    Base.open("org.sqlite.JDBC", "jdbc:sqlite:./kindergarten.sqlite", "", "");
    vendors = new ArrayListModel<VendorBean>();
    Vendor.<Vendor>findAll().stream().forEach(entity -> vendors.add(VendorBean.fromEntity(entity)));
  }

  // Public API *************************************************************

  public List<VendorBean> getList() {
    return vendors;
  }

  public ListModel<?> getListModel() {
    return vendors;
  }

  public void add(VendorBean vendor) {
    vendors.add(vendor);
    vendor.toEntity().saveIt();
  }

  public void remove(VendorBean customer) {
    vendors.remove(customer);
    customer.toEntity().delete();
  }

  public VendorBean findByName(String firstName, String lastName) {
    for (VendorBean vendor : vendors) {
      if (vendor.getFirstName().equals(firstName) && vendor.getLastName().equals(lastName)) {
        return vendor;
      }
    }
    return null;
  }

}
