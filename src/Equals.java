public class Equals extends Binary{

	Equals(Expression left, Expression right) {
		super(left, right, '=');
		this.type = "EQ";
	}

}
