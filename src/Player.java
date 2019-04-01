import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	ArrayList<Card> hand;
	ArrayList<Card> hand2;
	String name;
	double chipTotal;
	boolean type; // false is dealer, true is player
	int score;
	
	public Player(){
		hand = new ArrayList<Card>();
		chipTotal = 1000;
	}
	
	
	
	public double getChipTotal() {
		return chipTotal;
	}



	public void setChipTotal(double chipTotal) {
		this.chipTotal = chipTotal;
	}



	public void setName(){
		System.out.println("Enter name");
		name = new Scanner(System.in).nextLine();
	}
	
	public void setName(String _name){
		name = _name;
	}
	
	public void setType(boolean i){
		type = i;
	}
	
	public int drawCard(Card c){
		hand.add(c);
		return calculateScore();
	}
	
	public int calculateScore(){
		int _score = 0;
		ArrayList<Integer> aceIndex = new ArrayList<Integer>();
		for (int i = 0; i < hand.size(); i++){
			if (hand.get(i).pointValue() == 11){
				aceIndex.add(i);
			}
			_score += hand.get(i).pointValue();
		}
		for (int i = 0; i < aceIndex.size(); i++){
			if (_score > 21){
				_score -= 10;
			}
		}
		if (_score > 21){
			score = -1;
			return -1;
		}
		score = _score;
		return _score;
	}
	
	public int calculateDealerScore(){
		int _score = 0;
		ArrayList<Integer> aceIndex = new ArrayList<Integer>();
		for (int i = 1; i < hand.size(); i++){
			if (hand.get(i).pointValue() == 11){
				aceIndex.add(i);
			}
			_score += hand.get(i).pointValue();
		}
		for (int i = 0; i < aceIndex.size(); i++){
			if (_score > 21){
				_score -= 10;
			}
		}
		return _score;
	}
}
