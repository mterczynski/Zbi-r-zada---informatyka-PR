package Zadania;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class Zadanie79 {

	class Okrag{
		public double x, y, r;
		
		public Okrag(double x, double y, double r) {
			this.x = x;
			this.y = y;
			this.r = r;
		}
	}
	
	Scanner plik_okregi;
	
	List<Okrag> okregi = new ArrayList<Okrag>();
	
	public Zadanie79() {
		try {
			plik_okregi = new Scanner(new File("Files/79/okregi.txt"));
			plik_okregi.useLocale(Locale.US);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<2000; i++) {
			Okrag okrag = new Okrag(plik_okregi.nextDouble(), plik_okregi.nextDouble(), plik_okregi.nextDouble());
			okregi.add(okrag);
		}
		
		pp1();
		pp2();
		pp3();
		pp4();
	}
	
	void pp1() {
		/* �wiartki:
			I: x>0, y>0
			II: x<0, y<0
			III: x<0, y<0
			IV: x>0, y<0 
		*/
		
		// liczniki dla �wiartek:
		int ile_cw1 = 0;
		int ile_cw2 = 0;
		int ile_cw3 = 0;
		int ile_cw4 = 0;
		int ile_zadna = 0;
		
		for(Okrag okrag: okregi) {
			
			boolean top = okrag.y + okrag.r > 0;
			boolean left = okrag.x - okrag.r > 0;
			boolean bottom = okrag.y - okrag.r > 0;
			boolean right = okrag.x + okrag.r > 0;
			
			if(bottom && left) {
				ile_cw1++;
			} else if(!right && bottom) {
				ile_cw2++;
			} else if(!top && !right) {
				ile_cw3++;
			} else if(left && !top) {
				ile_cw4++;
			} else {
				ile_zadna++;
			}
		}
		
		System.out.println();
		System.out.println("79.1: ");
		System.out.println("Ilo�� okr�g�w znajduj�cych si� w pe�ni w I �wiartce: " + ile_cw1);
		System.out.println("Ilo�� okr�g�w znajduj�cych si� w pe�ni w II �wiartce: " + ile_cw2);
		System.out.println("Ilo�� okr�g�w znajduj�cych si� w pe�ni w III �wiartce: " + ile_cw3);
		System.out.println("Ilo�� okr�g�w znajduj�cych si� w pe�ni w IV �wiartce: " + ile_cw4);
		System.out.println("Ilo�� okr�g�w nie znajduj�cych si� w pe�ni w jakiejkolwiek �wiartce: " + ile_zadna);
		System.out.println();
	}
	
	void pp2() {
		int ilePar = 0;
		
		for(int i=0; i<okregi.size(); i++) {
			Okrag okrag = okregi.get(i);
			
			for(int j=i+1; j<okregi.size(); j++) {
				Okrag okrag2 = okregi.get(j);
				boolean symetryVertical = okrag.x == -okrag2.x;
				boolean symetryHorizontal = okrag.y == -okrag2.y;
				
				if((symetryVertical && !symetryHorizontal) || (symetryHorizontal && !symetryVertical) && okrag.r == okrag2.r) {
					ilePar++;
				}
			
			}
		}
		
		System.out.println();
		System.out.println("79.2: Ilo�� lustrzanych par okr�g�w: " + ilePar);
		System.out.println();
	}

	void pp3() {
		int ilePar = 0;
		
		for(int i=0; i<okregi.size(); i++) {
			Okrag okrag = okregi.get(i);
			
			for(int j=i+1; j<okregi.size(); j++) {
				Okrag okrag2 = okregi.get(j);
				boolean prostopadlosc1 = (okrag.x == okrag2.y && okrag.y == -okrag2.x);
				boolean prostopadlosc2 = (okrag.x == -okrag2.y && okrag.y == okrag2.x);
				boolean prostopadlosc = (prostopadlosc2 || prostopadlosc1);
				
				if(okrag.r == okrag2.r && prostopadlosc) {
					ilePar++;
				}
			}
		}
		
		System.out.println();
		System.out.println("79.3: Ilo�� prostopad�ych par okr�g�w: " + ilePar);
		System.out.println();
	}

	void pp4() {
		List<Integer> dlugosciLancuchow = new ArrayList<Integer>();
		List<Okrag> obecnyLancuch = new ArrayList<Okrag>();
		
		obecnyLancuch.add(okregi.get(0));
		
		for(int i=0; i<999; i++) {
			Okrag okrag = okregi.get(i);
			Okrag okrag2 = okregi.get(i+1);
			
			double distance = new Point2D.Double(okrag.x, okrag.y).distance(okrag2.x, okrag2.y);
			
			if(distance > okrag.r + okrag2.r) { 
				// okr�gi rozbie�ne
			} else if(distance < Math.abs(okrag.r - okrag2.r)) {
				// jeden okr�g w �rodku drugiego
			} else if(distance == 0 && okrag.r == okrag2.r) {
				// niesko�czenie wiele punkt�w wsp�lnych
				obecnyLancuch.add(okrag2);
				continue;
			} else if(distance == okrag.r + okrag2.r) {
				// jeden punkt wsp�lny
				obecnyLancuch.add(okrag2);
				continue;
			} else if(distance < okrag.r + okrag2.r) {
				// dwa punkty wsp�lne
				obecnyLancuch.add(okrag2);
				continue;
			} 
			// gdy brak punkt�w wsp�lnych:
			dlugosciLancuchow.add(obecnyLancuch.size());
			obecnyLancuch = new ArrayList<Okrag>(); 
			obecnyLancuch.add(okrag2);
		}
		
		dlugosciLancuchow.add(obecnyLancuch.size());
		
		System.out.println();
		System.out.println("79.4: D�ugo�ci �a�cuch�w: ");
		dlugosciLancuchow.forEach((dlugosc)->{
			System.out.println(dlugosc);
		});
		System.out.println("Najd�u�szy �a�cuch: ");
		Collections.sort(dlugosciLancuchow);
		System.out.println(dlugosciLancuchow.get(dlugosciLancuchow.size() - 1));
		System.out.println();
	}

}
