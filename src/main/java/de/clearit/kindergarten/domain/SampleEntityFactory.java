package de.clearit.kindergarten.domain;

import java.util.Arrays;

/**
 * An entity factory for sample data.
 */
public final class SampleEntityFactory {

  public static final SampleEntityFactory INSTANCE = new SampleEntityFactory();

  // Instance Creation ******************************************************

  private SampleEntityFactory() {
    // Override default constructor; prevents instantiation.
  }

  public void addSampleData() {
    addVendors();
  }

  private void addVendors() {
    VendorBean[] allVendors = new VendorBean[] { new VendorBean("Hans", "Mueller"), new VendorBean("Rita", "Meyer"),
        new VendorBean("Gernot", "Schulz") };
    Arrays.asList(allVendors).stream().forEach(vendor -> VendorBroker.INSTANCE.add(vendor));
  }

}