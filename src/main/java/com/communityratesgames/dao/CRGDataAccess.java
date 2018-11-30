package com.communityratesgames.dao;

import com.communityratesgames.domain.*;
import com.communityratesgames.model.CompanyModel;
import com.communityratesgames.model.RatingModel;
import com.communityratesgames.transactions.*;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class CRGDataAccess implements DataAccessLocal, DataAccessRemote {

    private final static Logger logger = Logger.getLogger(com.communityratesgames.dao.CRGDataAccess.class);

    //Injects to all interfaces for each entity

    @Inject
    private CompanyDataAccess companyDataAccess;

    @Inject
    private GameDataAccess gameDataAccess;

    @Inject
    private PlatformDataAccess platformDataAccess;

    @Inject
    private RatingDataAccess ratingDataAccess;

    @Inject
    private UserDataAccess userDataAccess;

    //User Access
    public List<User> showAllUsers() {return userDataAccess.showAllUsers();}
    public User register(User user) {
        return userDataAccess.register(user);
    }
    public User login(String email, String password) {return userDataAccess.login(email, password);}

    //Game Access
    public List<Game> showAllGames() {return gameDataAccess.showAllGames();}
    public Game gameByTitle(String title) {
        return gameDataAccess.gameByTitle(title);
    }
    public Game gameById(Long id) {
        return gameDataAccess.gameById(id);
    }
    public String searchFiveGames(String query) {
        return gameDataAccess.searchFiveGames(query);
    }
    public Game createNewGame(Game newGame) {
        return gameDataAccess.createNewGame(newGame);
    }
    public List<Game> getTopRatedGames(Integer limit, Integer page) {
        return gameDataAccess.getTopRatedGames(limit, page);
    }

    //Rating Access
    public List<Rating> showAllRatings() {return ratingDataAccess.showAllRatings();}
    public List<Rating> findRatingsByGameId(Long gameId) {
        return ratingDataAccess.findRatingsByGameId(gameId);
    }
    public float getAverageOfGame(Long gameId) {
        return ratingDataAccess.getAverageOfGame(gameId);
    }
    public Rating findByGameIdAndUserId(Long gameId, Long userId) {
        return ratingDataAccess.findByGameIdAndUserId( gameId, userId);
    }
    public void addNewRating(Rating rating) {
        ratingDataAccess.addNewRating(rating);
    }

    //Company Access
    public List<Company> showAllCompanies() {
        return companyDataAccess.showAllCompanies();
    }
    public Company registerNewCompany(CompanyModel companyModel){ return companyDataAccess.registerNewCompany(companyModel); }

    //Platform Access
    public List<Platform> showAllPlatforms() {return platformDataAccess.showAllPlatforms();}
}
