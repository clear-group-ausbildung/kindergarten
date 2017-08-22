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
    Vendor[] allVendors = new Vendor[] { new Vendor("MUELLER", "Hans Müller"), new Vendor("MEYER", "Rita Meyer"),
        new Vendor("SCHULZ", "Gernot Schulz") };
    Arrays.asList(allVendors).stream().forEach(vendor -> VendorBroker.INSTANCE.add(vendor));
  }

}