package de.clearit.kindergarten.domain;

import java.util.List;

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

  private final ObservableList<B> beans;

  public AbstractResourceService() {
    super();
    Base.open("org.sqlite.JDBC", "jdbc:sqlite:./kindergarten.sqlite", "", "");
    beans = new ArrayListModel<>();
    getEntities().forEach(entity -> beans.add(fromEntity(entity)));
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
  public static String toSnakeCase(String camelCase) {
    return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCase);
  }

  @Override
  public List<B> getAll() {
    return beans;
  }

  @Override
  public void create(B bean) {
    beans.add(bean);
    E entity = toEntity(bean);
    entity.saveIt();
  }

  @Override
  public B getById(Integer id) {
    E entity = E.<E>findById(id);
    return fromEntity(entity);
  }

  @Override
  public void update(B bean) {
    E entity = toEntity(bean);
    entity.saveIt();
  }

  @Override
  public void delete(B bean) {
    beans.remove(bean);
    E entity = toEntity(bean);
    entity.delete();
  }

  protected abstract List<E> getEntities();

}
