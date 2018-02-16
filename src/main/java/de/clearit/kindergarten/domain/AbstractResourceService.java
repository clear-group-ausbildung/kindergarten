package de.clearit.kindergarten.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  private static final String CSN = AbstractResourceService.class.getSimpleName();
  private static final String METHOD_PREFIX = "{}::";
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResourceService.class);
  private static final String LOG_MSG_FLUSH_PREFIX = "Successfully flushed! #Beans now: {}";

  private static boolean connectionEstablished = false;

  private final ObservableList<B> beans;

  AbstractResourceService() {
    super();
    LOGGER.debug(METHOD_PREFIX + "constructor()", CSN);
    if (!connectionEstablished) {
      LOGGER.debug("Establishing connection to database...");
      Base.open("org.sqlite.JDBC", "jdbc:sqlite:./kindergarten.sqlite", "", "");
      LOGGER.debug("Connection to database {} successfully established!", Base.connection().toString());
      connectionEstablished = true;
    }
    beans = new ArrayListModel<>(fromEntities());
    LOGGER.debug(METHOD_PREFIX + "constructor()", CSN);
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
    LOGGER.debug(METHOD_PREFIX + "getAll()", CSN);
    flush();
    LOGGER.debug(LOG_MSG_FLUSH_PREFIX, beans.size());
    LOGGER.debug(METHOD_PREFIX + "getAll()", CSN);
    return beans;
  }

  @Override
  public void preCreate(B bean, E entity) {
    LOGGER.debug(METHOD_PREFIX + "preCreate(B bean, E entity) -> ({}, {})", CSN, bean, entity);
  }

  @Override
  public void create(B bean) {
    LOGGER.debug(METHOD_PREFIX + "create(B bean) -> ({})", CSN, bean);
    E entity = toEntity(bean);
    preCreate(bean, entity);
    entity.saveIt();
    postCreate(bean, entity);
    flush();
    LOGGER.debug(LOG_MSG_FLUSH_PREFIX, beans.size());
    LOGGER.debug(METHOD_PREFIX + "create(B bean)", CSN);
  }

  @Override
  public void postCreate(B bean, E entity) {
    LOGGER.debug(METHOD_PREFIX + "postCreate(B bean, E entity) -> ({}, {})", CSN, bean, entity);
  }

  @Override
  public B getById(Integer id) {
    LOGGER.debug(METHOD_PREFIX + "getById(Integer id) -> ({})", CSN, id);
    E entity = E.findById(id);
    LOGGER.debug(METHOD_PREFIX + "getById(Integer id) -> ({})", CSN, entity);
    return fromEntity(entity);
  }

  @Override
  public void preUpdate(B bean, E entity) {
    LOGGER.debug(METHOD_PREFIX + "preUpdate(B bean, E entity) -> ({}, {})", CSN, bean, entity);
  }

  @Override
  public void update(B bean) {
    LOGGER.debug(METHOD_PREFIX + "update(B bean) -> ({})", CSN, bean);
    E entity = toEntity(bean);
    preUpdate(bean, entity);
    entity.saveIt();
    postUpdate(bean, entity);
    flush();
    LOGGER.debug(LOG_MSG_FLUSH_PREFIX, beans.size());
    LOGGER.debug(METHOD_PREFIX + "update(B bean)", CSN);
  }

  @Override
  public void postUpdate(B bean, E entity) {
    LOGGER.debug(METHOD_PREFIX + "postUpdate(B bean, E entity) -> ({}, {})", CSN, bean, entity);
  }

  @Override
  public void preDelete(B bean, E entity) {
    LOGGER.debug(METHOD_PREFIX + "preDelete(B bean, E entity) -> ({}, {})", CSN, bean, entity);
  }

  @Override
  public void delete(B bean) {
    LOGGER.debug(METHOD_PREFIX + "delete(B bean)", CSN, bean);
    E entity = toEntity(bean);
    preDelete(bean, entity);
    entity.delete();
    postDelete(bean, entity);
    flush();
    LOGGER.debug(LOG_MSG_FLUSH_PREFIX, beans.size());
    LOGGER.debug(METHOD_PREFIX + "delete(B bean)", CSN);
  }

  @Override
  public void postDelete(B bean, E entity) {
    LOGGER.debug(METHOD_PREFIX + "postDelete(B bean, E entity) -> ({}, {})", CSN, bean, entity);
  }

  /**
   * Flushes the instance cached beans and recreates all Beans from the current
   * persisted entities.
   */
  protected void flush() {
    LOGGER.debug(AbstractResourceService.class.getName(), "flush");
    beans.clear();
    beans.addAll(fromEntities());
    LOGGER.debug(LOG_MSG_FLUSH_PREFIX, beans.size());
    LOGGER.debug(AbstractResourceService.class.getName(), "flush");
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
