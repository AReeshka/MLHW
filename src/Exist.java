public class Exist extends Unary{

	Exist(Expression var, Expression l) {
		super(var, l);
		this.type = "EXI";
		this.s = "(?" + var.s + l.s + ")"; 
	}
	

}
