import java.io.*;
import java.util.*;

public class Solver {
	HashMap<String, String> map;
	
	Solver() {
		map = new HashMap<>();
	}
	
	void annotate(Scanner sc, PrintWriter out, List<Expression> axes, List<Expression> assu) {
		ArrayList<Expression> li = new ArrayList<>();
		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			li.add(new Parser(s).parse());
		}
		work(out, axes, assu, li);
	}
	void work(PrintWriter out, List<Expression> axes, List<Expression> assu, List<Expression> li) {
		List<Expression> done = new ArrayList<>();
		
		for (int i = 0; i < li.size(); i++) {
			Expression t = li.get(i);
			out.print("(" + (i + 1) + ") " + t.init + " (");
			int[] get = check(axes, assu, done, t);
			
	        if (get[1] == -1) {
	            if (get[0] >= 0) {
	            	out.print("Сх. акс. " + (get[0] + 1));
	            	done.add(t);
	            } else {
	            	out.print("Не доказано");
	            }
	        } else {
	        	if (get[0] >= 0) {
	            	out.print("M.P. " + (get[0] + 1) + ", " + (get[1] + 1));
	        	} else {
	            	out.print("Предп. " + (get[1] + 1));
	        		
	        	}
	        	done.add(t);
	        }
	        out.println(")");
	    }		
	}
	
	boolean match(Expression t, Expression p, boolean var) {
		map.clear();
		return match2(t, p, var);
	}
	
	boolean match2(Expression t, Expression p, boolean var) {
		if (p.type.equals("VAR") && (var || t.type.equals("VAR"))) {
			if (map.containsKey(p.s)) {
				return map.get(p.s).equals(t.s);
			}
			map.put(p.s, t.s);
			if (var) return true;
			return p.s.equals(t.s);
		}
		if (!p.type.equals(t.type)) return false;
		if (p.type.equals("NEG") || p.type.equals("INC")) {
			return match2(t.t1, p.t1, var);
		}
		if (p.type.equals("ANY") || p.type.equals("EXI")) {
			return match2(t.v, p.v, var) && match2(t.t1, p.t1, var);
		}
		if (p.type.equals("PRE") ) {
			if (p.li.size() != t.li.size()) return false;
			for (int i = 0; i < p.li.size(); i++)
				if (!match2(t.li.get(i), p.li.get(i), var)) return false;
			if (map.containsKey(p.s)) {
				return map.get(p.s).equals(t.s);
			}
			map.put(p.s, t.s);
			if (var) return true;
			return p.s.equals(t.s);
		}
		return match2(t.t1, p.t1, var) && match2(t.t2, p.t2, var);
		
	}
	
	
	int[] check(List<Expression> axes, List<Expression> assu, List<Expression> done, Expression t) {
		for (int i = 0; i < axes.size(); i++) {
			if (match(t, axes.get(i), true)) return new int[]{i, -1};
		}
		
		for (int i = 0; i < assu.size(); i++) if (match(t, assu.get(i), false)) return new int[]{-1, i};
		
		for (int i = done.size() - 1; i >= 0; i--) if (done.get(i).type.equals("IMP"))
			for (int j = done.size() - 1; j >= 0; j--) if (i != j) {
				if (match(done.get(j), done.get(i).t1, false) && match(t, done.get(i).t2, false)) return new int[] {j, i};
			}
		return new int[] {-1, -1};
		
	}
}
