public class PlayerSmart implements Player
{
	// Constructs a new instance of the PlayerSmart class
	public PlayerSmart()
	{
		
	}

	// Returns the Location where this Player chooses to move
	@Override
	public Location getNextMove(Board board, int player)
	{		
		Location nextMove = new Location(0, 0);
		int score = 0;
		int tempScore = 0;

		
		for(int i = 0; i < board.getRows(); i++) {
			for(int j = 0; j < board.getColumns(); j++) {
				if(board.getPlayer(new Location(i, j)) == board.PLAYER_NONE) {
					tempScore = 0;
					
					
					for(int a = 0; a < 1000; a++) {
						Board clone = new Board(board);
						clone.setPlayer(new Location(i, j), player);
						
						boolean isPlayersTurn = false;
							PlayerRandom p1 = new PlayerRandom();
							
							while(clone.getCurrentWinner() == board.PLAYER_NONE) {
								if (!isPlayersTurn)
									clone.setPlayer(p1.getNextMove(clone, 1), isPlayersTurn?player:(player== 1?2:1));
								else
									clone.setPlayer(p1.getNextMove(clone, 1), player);
								
								isPlayersTurn = !isPlayersTurn;
							}
							
							if(clone.getCurrentWinner() == player) {
								tempScore++;
							}
					}
					
					if(tempScore > score) {
						nextMove = new Location(i, j);
						score = tempScore;
					}
				}
			}
		}
		
		return nextMove;
	}
}
