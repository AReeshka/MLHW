public class Any extends Unary{

	Any(Expression var, Expression l) {
		super(var, l);
		this.type = "ANY";
	}
	Any(Expression l) {
		super(l);
		this.type = "ANY";
	}

}
