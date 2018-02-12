package de.clearit.kindergarten.domain;

import java.util.List;
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

  private static boolean connectionEstablished = false;

  private final ObservableList<B> beans;

  AbstractResourceService() {
    super();
    if (!connectionEstablished) {
      Base.open("org.sqlite.JDBC", "jdbc:sqlite:./kindergarten.sqlite", "", "");
      connectionEstablished = true;
    }
    beans = new ArrayListModel<>(fromEntities());
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
    flush();
    return beans;
  }

  @Override
  public void create(B bean) {
    E entity = toEntity(bean);
    preCreate(bean, entity);
    entity.saveIt();
    postCreate(bean, entity);
    flush();
  }

  @Override
  public B getById(Integer id) {
    E entity = E.findById(id);
    return fromEntity(entity);
  }

  @Override
  public void update(B bean) {
    E entity = toEntity(bean);
    preUpdate(bean, entity);
    entity.saveIt();
    postUpdate(bean, entity);
    flush();
  }

  @Override
  public void delete(B bean) {
    E entity = toEntity(bean);
    preDelete(bean, entity);
    entity.delete();
    postDelete(bean, entity);
    flush();
  }

  /**
   * Returns all resources as a list of entities.
   * 
   * @return the list of entities
   */
  protected abstract List<E> getEntities();

  protected void preCreate(B bean, E entity) {
    LOGGER.entering(AbstractResourceService.class.getName(), "create", new Object[] { bean, entity });
  }

  protected void postCreate(B bean, E entity) {
    LOGGER.exiting(AbstractResourceService.class.getName(), "create", new Object[] { bean, entity });
  }

  protected void preUpdate(B bean, E entity) {
    LOGGER.entering(AbstractResourceService.class.getName(), "update", new Object[] { bean, entity });
  }

  protected void postUpdate(B bean, E entity) {
    LOGGER.exiting(AbstractResourceService.class.getName(), "update", new Object[] { bean, entity });
  }

  protected void preDelete(B bean, E entity) {
    LOGGER.entering(AbstractResourceService.class.getName(), "delete", new Object[] { bean, entity });
  }

  protected void postDelete(B bean, E entity) {
    LOGGER.exiting(AbstractResourceService.class.getName(), "delete", new Object[] { bean, entity });
  }

  protected void flush() {
    LOGGER.entering(AbstractResourceService.class.getName(), "flush");
    beans.clear();
    beans.addAll(fromEntities());
    LOGGER.exiting(AbstractResourceService.class.getName(), "flush ", new Object[] { beans.size() });
  }

  private List<B> fromEntities() {
    return getEntities().stream().map(this::fromEntity).collect(Collectors.toList());
  }

}
