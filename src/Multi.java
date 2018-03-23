public class Multi extends Binary{

	Multi(Expression left, Expression right) {
		super(left, right, '*');
		this.type = "MUL";
	}

}
