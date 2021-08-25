package academy.learnprogramming;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MessageGeneratorImpl implements MessageGenerator {

    private static final String MAIN_MESSAGE="game.main.message";
    private static final String WIN = "game.win";
    private static final String LOSE = "game.lost";
    private static final String INVALID_RANGE = "game.invalid.range";
    private static final String FIRST_GUESS = "game.first.guess";
    private static final String HIGHER = "game.higher";
    private static final String LOWER = "game.lower";
    private static final String REMAINING = "game.remaining";
    private MessageSource messageSource;



    // == fields ==
    private final Game game;

    // == constructor ==

    @Autowired
    public MessageGeneratorImpl(Game game,MessageSource messageSource) {
        this.messageSource=messageSource;
        this.game = game;
    }

    // == init ==
    @PostConstruct
    public void init() {
        log.info("game = {}", game);
    }
    // == public methods ==
    @Override
    public String getMainMessage() {

        return getMessage(MAIN_MESSAGE,game.getSmallest(),game.getBiggest());
//        return "Number is between " +
//                game.getSmallest() +
//                " and " +
//                game.getBiggest() +
//                ". Can you guess it?";
    }

    @Override
    public String getResultMessage() {

        if(game.isGameWon()) {
            return getMessage(WIN,game.getNumber());
//            return "You guessed it! The number was " + game.getNumber();
        } else if(game.isGameLost()) {
            return getMessage(LOSE,game.getNumber());
//            return "You lost. The number was " + game.getNumber();
        } else if(!game.isValidNumberRange()) {
            return getMessage(INVALID_RANGE);
//            return "Invalid number range!";
        } else if(game.getRemainingGuesses() == game.getGuessCount()) {

            return getMessage(FIRST_GUESS);
//            return "What is your first guess?";
        } else {
            String direction = getMessage(LOWER);

            if(game.getGuess() < game.getNumber()) {
                direction = getMessage(HIGHER);
            }

            return getMessage(REMAINING,direction,game.getRemainingGuesses());
//            return direction + "! You have " + game.getRemainingGuesses() + " guesses left";
        }
    }


    private String getMessage(String code, Object... args){
        return messageSource.getMessage(code,args, LocaleContextHolder.getLocale());
    }
}
