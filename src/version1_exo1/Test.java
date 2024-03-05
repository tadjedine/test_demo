package version1_exo1;


class Livre{
	static int count = 0;
	protected String id;
	public Livre() {}
	public Livre(String numbercategorie) {
		count++;
		this.id = Test.generateIDLivre(count,numbercategorie);
	}
}

class Examplaire extends Livre {
	static int count = 0;
	public Examplaire(Livre lv) {
		count++;
		this.id = Test.generateIDExamplaire(count, lv.id);
	}
}

public class Test {

	static String generateIDLivre(int numLivre,String numbercategorie) {
		String id = "";
		id = id.concat("L");
		id = id.concat(String.valueOf(numLivre));
		id = id.concat("C");
		id = id.concat(numbercategorie);
		return id;
	}

	
	static String generateIDExamplaire(int numExamplaire,String numLivre) {
		String id = "";
		id = id.concat("E");
		id = id.concat(String.valueOf(numExamplaire));
		id = id.concat(numLivre);
		return id;
	}
	
	public static void main(String[] args) {
		Livre lv = new Livre("7");
		System.out.println(lv.id);
		Examplaire expl1 = new Examplaire(lv);
		Examplaire expl2 = new Examplaire(lv);
		System.out.println(expl1.id+"/"+expl2.id);
	}

}
