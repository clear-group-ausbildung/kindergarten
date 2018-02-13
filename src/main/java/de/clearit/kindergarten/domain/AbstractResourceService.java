package de.clearit.kindergarten.domain;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.javalite.activejdbc.Base;

import com.google.common.base.CaseFormat;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.ObservableList;

/**
 * This class provides common implementation for all resources.
 *
 * @param <B>
 *          The type of the bean class
 * @param <E>
 *          The type of the entity class
 */
public abstract class AbstractResourceService<B extends com.jgoodies.binding.beans.Model, E extends org.javalite.activejdbc.Model>
    implements ResourceService<B, E> {

  private static final Logger LOGGER = Logger.getLogger(AbstractResourceService.class.getName());
  private static final String LOG_MSG_FLUSH_PREFIX = "Successfully flushed! #Beans now: ";

  private static boolean connectionEstablished = false;

  private final ObservableList<B> beans;

  AbstractResourceService() {
    super();
    LOGGER.entering(AbstractResourceService.class.getSimpleName(), "constructor()");
    if (!connectionEstablished) {
      LOGGER.fine("Establishing connection to database...");
      Base.open("org.sqlite.JDBC", "jdbc:sqlite:./kindergarten.sqlite", "", "");
      LOGGER.log(Level.FINE, () -> "Connection to database " + Base.connection().toString()
          + " successfully established!");
      connectionEstablished = true;
    }
    beans = new ArrayListModel<>(fromEntities());
    LOGGER.exiting(AbstractResourceService.class.getSimpleName(), "constructor()");
  }

  /**
   * Converts the given camelCase String to a snake_case String. The database
   * column names use snake_case names for columns, e.g. {@code first_name}. The
   * bean properties however use camelCase names for properties, e.g.
   * {@code firstName}. So with this method, the property constant names can be
   * used to map to the database columns for property names with multiple words.
   * <p>
   * <b>Example:</b>
   * </p>
   * <p>
   * {@code toSnakeCase("firstName");  // becomes "first_name"}
   * </p>
   *
   * @param camelCase
   *          the camelCase formatted String to be converted
   * @return the snake_case converted String
   */
  static String toSnakeCase(String camelCase) {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCase);
  }

  @Override
  public List<B> getAll() {
    LOGGER.entering(AbstractResourceService.class.getSimpleName(), "getAll()");
    flush();
    LOGGER.log(Level.FINE, () -> LOG_MSG_FLUSH_PREFIX + beans.size());
    LOGGER.exiting(AbstractResourceService.class.getSimpleName(), "getAll()");
    return beans;
  }

  @Override
  public void preCreate(B bean, E entity) {
    LOGGER.entering(AbstractResourceService.class.getName(), "preCreate(B bean, E entity)", new Object[] { bean,
        entity });
  }

  @Override
  public void create(B bean) {
    LOGGER.entering(AbstractResourceService.class.getSimpleName(), "create(B bean)", new Object[] { bean });
    E entity = toEntity(bean);
    preCreate(bean, entity);
    entity.saveIt();
    postCreate(bean, entity);
    flush();
    LOGGER.log(Level.FINE, () -> LOG_MSG_FLUSH_PREFIX + beans.size());
    LOGGER.exiting(AbstractResourceService.class.getSimpleName(), "create(B bean)");
  }

  @Override
  public void postCreate(B bean, E entity) {
    LOGGER.entering(AbstractResourceService.class.getName(), "postCreate(B bean, E entity)", new Object[] { bean,
        entity });
  }

  @Override
  public B getById(Integer id) {
    LOGGER.entering(AbstractResourceService.class.getSimpleName(), "getById(Integer id)", new Object[] { id });
    E entity = E.findById(id);
    LOGGER.exiting(AbstractResourceService.class.getSimpleName(), "getById(Integer id)", new Object[] { entity });
    return fromEntity(entity);
  }

  @Override
  public void preUpdate(B bean, E entity) {
    LOGGER.entering(AbstractResourceService.class.getName(), "preUpdate(B bean, E entity)", new Object[] { bean,
        entity });
  }

  @Override
  public void update(B bean) {
    LOGGER.entering(AbstractResourceService.class.getSimpleName(), "update(B bean)", new Object[] { bean });
    E entity = toEntity(bean);
    preUpdate(bean, entity);
    entity.saveIt();
    postUpdate(bean, entity);
    flush();
    LOGGER.log(Level.FINE, () -> LOG_MSG_FLUSH_PREFIX + beans.size());
    LOGGER.exiting(AbstractResourceService.class.getSimpleName(), "update(B bean)");
  }

  @Override
  public void postUpdate(B bean, E entity) {
    LOGGER.entering(AbstractResourceService.class.getName(), "postUpdate(B bean, E entity)", new Object[] { bean,
        entity });
  }

  @Override
  public void preDelete(B bean, E entity) {
    LOGGER.entering(AbstractResourceService.class.getName(), "preDelete(B bean, E entity)", new Object[] { bean,
        entity });
  }

  @Override
  public void delete(B bean) {
    LOGGER.entering(AbstractResourceService.class.getSimpleName(), "delete(B bean)", new Object[] { bean });
    E entity = toEntity(bean);
    preDelete(bean, entity);
    entity.delete();
    postDelete(bean, entity);
    flush();
    LOGGER.log(Level.FINE, () -> LOG_MSG_FLUSH_PREFIX + beans.size());
    LOGGER.exiting(AbstractResourceService.class.getSimpleName(), "delete(B bean)");
  }

  @Override
  public void postDelete(B bean, E entity) {
    LOGGER.entering(AbstractResourceService.class.getName(), "postDelete(B bean, E entity)", new Object[] { bean,
        entity });
  }

  /**
   * Flushes the instance cached beans and recreates all Beans from the current
   * persisted entities.
   */
  protected void flush() {
    LOGGER.entering(AbstractResourceService.class.getName(), "flush");
    beans.clear();
    beans.addAll(fromEntities());
    LOGGER.log(Level.FINE, () -> LOG_MSG_FLUSH_PREFIX + beans.size());
    LOGGER.exiting(AbstractResourceService.class.getName(), "flush ", new Object[] { beans.size() });
  }

  /**
   * Returns all resources as a list of entities.
   *
   * @return the list of entities
   */
  protected abstract List<E> getEntities();

  private List<B> fromEntities() {
    return getEntities().stream().map(this::fromEntity).collect(Collectors.toList());
  }

}
