import java.util.List;

public class Pred extends Unary{
	Pred(String var, List<Expression> l) {
		super();
		this.s = var;
		this.li = l;
		this.type = "PRE";
		this.li = l;
		this.s = var;
		if (l.size() > 0) {
			this.s += "(";
			for (Expression e : l) {
				this.s += e.s + ",";
			}
			this.s += ")";
		}
	}
}
