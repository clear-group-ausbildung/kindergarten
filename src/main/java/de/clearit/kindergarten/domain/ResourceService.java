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
interface ResourceService<B extends com.jgoodies.binding.beans.Model, E extends org.javalite.activejdbc.Model> {

  /**
   * Returns a list of all Resources with their elements as their respective Beans
   * 
   * @return the list of all beans
   */
  List<B> getAll();

  /**
   * Persists the given {@code bean} to the database.
   * 
   * @param bean
   *          the bean to persist
   */
  void create(B bean);

  /**
   * Looks up the bean by its given {@code id} and returns it
   * 
   * @param id
   *          the id of the bean
   * @return the found bean, if any
   */
  B getById(Integer id);

  /**
   * Persists the changes for the given {@code bean} to the database.
   * 
   * @param bean
   *          the bean to persist
   */
  void update(B bean);

  /**
   * Deletes the given {@code bean} from the database.
   * 
   * @param bean
   *          the bean to delete
   */
  void delete(B bean);

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
