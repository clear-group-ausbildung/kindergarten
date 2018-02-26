package de.clearit.kindergarten.domain;

import java.util.List;

/**
 * This interface describes methods to interact with database resources.
 *
 * @param <B>
 *          The type of the bean class
 * @param <E>
 *          The type of the entity class
 */
public interface ResourceService<B extends com.jgoodies.binding.beans.Model, E extends org.javalite.activejdbc.Model> {

  /**
   * Returns a list of all Resources with their elements as their respective Beans
   * 
   * @return the list of all beans
   */
  List<B> getAll();

  /**
   * Lifecycle hook to handle logic before entity.saveIt() is called.
   * 
   * @param bean
   *          the bean to create the entity from
   * @param entity
   *          the entity created from the bean
   */
  void preCreate(B bean, E entity);

  /**
   * Persists the given {@code bean} to the database.
   * 
   * @param bean
   *          the bean to persist
   */
  void create(B bean);

  /**
   * Lifecycle hook to handle logic after entity.saveIt() is called.
   * 
   * @param bean
   *          the bean to create the entity from
   * @param entity
   *          the entity created from the bean
   */
  void postCreate(B bean, E entity);

  /**
   * Looks up the bean by its given {@code id} and returns it
   * 
   * @param id
   *          the id of the bean
   * @return the found bean, if any
   */
  B getById(Integer id);

  /**
   * Lifecycle hook to handle logic before entity.saveIt() is called.
   * 
   * @param bean
   *          the bean to create the entity from
   * @param entity
   *          the entity created from the bean
   */
  void preUpdate(B bean, E entity);

  /**
   * Persists the changes for the given {@code bean} to the database.
   * 
   * @param bean
   *          the bean to persist
   */
  void update(B bean);

  /**
   * Lifecycle hook to handle logic after entity.saveIt() is called.
   * 
   * @param bean
   *          the bean to create the entity from
   * @param entity
   *          the entity created from the bean
   */
  void postUpdate(B bean, E entity);

  /**
   * Lifecycle hook to handle logic before entity.delete() is called.
   * 
   * @param bean
   *          the bean to create the entity from
   * @param entity
   *          the entity created from the bean
   */
  void preDelete(B bean, E entity);

  /**
   * Deletes the given {@code bean} from the database.
   * 
   * @param bean
   *          the bean to delete
   */
  void delete(B bean);

  /**
   * Lifecycle hook to handle logic after entity.delete() is called.
   * 
   * @param bean
   *          the bean to create the entity from
   * @param entity
   *          the entity created from the bean
   */
  void postDelete(B bean, E entity);

  /**
   * Converts the given {@code entity} to a bean.
   * 
   * @param entity
   *          the entity to convert
   * @return the bean
   */
  B fromEntity(E entity);

  /**
   * Converts the given {@code bean} to an entity.
   * 
   * @param bean
   *          the bean to convert
   * @return the entity
   */
  E toEntity(B bean);

}
