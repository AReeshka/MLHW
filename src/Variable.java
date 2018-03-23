public class Variable extends Unary{

	Variable(Expression l) {
		super(l);
		this.type = "VAR";
	}
	Variable(String var) {
		super();
		this.s = var;
		this.type = "VAR";
	}
	

}
