public class Implication extends Binary{

	Implication(Expression left, Expression right) {
		super(left, right, "->");
		this.type = "IMP";
	}

}
