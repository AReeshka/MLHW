public class Negate extends Unary{

	Negate(Expression l) {
		super(l);
		this.type = "NEG";
		this.s = "!" + l.s;
	}
	

}
