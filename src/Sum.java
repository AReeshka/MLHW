public class Sum extends Binary{

	Sum(Expression left, Expression right) {
		super(left, right, '+');
		this.type = "SUM";
	}

}
