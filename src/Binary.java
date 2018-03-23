public class Binary extends Expression{
	Binary(Expression left, Expression right, char oper) {
		this.t1 = left;
		this.t2 = right;
		this.s = left.s + oper + right.s;
	}
	Binary(Expression left, Expression right, String oper) {
		this.t1 = left;
		this.t2 = right;
		this.s = left.s + oper + right.s;
	}
}
