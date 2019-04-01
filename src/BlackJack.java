import java.util.ArrayList;
import java.util.Scanner;
public class BlackJack {
	private Scanner input;
	private Deck deck;
	private Player dealer;
	private Player user;
	final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
	final String[] suits = {"Heart", "Diamond", "Spade", "Club"};
	final int[] value = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
	
	
	public BlackJack(){
		input= new Scanner(System.in);
		deck = new Deck(ranks, suits, value);
		dealer = new Player();
		user = new Player();
	}
		
	
	public Scanner getInput() {
		return input;
	}



	public Deck getDeck() {
		return deck;
	}


	public void setDeck(Deck deck) {
		this.deck = deck;
	}


	public Player getDealer() {
		return dealer;
	}


	public void setDealer(Player dealer) {
		this.dealer = dealer;
	}


	public Player getUser() {
		return user;
	}


	public void setUser(Player user) {
		this.user = user;
	}


	public static void main(String[] args){
		BlackJack bj = new BlackJack();
		
		
		
		bj.dealer.setName("Dealer");
		try{
			bj.getUser().setName();
		}catch (Exception e){
			
		}
		
		bj.getUser().setChipTotal(1000);
		bj.playGame();
		
	}
	
	public void displayRules(){
		System.out.println("BlackJack (One deck & dealer must stand on soft 17)");
	}
	
	public void playHand(){

		double ante = 0;
		boolean complete = false;
		do{
			try{
				do{
					System.out.println("Enter the ante");
					ante = Double.parseDouble(input.nextLine());
					if (ante > user.chipTotal){
						System.out.println("INVALID INPUT");
					}else{
						complete = true;
					}
				}while(ante >= user.chipTotal);
			}catch (Exception e){
				System.out.println("INVALID INPUT");
			}
		}while(!complete);
		
		user.drawCard(deck.deal());
		dealer.drawCard(deck.deal());
		user.drawCard(deck.deal());
		dealer.drawCard(deck.deal());
		displayStatus(false);
		
		complete = false;
		while(complete == false){
			int option = -1;
			if (user.calculateScore() == 21 || user.calculateScore() == -1){
				complete = true;
			}else{
				option = playerOptions();
				if (option == 4){
					ante += ante;
					complete = true;
				}
				if (option == 2){
					complete = true;
				}
			}
			
			
		}
		transferFunds(ante, reveal());
		
		user.hand.clear();
		dealer.hand.clear();
		deck.resetDeck(ranks, suits, value);
	}
	
	public int playHand(ArrayList<Card> cards){
		displayStatusSplit(false, cards);
		int doubled = 0;
		cards.add(deck.deal());
		boolean complete = false;
		while(complete == false){
			int option = -1;
			if (calculateScore(cards) == 21 || calculateScore(cards) == -1){
				complete = true;
			}else{
				option = playerOptionsSplit(cards);
				if (option == 4){
					complete = true;
					doubled = 1;
				}
				if (option == 2){
					complete = true;
				}
			}
			
			
		}
		int reveal = reveal();
		if (doubled == 1){
			reveal += reveal();
		}		
		user.hand.clear();
		dealer.hand.clear();
		deck.resetDeck(ranks, suits, value);
		return reveal;
	}
	
	public void playGame(){
		
		int option;
		boolean complete = false;
		do{
			try{
				do{
					System.out.println(user.name + ", your chip total is " + user.chipTotal);
					System.out.println("Type 1 to PLAY, Type 2 to CASH OUT");
					option = Integer.parseInt(input.nextLine());
					if (option == 1){
						playHand();
					}
					if (option == 2){
						System.out.println("Your Score is " + user.chipTotal);
						complete = true;
					}
					if (option != 1 && option != 2){
						System.out.println("INVALID INPUT");
					}
				}while(option != 2);
			}catch (Exception e){
			}
		}while(!complete);
		
		
	}
	
	public int playerOptionsSplit(ArrayList<Card> cards){
		int option = 0; 
		try{	
			System.out.println("Type 1 to HIT, 2 to STAND, 3 to SPLIT, 4 to DOUBLE DOWN");
			option = Integer.parseInt(input.nextLine());
			if (option == 1){
				cards.add(deck.deal());
				displayStatus(false);
			}else if (option == 2){
				//Do nothing
			}else if (option == 3){
				if (cards.get(0) == cards.get(1) && cards.size() == 2){
					split(cards);
				}
			}else if (option == 4){
				cards.add(deck.deal());
				displayStatus(false);
			}else {
				System.out.println("INVALD INPUT TRY AGAIN");
			}
		}catch (Exception e){
			System.out.println("INVALD INPUT TRY AGAIN");
		}
		
		return option;
	}
	
