import java.util.ArrayList;

public class Deck extends ArrayList{
		
	public Deck(String[] _ranks, String[] _suits, int[] _values) {
		for (int j = 0; j < _ranks.length; j++) {
			for (String suitString : _suits) {
				this.add(new Card(_ranks[j], suitString, _values[j]));
			}
		}
		shuffle();
	}
	
	public void resetDeck(String[] _ranks, String[] _suits, int[] _values){
		this.clear();
		for (int j = 0; j < _ranks.length; j++) {
			for (String suitString : _suits) {
				this.add(new Card(_ranks[j], suitString, _values[j]));
			}
		}
		shuffle();
	}


	
	public boolean isEmpty() {
		return this.size() == 0;
	}


	
	public void shuffle() {
		for( int k = this.size() - 1; k >= 0; k-- ) {
            int r = (int)(Math.random() * k);
            Card tmp = (Card) this.get(r);
            this.set(r, this.get(k));
            this.set(k, tmp);
        }
	}

	
	public Card deal() {
		if (isEmpty()) {
			return null;
		}
		Card c = (Card) this.remove(this.size() - 1);
		return c;
	}

	
	@Override
	public String toString() {
		String rtn = "size = " + this.size() + "\nUndealt this: \n";

		for (int k = this.size() - 1; k >= 0; k--) {
			rtn = rtn + this.get(k);
			if (k != 0) {
				rtn = rtn + ", ";
			}
			if ((this.size() - k) % 2 == 0) {
				// Insert carriage returns so entire deck is visible on console.
				rtn = rtn + "\n";
			}
		}
		
		/*
		rtn = rtn + "\nDealt this: \n";
		for (int k = this.size() - 1; k >= size; k--) {
			rtn = rtn + this.get(k);
			if (k != size) {
				rtn = rtn + ", ";
			}
			if ((k - this.size()) % 2 == 0) {
				// Insert carriage returns so entire deck is visible on console.
				rtn = rtn + "\n";
			}
		}

		rtn = rtn + "\n";
		
		*/
		return rtn;
	}
}
