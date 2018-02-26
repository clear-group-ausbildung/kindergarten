package de.clearit.kindergarten.appliance;

public interface PostChangeHandler {

  void onPostCreate();

  void onPostUpdate();

  void onPostDelete();

}
