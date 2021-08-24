package academy.learnprogramming.service;

public interface GameService {
    
    boolean isGameOver();
    String getMainMessage();
    
    String getResultMessage();

    void checkGues(int Guess);

    void reset();
}