	public int playerOptions(){
		int option = 0; 
		try{	
			System.out.println("Type 1 to HIT, 2 to STAND, 3 to SPLIT, 4 to DOUBLE DOWN");
			option = Integer.parseInt(input.nextLine());
			if (option == 1){
				hit();
			}else if (option == 2){
				stand();
			}else if (option == 3){
				if (user.hand.get(0).pointValue() == user.hand.get(1).pointValue() && user.hand.size() == 2){
					split(user.hand);
				}
			}else if (option == 4){
				doubleDown();
			}else {
				System.out.println("INVALD INPUT TRY AGAIN");
			}
		}catch (Exception e){
			System.out.println("INVALD INPUT TRY AGAIN");
		}
		
		return option;
	}
	
	public void hit(){
		user.drawCard(deck.deal());
		displayStatus(false);
	}
	
	public void stand(){
		
	}
	
	public void doubleDown(){
		user.drawCard(deck.deal());
		displayStatus(false);
	}
	
	public void split(ArrayList<Card> card){
		int sum = 0;
		ArrayList<Card> A1 = new ArrayList<Card>();
		A1.add(card.get(0));
		sum += playHand(A1);
		
		ArrayList<Card> A2 = new ArrayList<Card>();
		A2.add(card.get(1));
		sum += playHand(A2);
		
	}
	
	public int reveal(){
		while(dealer.calculateScore() < 17 && dealer.calculateScore() < user.calculateScore() && dealer.calculateScore() != -1 && user.calculateScore() != -1){
			dealer.drawCard(deck.deal());
			displayStatus(true);
		}
		displayStatus(true);
		return calculateWinner();
	}
	
	public void displayStatus(boolean complete){
		if (!complete){
			System.out.println("Dealer Score: " + (dealer.calculateDealerScore()));
			
			System.out.print("Dealer Hand: ");
			System.out.println(dealer.hand.subList(1, dealer.hand.size()).toString() + " + MYSTERY CARD");			
			System.out.println("Your Score: " + ((user.calculateScore() == -1) ? "Bust" : user.calculateScore()));
			System.out.print("Your Hand: ");
			System.out.println(user.hand.toString());
			System.out.println("ChipTotal: " + user.getChipTotal());
			System.out.println("");
		}else{
			System.out.println("Dealer Score: " + ((dealer.calculateScore() == -1) ? "Bust" : dealer.calculateScore()));
			System.out.print("Dealer Hand: ");
			System.out.println(dealer.hand.subList(0, dealer.hand.size()).toString());			
			System.out.println("Your Score: " + ((user.calculateScore() == -1) ? "Bust" : user.calculateScore()));
			System.out.print("Your Hand: ");
			System.out.println(user.hand.toString());
			System.out.println("ChipTotal: " + user.getChipTotal());
			System.out.println("");
		}
		
	}
	
	public void displayStatusSplit(boolean complete, ArrayList<Card> card){
		if (!complete){
			System.out.println("Dealer Score: " + (dealer.calculateDealerScore()));
			
			System.out.print("Dealer Hand: ");
			System.out.println(dealer.hand.subList(1, dealer.hand.size()).toString() + " + MYSTERY CARD");			
			System.out.println("Your Score: " + ((calculateScore(card) == -1) ? "Bust" : calculateScore(card)));
			System.out.print("Your Hand: ");
			System.out.println(card.toString());
			System.out.println("ChipTotal: " + user.getChipTotal());
			System.out.println("");
		}else{
			System.out.println("Dealer Score: " + ((dealer.calculateScore() == -1) ? "Bust" : dealer.calculateScore()));
			System.out.print("Dealer Hand: ");
			System.out.println(dealer.hand.subList(0, dealer.hand.size()).toString());			
			System.out.println("Your Score: " + ((calculateScore(card) == -1) ? "Bust" : calculateScore(card)));
			System.out.print("Your Hand: ");
			System.out.println(card.toString());
			System.out.println("ChipTotal: " + user.getChipTotal());
			System.out.println("");
		}
		
	}
	
	
	public int calculateWinner(){
		if (user.calculateScore() == dealer.calculateScore()){
			System.out.println("It is a push");
			return 0;
		}
		if (user.calculateScore() > dealer.calculateScore()){
			System.out.println("You Win!");
			return 1;
		}
		if (user.calculateScore() < dealer.calculateScore()){
			System.out.println("You Lose");
			return -1;
		}
		
		return -1;
	}
	
	public void transferFunds(double i, int t){
		if (t == 0){
			user.setChipTotal(user.chipTotal);
		}else if (t == 1){
			user.setChipTotal(user.chipTotal + i);
		}else if (t == -1){
			user.setChipTotal(user.chipTotal - i);
		}
	}
	
	
	public int calculateScore(ArrayList<Card> cards){
		int _score = 0;
		ArrayList<Integer> aceIndex = new ArrayList<Integer>();
		for (int i = 0; i < cards.size(); i++){
			if (cards.get(i).pointValue() == 11){
				aceIndex.add(i);
			}
			_score += cards.get(i).pointValue();
		}
		for (int i = 0; i < aceIndex.size(); i++){
			if (_score > 21){
				_score -= 10;
			}
		}
		if (_score > 21){
			return -1;
		}
		return _score;
	}
}
