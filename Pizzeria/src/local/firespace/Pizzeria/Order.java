package local.firespace.Pizzeria;

public class Order {
	public static final String KEY_PIZZERIA_NAME = "pizzeria_name";
	public static final String KEY_COURIERS_NUMBER = "cour_number";

	private String pizzaName;
	private Integer courierNumb;
	private String orderTime;
	private String telephoneNumber;
	private String orderSpeed;
	private Integer ID;

	public Order(String pizzaName, Integer courierNumb, String orderTime, String telephoneNumber, String orderSpeed) {
		this.pizzaName = pizzaName;
		this.courierNumb = courierNumb;
		this.orderTime = orderTime;
		this.telephoneNumber = telephoneNumber;
		this.orderSpeed = orderSpeed;
	}

	public Order(String pizzaName, Integer courierNumb, String orderTime, String telephoneNumber, String orderSpeed, Integer ID) {
		this.pizzaName = pizzaName;
		this.courierNumb = courierNumb;
		this.orderTime = orderTime;
		this.telephoneNumber = telephoneNumber;
		this.orderSpeed = orderSpeed;
		this.ID = ID;
	}

	public String getPizzaName() {
		return pizzaName;
	}

	public Integer getCourierNumb() {
		return courierNumb;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public String getOrderSpeed() {
		return orderSpeed;
	}

	public Integer getID() {
		return ID;
	}
}
