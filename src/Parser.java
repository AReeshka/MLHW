import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
	String s;
	int pos;
	boolean pred;
	boolean balance;
	Parser(String text) {
		this.s = text;
		pred = false;
		balance = true;
	}
	
	Expression parse() {
		Expression ret = get_imp();
		ret.init = s;
		return ret;
	}
	
	
	static List<Expression> get_ax(String fn, boolean lc) throws FileNotFoundException {
		ArrayList<Expression> ret = new ArrayList<>();
		Scanner sc = new Scanner(new File(fn));
		
		String s;
	    while (sc.hasNextLine()) {
	    	s = sc.nextLine();
	    	
	    	if (lc) s = s.toLowerCase();
	        ret.add(new Parser(s).parse());
	    }
	    sc.close();
	    return ret;
	}
	
	static List<Expression> get_assu(Scanner sc) {
		ArrayList<Expression> ret = new ArrayList<>();
		String s = sc.nextLine();
		
		int pos = s.indexOf("|-");
		int x = 0;
		while (true) {
			int cur = x;
			x = s.indexOf(",", cur);
			int en = x;
			if (x == -1) en = pos;
			ret.add(new Parser(s.substring(cur, en)).parse());
			if (x == -1) break;
			x++;
		}
		return ret;
	}	
	
	
	Expression get_imp() {
	   Expression ret = get_dis();
	    if (pos < s.length() && s.charAt(pos) == '-') {
	        pos += 2;
	        Expression t = get_imp();
	        return new Implication(ret, t);
	    }
	    return ret;
	}
	
	Expression get_dis() {
	   Expression ret = get_con();
	    while (pos < s.length() && s.charAt(pos) == '|') {
	        pos++;
	        Expression t = get_con();
	        ret = new Disjunction(ret, t);
	    }
	    return ret;		
	}
	
	Expression get_con() {
		Expression ret = get_un();
		
	    while (pos < s.length() && s.charAt(pos) == '&') {
	        pos++;
	        Expression t = get_un();
	        ret = new Conjunction(ret, t);
	    }
	    return ret;		
	}
	
	Expression get_un() {
	    char c = s.charAt(pos);;
	    if (c == '!') {
	        pos++;
	        return new Negate(get_un());
	    }
	    if (pred) {
	        if (c == '@' || c == '?') {
	            pos++;
	            Expression t = new Variable(get_str());
	            Expression t2 = get_un();
	            if (c == '@') {
	            	return new Any(t, t2);
	            } else {
	            	return new Exist(t, t2);
	            }
	        }
	        Expression t = get_pred();
	        if (t != null && balance) return t;
	        balance = true;
	    }
	    if (c == '(') {
	        pos++;
	        Expression t = get_imp();
	        pos++;
	        return t;
	    }
	    if (pred) {
	    	return new Variable(get_str());
	    } else {
	    	return new Variable(get_str2());
	    }
	}
	
	Expression get_pred() {
		String name = get_str2();
		if (name.length() > 0) return new Pred(name, get_list());
		
		int x = pos;
		
		Expression t = get_term();
		
		if (pos >= s.length() || s.charAt(pos) != '=') {
			pos = x;
			return null;
		}
		pos++;
		return new Equals(t, get_term());
	}
	
	List<Expression> get_list() {
		ArrayList<Expression> v = new ArrayList<>();
		if (pos >= s.length() || s.charAt(pos) != '(') return v;
		pos++;
		v.add(get_term());
		if (pos < s.length() && s.charAt(pos) != ')') {
			pos++;
			v.add(get_term());
		}
		pos++;
		return v;
	}
	
	Expression get_term() {
		Expression t = get_sum();
		while (pos < s.length() && s.charAt(pos) == '+') {
			pos++;
			Expression t2 = get_sum();
			t = new Sum(t, t2);
		}
		return t;
	}
	
	Expression get_sum() {
		Expression t = get_mul();
		while (pos < s.length() && s.charAt(pos) == '*') {
			pos++;
			Expression t2 = get_mul();
			t = new Multi(t, t2);
		}
		return t;
	}
	
	Expression get_mul() {
		if (pos < s.length() && s.charAt(pos) == '(') {
			pos++;
			Expression t = get_term();
			if (s.charAt(pos) != ')') balance = false;
			pos++;
			return get_inc(t);
		}
		String name = get_str();
		if (pos < s.length() && s.charAt(pos) == '(') return get_inc(new Pred(name, get_list()));
		return get_inc(new Variable(name));
	}
	
	Expression get_inc(Expression x) {
	    while (pos < s.length() && s.charAt(pos) == '\'') {
	        pos++;
	        x = new Increment(x);
	    }
	    return x;
	}	
	
	
	String get_str() {
		int x = pos;
		while (x < s.length()) {
			char c = s.charAt(x);
			if ((c < '0' || c > '9') && (c < 'a' || c > 'z')) break;
			x++;
			
		}
		int old = pos;
		pos = x;
		return s.substring(old, x);
	}
	
	String get_str2() {
		int x = pos;
		while (x < s.length()) {
			char c = s.charAt(x);
			if ((c < '0' || c > '9') && (c < 'A' || c > 'Z')) break;
			x++;
			
		}
		int old = pos;
		pos = x;
		return s.substring(old, x);
	}
	
	
}
