public class Disjunction extends Binary{

	Disjunction(Expression left, Expression right) {
		super(left, right, '|');
		this.type = "DIS";
	}

}
