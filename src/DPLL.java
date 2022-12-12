import java.util.*;
public class DPLL {
    public final List<Object[]> solutions = new ArrayList<>();
    private final Stack<Integer> stack = new Stack<>();
    public List<Integer> longueurClause;
    public List<List<Integer>> litteralClause;
    public List<List<Integer>> clauseLitteral;
    public List<Integer> etatLitteral;
    public List<Integer> etatClause;
    public boolean solve(boolean oneSol, String heuristique) {
        switch (heuristique) {
            case "satisfy" -> solve_Satisfy(oneSol);
            case "fail" -> solve_Fail(oneSol);
            default -> solve_Order(oneSol);
        }
        return !solutions.isEmpty();
    }
    private void solve_Order(boolean oneSol) {
        reset();
        Integer litV;
        int litB;
        while (isNotOver()) {
            if(monoLit()) ;
            else if(purLit()) ;
            else simpleOrder();
            for(List<Integer> c : clauseLitteral) {
                if(c.size()!=0) continue;
                while (etatLitteral.get(stack.peek())==2) {
                    if(stack.size()==1) {
                        if(!etatLitteral.contains(0) && !etatClause.contains(-1)) solutions.add(stack.toArray());
                        return;
                    }
                    litV=stack.pop();
                    litB=getLitB(litV);
                    etatLitteral.set(litV,0);
                    etatLitteral.set(litB,0);
                    for(Integer clause : litteralClause.get(litV)) {
                        if(isAlreadyTrue(clause)) continue;
                        longueurClause.set(clause,clauseLitteral.get(clause).size());
                        etatClause.set(clause,-1);
                    }
                    for(Integer clause : litteralClause.get(litB)) {
                        clauseLitteral.get(clause).add(litB);
                        if(!isAlreadyTrue(clause)) longueurClause.set(clause,longueurClause.get(clause)+1);
                    }
                }
                if(etatLitteral.get(stack.peek())==1) {
                    litV = stack.pop();
                    litB = getLitB(litV);
                    etatLitteral.set(litV, 2);
                    etatLitteral.set(litB, 2);
                    push(litB);
                    for (Integer clause : litteralClause.get(litV)) {
                        clauseLitteral.get(clause).remove(litV);
                        longueurClause.set(clause, clauseLitteral.get(clause).size());
                        if(!isAlreadyTrue(clause)) etatClause.set(clause, -1);
                    }
                    for (Integer clause : litteralClause.get(litB)) {
                        clauseLitteral.get(clause).add(litB);
                        longueurClause.set(clause, 0);
                        etatClause.set(clause, 1);
                    }
                }
            }
            if(!etatLitteral.contains(0)) {
                if(!etatClause.contains(-1)) {
                    solutions.add(stack.toArray());
                    if(oneSol) return;
                }
                while (etatLitteral.get(stack.peek())==2) {
                    if(stack.size()==1) {
                        if(!etatLitteral.contains(0) && !etatClause.contains(-1)) solutions.add(stack.toArray());
                        return;
                    }
                    litV=stack.pop();
                    litB=getLitB(litV);
                    etatLitteral.set(litV,0);
                    etatLitteral.set(litB,0);
                    for(Integer clause : litteralClause.get(litV)) {
                        if(isAlreadyTrue(clause)) continue;
                        longueurClause.set(clause,clauseLitteral.get(clause).size());
                        etatClause.set(clause,-1);
                    }
                    for(Integer clause : litteralClause.get(litB)) {
                        clauseLitteral.get(clause).add(litB);
                        if(!isAlreadyTrue(clause)) longueurClause.set(clause,longueurClause.get(clause)+1);
                    }
                }
                if(etatLitteral.get(stack.peek())==1) {
                    litV = stack.pop();
                    litB = getLitB(litV);
                    etatLitteral.set(litV, 2);
                    etatLitteral.set(litB, 2);
                    push(litB);
                    for (Integer clause : litteralClause.get(litV)) {
                        clauseLitteral.get(clause).remove(litV);
                        longueurClause.set(clause, clauseLitteral.get(clause).size());
                        if(!isAlreadyTrue(clause)) etatClause.set(clause, -1);
                    }
                    for (Integer clause : litteralClause.get(litB)) {
                        clauseLitteral.get(clause).add(litB);
                        longueurClause.set(clause, 0);
                        etatClause.set(clause, 1);
                    }
                }
            }
        }
        if(!etatLitteral.contains(0) && !etatClause.contains(-1)) solutions.add(stack.toArray());
    }
    private void solve_Satisfy(boolean oneSol) {
        reset();
        Integer litV;
        int litB;
        while (isNotOver()) {
            if(monoLit()) ;
            else if(purLit()) ;
            else firstSatisfy();
            for(List<Integer> c : clauseLitteral) {
                if(c.size()!=0) continue;
                while (etatLitteral.get(stack.peek())==2) {
                    if(stack.size()==1) {
                        if(!etatLitteral.contains(0) && !etatClause.contains(-1)) solutions.add(stack.toArray());
                        return;
                    }
                    litV=stack.pop();
                    litB=getLitB(litV);
                    etatLitteral.set(litV,0);
                    etatLitteral.set(litB,0);
                    for(Integer clause : litteralClause.get(litV)) {
                        if(isAlreadyTrue(clause)) continue;
                        longueurClause.set(clause,clauseLitteral.get(clause).size());
                        etatClause.set(clause,-1);
                    }
                    for(Integer clause : litteralClause.get(litB)) {
                        clauseLitteral.get(clause).add(litB);
                        if(!isAlreadyTrue(clause)) longueurClause.set(clause,longueurClause.get(clause)+1);
                    }
                }
                if(etatLitteral.get(stack.peek())==1) {
                    litV = stack.pop();
                    litB = getLitB(litV);
                    etatLitteral.set(litV, 2);
                    etatLitteral.set(litB, 2);
                    push(litB);
                    for (Integer clause : litteralClause.get(litV)) {
                        clauseLitteral.get(clause).remove(litV);
                        longueurClause.set(clause, clauseLitteral.get(clause).size());
                        if(!isAlreadyTrue(clause)) etatClause.set(clause, -1);
                    }
                    for (Integer clause : litteralClause.get(litB)) {
                        clauseLitteral.get(clause).add(litB);
                        longueurClause.set(clause, 0);
                        etatClause.set(clause, 1);
                    }
                }
            }
            if(!etatLitteral.contains(0)) {
                if(!etatClause.contains(-1)) {
                    solutions.add(stack.toArray());
                    if(oneSol) return;
                }
                while (etatLitteral.get(stack.peek())==2) {
                    if(stack.size()==1) {
                        if(!etatLitteral.contains(0) && !etatClause.contains(-1)) solutions.add(stack.toArray());
                        return;
                    }
                    litV=stack.pop();
                    litB=getLitB(litV);
                    etatLitteral.set(litV,0);
                    etatLitteral.set(litB,0);
                    for(Integer clause : litteralClause.get(litV)) {
                        if(isAlreadyTrue(clause)) continue;
                        longueurClause.set(clause,clauseLitteral.get(clause).size());
                        etatClause.set(clause,-1);
                    }
                    for(Integer clause : litteralClause.get(litB)) {
                        clauseLitteral.get(clause).add(litB);
                        if(!isAlreadyTrue(clause)) longueurClause.set(clause,longueurClause.get(clause)+1);
                    }
                }
                if(etatLitteral.get(stack.peek())==1) {
                    litV = stack.pop();
                    litB = getLitB(litV);
                    etatLitteral.set(litV, 2);
                    etatLitteral.set(litB, 2);
                    push(litB);
                    for (Integer clause : litteralClause.get(litV)) {
                        clauseLitteral.get(clause).remove(litV);
                        longueurClause.set(clause, clauseLitteral.get(clause).size());
                        if(!isAlreadyTrue(clause)) etatClause.set(clause, -1);
                    }
                    for (Integer clause : litteralClause.get(litB)) {
                        clauseLitteral.get(clause).add(litB);
                        longueurClause.set(clause, 0);
                        etatClause.set(clause, 1);
                    }
                }
            }
        }
        if(!etatLitteral.contains(0) && !etatClause.contains(-1)) solutions.add(stack.toArray());
    }
    private void solve_Fail(boolean oneSol) {
        reset();
        Integer litV;
        int litB;
        while (isNotOver()) {
            if(monoLit()) ;
            else if(purLit()) ;
            else firstFail();
            for(List<Integer> c : clauseLitteral) {
                if(c.size()!=0) continue;
                while (etatLitteral.get(stack.peek())==2) {
                    if(stack.size()==1) {
                        if(!etatLitteral.contains(0) && !etatClause.contains(-1)) solutions.add(stack.toArray());
                        return;
                    }
                    litV=stack.pop();
                    litB=getLitB(litV);
                    etatLitteral.set(litV,0);
                    etatLitteral.set(litB,0);
                    for(Integer clause : litteralClause.get(litV)) {
                        if(isAlreadyTrue(clause)) continue;
                        longueurClause.set(clause,clauseLitteral.get(clause).size());
                        etatClause.set(clause,-1);
                    }
                    for(Integer clause : litteralClause.get(litB)) {
                        clauseLitteral.get(clause).add(litB);
                        if(!isAlreadyTrue(clause)) longueurClause.set(clause,longueurClause.get(clause)+1);
                    }
                }
                if(etatLitteral.get(stack.peek())==1) {
                    litV = stack.pop();
                    litB = getLitB(litV);
                    etatLitteral.set(litV, 2);
                    etatLitteral.set(litB, 2);
                    push(litB);
                    for (Integer clause : litteralClause.get(litV)) {
                        clauseLitteral.get(clause).remove(litV);
                        longueurClause.set(clause, clauseLitteral.get(clause).size());
                        if(!isAlreadyTrue(clause)) etatClause.set(clause, -1);
                    }
                    for (Integer clause : litteralClause.get(litB)) {
                        clauseLitteral.get(clause).add(litB);
                        longueurClause.set(clause, 0);
                        etatClause.set(clause, 1);
                    }
                }
            }
            if(!etatLitteral.contains(0)) {
                if(!etatClause.contains(-1)) {
                    solutions.add(stack.toArray());
                    if(oneSol) return;
                }
                while (etatLitteral.get(stack.peek())==2) {
                    if(stack.size()==1) {
                        if(!etatLitteral.contains(0) && !etatClause.contains(-1)) solutions.add(stack.toArray());
                        return;
                    }
                    litV=stack.pop();
                    litB=getLitB(litV);
                    etatLitteral.set(litV,0);
                    etatLitteral.set(litB,0);
                    for(Integer clause : litteralClause.get(litV)) {
                        if(isAlreadyTrue(clause)) continue;
                        longueurClause.set(clause,clauseLitteral.get(clause).size());
                        etatClause.set(clause,-1);
                    }
                    for(Integer clause : litteralClause.get(litB)) {
                        clauseLitteral.get(clause).add(litB);
                        if(!isAlreadyTrue(clause)) longueurClause.set(clause,longueurClause.get(clause)+1);
                    }
                }
                if(etatLitteral.get(stack.peek())==1) {
                    litV = stack.pop();
                    litB = getLitB(litV);
                    etatLitteral.set(litV, 2);
                    etatLitteral.set(litB, 2);
                    push(litB);
                    for (Integer clause : litteralClause.get(litV)) {
                        clauseLitteral.get(clause).remove(litV);
                        longueurClause.set(clause, clauseLitteral.get(clause).size());
                        if(!isAlreadyTrue(clause)) etatClause.set(clause, -1);
                    }
                    for (Integer clause : litteralClause.get(litB)) {
                        clauseLitteral.get(clause).add(litB);
                        longueurClause.set(clause, 0);
                        etatClause.set(clause, 1);
                    }
                }
            }
        }
        if(!etatLitteral.contains(0) && !etatClause.contains(-1)) solutions.add(stack.toArray());
    }
    private void reset() {
        etatClause=Parser.etatClause;
        etatLitteral=Parser.etatLitteral;
        longueurClause=Parser.longueurClause;
        clauseLitteral=Parser.clauseLitteral;
        litteralClause=Parser.litteralClause;
        stack.clear();
        solutions.clear();
    }
    private boolean isAlreadyTrue(int clause) {
        for(Integer lit : stack)
            if(litteralClause.get(lit).contains(clause)) return true;
        return false;
    }
    private boolean monoLit() {
        for(int i=0;i<longueurClause.size();i++) {
            if(longueurClause.get(i)==1 && etatLitteral.get(clauseLitteral.get(i).get(0))==0) {
                int monoLit = clauseLitteral.get(i).get(0);
                etatClause.set(i,1);
                etatLitteral.set(monoLit,1);
                longueurClause.set(i,0);
                push(monoLit);
                int monoLitB = getLitB(monoLit);
                for(Integer clause : litteralClause.get(monoLit)) {
                    longueurClause.set(clause,0);
                    etatClause.set(clause,1);
                }
                for(Integer clause : litteralClause.get(monoLitB)) {
                    longueurClause.set(clause, longueurClause.get(clause)-1);
                    clauseLitteral.get(clause).remove((Integer) monoLitB);
                }
                etatLitteral.set(monoLitB,1);
                return true;
            }
        }
        return false;
    }
    private boolean pur(int i) {
        for(List<Integer> lits : clauseLitteral)
            if(lits.contains(getLitB(i))) return false;
        return true;
    }
    private boolean purLit() {
        for(int i=0;i<etatLitteral.size();i++) {
            if(etatLitteral.get(i)==0 && pur(i)) {
                etatLitteral.set(i,1);
                push(i);
                int monoLitB = getLitB(i);
                for(Integer clause : litteralClause.get(i)) {
                    longueurClause.set(clause,0);
                    etatClause.set(clause,1);
                }
                for(Integer clause : litteralClause.get(monoLitB)) {
                    longueurClause.set(clause, longueurClause.get(clause)-1);
                    clauseLitteral.get(clause).remove((Integer) monoLitB);
                }
                etatLitteral.set(monoLitB,1);
                return true;
            }
        }
        return false;
    }
    private void simpleOrder() {
        for(int i=0;i<etatLitteral.size();i++) {
            if (etatLitteral.get(i) == 0) {                // premier lit non affecter a vrai ou faux
                etatLitteral.set(i, 1);
                push(i);
                int monoLitB = getLitB(i);
                for (Integer clause : litteralClause.get(i)) {
                    longueurClause.set(clause, 0);
                    etatClause.set(clause, 1);
                }
                for (Integer clause : litteralClause.get(monoLitB)) {
                    longueurClause.set(clause, longueurClause.get(clause) - 1);
                    clauseLitteral.get(clause).remove((Integer) monoLitB);
                }
                etatLitteral.set(monoLitB, 1);
                return;
            }
        }
    }
    private void firstSatisfy() {
        int max=0;
        int lit=-1;
        for(int i=0;i<etatLitteral.size();i++) {
            if(max<litteralClause.get(i).size() && etatLitteral.get(i)==0) {
                max=litteralClause.get(i).size();lit=i;
            }
        }
        if(lit!=-1) {
            etatLitteral.set(lit, 1);
            push(lit);
            int monoLitB = getLitB(lit);
            for (Integer clause : litteralClause.get(lit)) {
                longueurClause.set(clause, 0);
                etatClause.set(clause, 1);
            }
            for (Integer clause : litteralClause.get(monoLitB)) {
                longueurClause.set(clause, longueurClause.get(clause) - 1);
                clauseLitteral.get(clause).remove((Integer) monoLitB);
            }
            etatLitteral.set(monoLitB, 1);
        }
    }
    private void firstFail() {
        int max=0;
        int lit=-1;
        for(int i=0;i<etatLitteral.size();i++) {
            if(max<litteralClause.get(i).size() && etatLitteral.get(i)==0) {
                max=litteralClause.get(i).size();lit=i;
            }
        }
        if(lit!=-1) {
            lit=getLitB(lit);
            etatLitteral.set(lit, 1);
            push(lit);
            int monoLitB = getLitB(lit);
            for (Integer clause : litteralClause.get(lit)) {
                longueurClause.set(clause, 0);
                etatClause.set(clause, 1);
            }
            for (Integer clause : litteralClause.get(monoLitB)) {
                longueurClause.set(clause, longueurClause.get(clause) - 1);
                clauseLitteral.get(clause).remove((Integer) monoLitB);
            }
            etatLitteral.set(monoLitB, 1);
        }
    }
    private int getLitB(int lit) {    // return l'inverse de lit
        return lit%2==0 ? lit+1 : lit-1;
    }
    private void push(int lit) {
        stack.push(lit);
    }
    private boolean isNotOver() {        // Tous les litteraux ont ete teste
        return etatLitteral.contains(0) || etatLitteral.contains(1);
    }
}
