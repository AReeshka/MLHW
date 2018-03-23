public class Unary extends Expression{
	Unary() {
		this.t1 = null;
	}
	Unary(Expression l) {
		this.t1 = l;
	}
	Unary(Expression var, Expression l) {
		this.t1 = l;
		this.v = var;
	}
}
