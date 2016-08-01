package org.developerworld.commons.dbutils.sql.command;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 排序类
 * 
 * @author Roy
 *
 */
public class OrderByCommand {

	/**
	 * 排序类型
	 * 
	 * @author Roy Huang
	 *
	 */
	public static enum OrderType {

		ASC("asc"), DESC("desc");
		private String value;

		OrderType(String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}

		/**
		 * 根据值获取对象
		 * 
		 * @param value
		 * @return
		 */
		public static OrderType valueOfValue(String value) {
			for (OrderType orderType : OrderType.values())
				if (orderType.value.equals(value))
					return orderType;
			return null;
		}
	}

	/**
	 * 排序对象
	 * 
	 * @author Roy Huang
	 *
	 */
	public static class Order {

		public Order() {

		}

		public Order(String orderColumn) {
			this(orderColumn, (OrderType) null);
		}

		public Order(String orderColumn, String orderType) {
			this(orderColumn, OrderType.valueOfValue(orderType));
		}

		public Order(String orderColumn, OrderType orderType) {
			setOrderColumn(orderColumn);
			setOrderType(orderType);
		}

		private String orderColumn;
		private OrderType orderType;

		public String getOrderColumn() {
			return orderColumn;
		}

		public void setOrderColumn(String orderColumn) {
			this.orderColumn = orderColumn;
		}

		public OrderType getOrderType() {
			return orderType;
		}

		public void setOrderType(OrderType orderType) {
			this.orderType = orderType;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((orderColumn == null) ? 0 : orderColumn.hashCode());
			result = prime * result + ((orderType == null) ? 0 : orderType.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Order other = (Order) obj;
			if (orderColumn == null) {
				if (other.orderColumn != null)
					return false;
			} else if (!orderColumn.equals(other.orderColumn))
				return false;
			if (orderType != other.orderType)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Order [orderColumn=" + orderColumn + ", orderType=" + orderType + "]";
		}

	}

	public OrderByCommand() {

	}

	public OrderByCommand(Order order) {

	}

	public OrderByCommand(String orderColumn, String orderType) {
		if (orderColumn == null || orderColumn.length() <= 0)
			return;
		addOrder(new Order(orderColumn, orderType));
	}

	public OrderByCommand(String orderColumn, OrderType orderType) {
		if (orderColumn == null || orderColumn.length() <= 0)
			return;
		addOrder(new Order(orderColumn, orderType));
	}

	public OrderByCommand(String orderSqlStr) {
		if (orderSqlStr == null || orderSqlStr.length() <= 0)
			return;
		String[] orderSqls = orderSqlStr.split(",");
		if (orderSqls != null)
			initOrder(orderSqls);
	}

	public OrderByCommand(LinkedHashMap<String, String> orderByMap) {
		if (orderByMap == null || orderByMap.size() <= 0)
			return;
		Set<String> keySet = orderByMap.keySet();
		for (String key : keySet)
			addOrder(key, orderByMap.get(key));
	}

	public OrderByCommand(Set<String> orderSqls) {
		if (orderSqls == null || orderSqls.size() <= 0)
			return;
		initOrder(orderSqls.toArray(new String[orderSqls.size()]));
	}

	public OrderByCommand(List<String> orderSqls) {
		if (orderSqls == null || orderSqls.size() <= 0)
			return;
		initOrder(orderSqls.toArray(new String[orderSqls.size()]));
	}

	public OrderByCommand(String[] orderSqls) {
		if (orderSqls == null || orderSqls.length <= 0)
			return;
		initOrder(orderSqls);
	}

	private void initOrder(String[] orderSqls) {
		if (orderSqls == null)
			return;
		for (String orderSql : orderSqls) {
			// 去除两侧空格
			orderSql = orderSql.trim();
			// 去除多余空格
			StringBuilder orderColumn = new StringBuilder();
			// 获取字段
			int index = 0;
			for (; index < orderSql.length(); index++) {
				char _char = orderSql.charAt(index);
				if (_char == ' ')
					break;
				orderColumn.append(_char);
			}
			// 去除空格
			for (; index < orderSql.length(); index++) {
				char _char = orderSql.charAt(index);
				if (_char != ' ')
					break;
			}
			StringBuilder orderType = new StringBuilder();
			// 获取排序方式
			for (; index < orderSql.length(); index++) {
				char _char = orderSql.charAt(index);
				if (_char == ' ')
					break;
				orderType.append(_char);
			}
			if (orderType.length() > 0)
				getOrders().add(new Order(orderColumn.toString(), OrderType.valueOfValue(orderType.toString())));
			else
				getOrders().add(new Order(orderColumn.toString()));
		}
	}

	private Set<Order> orders = new LinkedHashSet<Order>();

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public OrderByCommand addOrder(Order order) {
		orders.add(order);
		return this;
	}

	public OrderByCommand addOrder(String orderColumn, OrderType orderType) {
		return addOrder(new Order(orderColumn, orderType));
	}

	public OrderByCommand addOrder(String orderColumn, String orderType) {
		return addOrder(new Order(orderColumn, orderType));
	}

	public OrderByCommand addOrder(String orderColumn) {
		return addOrder(new Order(orderColumn));
	}

	/**
	 * 获取order语句
	 * 
	 * @return
	 */
	public String buildSqlWithOutOrderBy() {
		StringBuilder rst = new StringBuilder();
		for (Order order : getOrders()) {
			rst.append(order.getOrderColumn())
					.append(order.getOrderType() != null ? (" " + order.getOrderType().toString()) : "").append(",");
		}
		if (rst.length() > 0)
			rst.delete(rst.length() - 1, rst.length());
		return rst.toString();
	}

	/**
	 * 获取order语句
	 * 
	 * @return
	 */
	public String buildSql() {
		String rst = buildSqlWithOutOrderBy();
		if (rst.length() > 0)
			rst = "order by " + rst;
		return rst;
	}

	/**
	 * 是否有排序信息
	 * 
	 * @return
	 */
	public boolean hasOrder() {
		return getOrders().size() > 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orders == null) ? 0 : orders.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderByCommand other = (OrderByCommand) obj;
		if (orders == null) {
			if (other.orders != null)
				return false;
		} else if (!orders.equals(other.orders))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderByCommand [orders=" + orders + "]";
	}

}
