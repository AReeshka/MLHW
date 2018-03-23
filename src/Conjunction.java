public class Conjunction extends Binary{

	Conjunction(Expression left, Expression right) {
		super(left, right, '&');
		this.type = "CON";
	}

}
