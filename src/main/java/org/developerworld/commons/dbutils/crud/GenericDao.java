package org.developerworld.commons.dbutils.crud;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * DAO API(数据访问对象接口) 该接口为所有使用不同的数据访问对象提供方法约定， 不同的DAO对象必须实现该接口，
 * 使所有项目能够穿插于不同框架当中运行！ 注：该类使用了泛型，因此必须在JDK5或以上运行环境使用
 * 
 * @author Roy.Huang
 * @version 20120628
 * @param <T>
 * @param <PK>
 */
public interface GenericDao<T, PK extends Serializable> {

	/**
	 * 获取DAO所属实体类
	 * 
	 * @return
	 */
	public Class<T> getEntityClass();
	
	/**
	 * 获取DAO所属实体类名
	 * @return
	 */
	public String getEntityClassName();

	/**
	 * 保存对象并返回主键
	 * 
	 * @param entity
	 * @return
	 */
	public void save(T entity);

	/**
	 * 更新对象
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 删除指定对象
	 * 
	 * @param entity
	 */
	public void delete(T entity);

	/**
	 * 删除指定对象
	 * 
	 * @param id
	 */
	public void delete(PK id);

	/**
	 * 删除指定集合对象
	 * 
	 * @param entitys
	 */
	public void delete(Collection<T> entitys);

	/**
	 * 删除指定ID集合的对象
	 * 
	 * @param ids
	 */
	public void delete(PK ids[]);

	/**
	 * 获取总数目
	 * 
	 * @return
	 */
	public long findCount();
	
	/**
	 * 根据主键查找对象
	 * @param id
	 * @return
	 */
	public T findByPk(PK id);

	/**
	 * 获取对象集合
	 * 
	 * @return
	 */
	public List<T> findList();

	/**
	 * 获取对象集合
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<T> findList(int pageNum, int pageSize);
	
	/**
	 * 根据排序条件获取数据
	 * @param order
	 * @return
	 */
	public List<T> findList(String order);
	
	/**
	 * 获取对象集合
	 * @param order
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<T> findList(String order,int pageNum,int pageSize);

}
