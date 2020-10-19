package com.skilldistillery.filmquery.database;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Categ;
import com.skilldistillery.filmquery.entities.Condition;
import com.skilldistillery.filmquery.entities.Feature;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.InventoryItem;
import com.skilldistillery.filmquery.entities.Language;
import com.skilldistillery.filmquery.entities.Rating;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private Connection connect;
	private String connectString="jdbc:mysql://localhost:3306/sdvid?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public DatabaseAccessorObject() {
		try{  
			connect=DriverManager.getConnection(connectString,"root","");  
			//System.out.println("connected");
			} catch(Exception e) { 
				System.out.print("Database connection failed with message : "+e.getMessage());
				}
	}

	@Override
	public Film findFilmById(int filmId) {
		String sql = "SELECT * FROM `film` WHERE "
    			+ "`Id` lIKE ?";
		DatabaseAccessorObject conn = new DatabaseAccessorObject();
    	try {
    		PreparedStatement pst = conn.connect.prepareStatement(sql); 
    		pst.setString(1, Integer.toString(filmId));
    		ResultSet rs = pst.executeQuery();
			if (rs.next()) { 
				//System.out.println("film found!");
				int id = rs.getInt(0);  
				String title = rs.getString(1);
				String description = rs.getString(2);
				short releaseYear = rs.getShort(3);
				short languageId = rs.getShort(4);
				short rentalDuration = rs.getShort(5);
				double rentalRate = rs.getDouble(6);
				short length = rs.getShort(7);
				double replacementCost = rs.getDouble(8);
				Rating rating = Rating.valueOf(rs.getString(9));
				Set<Feature> specialFeatures = new HashSet<Feature>();
 		        String[] tempArray;
		        String delimiter = ",";
		        tempArray = rs.getString(10).split(delimiter);
		        for (int i = 0; i < tempArray.length; i++)
		        	specialFeatures.add(Feature.valueOf(tempArray[i]));
				  
				Film foundFilm = new Film(id, title, description, releaseYear, languageId,
						rentalDuration, rentalRate, length, replacementCost, rating, specialFeatures);
				foundFilm.setActors(findActorsByFilmId(filmId));
				return foundFilm;
			} 
			rs.close();
		    pst.close();
			conn.connect.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Warning! "+e1.getMessage());
		} 		    	
    return null;
  }
	@Override
	public Actor findActorById(int actorId) {
		String sql = "SELECT * FROM `actor` WHERE "
    			+ "`Id` lIKE ?";
		DatabaseAccessorObject conn = new DatabaseAccessorObject();
    	try {
    		PreparedStatement pst = conn.connect.prepareStatement(sql); 
    		pst.setString(1, Integer.toString(actorId));
    		ResultSet rs = pst.executeQuery();
			if (rs.next()) { 
				//System.out.println("actor found!");
				Actor foundActor = new Actor(rs.getInt(0), rs.getString(1), rs.getString(2));
				return foundActor;
			} 
			rs.close();
		    pst.close();
			conn.connect.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Warning! "+e1.getMessage());
		} 		    	
    return null;
  }
	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		String sql = "SELECT `actor_Id`, `first_Name`, `last_Name`, `film_Id`, `Id` "+
						"FROM `actor`, `film_actor` WHERE "+
						"`Id` lIKE `actor_Id` AND `film_Id` lIKE ?";
		DatabaseAccessorObject conn = new DatabaseAccessorObject();
		List<Actor> actors = new LinkedList<Actor>();
		try {
			PreparedStatement pst = conn.connect.prepareStatement(sql); 
    		pst.setString(1, Integer.toString(filmId));
    		ResultSet rs = pst.executeQuery();
			while (rs.next()) { 
				//System.out.println("film found!");
				Actor foundActor = new Actor(rs.getInt(0), rs.getString(1), rs.getString(2));
				actors.add(foundActor);
			} 
			rs.close();
		    pst.close();
			conn.connect.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Warning! "+e1.getMessage());
		} 		    	
    return actors;
  }
	@Override
	public List<Film> findFilmByKeyword(String filmKeyword) {
		String sql = "SELECT * FROM `film` WHERE "
    			+ "`TITLE` LIKE ? OR `Description` LIKE ?";
		DatabaseAccessorObject conn = new DatabaseAccessorObject();
		List<Film> films = new LinkedList<Film>();
		try {
			PreparedStatement pst = conn.connect.prepareStatement(sql); 
			pst.setString(1, "%"+filmKeyword+"%");
			pst.setString(2, "%"+filmKeyword+"%");
    		ResultSet rs = pst.executeQuery();
			while (rs.next()) { 
				//System.out.println("film found!");
				int id = rs.getInt(0);  
				String title = rs.getString(1);
				String description = rs.getString(2);
				short releaseYear = rs.getShort(3);
				short languageId = rs.getShort(4);
				short rentalDuration = rs.getShort(5);
				double rentalRate = rs.getDouble(6);
				short length = rs.getShort(7);
				double replacementCost = rs.getDouble(8);
				Rating rating = Rating.valueOf(rs.getString(9));
				Set<Feature> specialFeatures = new HashSet<Feature>();
 		        String[] tempArray;
		        String delimiter = ",";
		        tempArray = rs.getString(10).split(delimiter);
		        for (int i = 0; i < tempArray.length; i++)
		        	specialFeatures.add(Feature.valueOf(tempArray[i]));
				  
				Film foundFilm = new Film(id, title, description, releaseYear, languageId,
						rentalDuration, rentalRate, length, replacementCost, rating, specialFeatures);
				foundFilm.setActors(findActorsByFilmId(id));
				films.add(foundFilm);
			} 
			rs.close();
		    pst.close();
			conn.connect.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Warning! "+e1.getMessage());
		} 		    	
    return films;
	}
	@Override
	public Language findLanuageById(int languageId) {
		String sql = "SELECT * FROM `language` WHERE "
    			+ "`Id` lIKE ?";
		DatabaseAccessorObject conn = new DatabaseAccessorObject();
    	try {
    		PreparedStatement pst = conn.connect.prepareStatement(sql); 
    		pst.setString(1, Integer.toString(languageId));
    		ResultSet rs = pst.executeQuery();
			if (rs.next()) { 
				//System.out.println("language found!");
				Language foundLanguage = new Language(rs.getInt(0), rs.getString(1));
				return foundLanguage;
			} 
			rs.close();
		    pst.close();
			conn.connect.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Warning! "+e1.getMessage());
		} 		    	
    return null;
	}
	@Override
	public List<Categ> findCategoriesByFilmId(int filmId) {
		String sql = "SELECT `category_Id`, `name`, `film_Id`, `Id` "+
				"FROM `category`, `film_category` WHERE "+
				"`Id` lIKE `category_Id` AND `film_Id` lIKE ?";
		DatabaseAccessorObject conn = new DatabaseAccessorObject();
		List<Categ> categories = new LinkedList<Categ>();
		try {
			PreparedStatement pst = conn.connect.prepareStatement(sql); 
    		pst.setString(1, Integer.toString(filmId));
    		ResultSet rs = pst.executeQuery();
			while (rs.next()) { 
				//System.out.println("film found!");
				Categ foundCateg = new Categ(rs.getInt(0), rs.getString(1));
				categories.add(foundCateg);
			} 
			rs.close();
		    pst.close();
			conn.connect.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Warning! "+e1.getMessage());
		} 		    	
    return categories;
	}
	@Override
	public List<InventoryItem> findItemsByFilmId(int filmId) {
		String sql = "SELECT * FROM `Inventory_item` WHERE "+
				"`film_Id` lIKE ?";
		DatabaseAccessorObject conn = new DatabaseAccessorObject();
		List<InventoryItem> items = new LinkedList<InventoryItem>();
		try {
			PreparedStatement pst = conn.connect.prepareStatement(sql); 
    		pst.setString(1, Integer.toString(filmId));
    		ResultSet rs = pst.executeQuery();
			while (rs.next()) { 
				//System.out.println("film found!");
				Condition mediaCondition = Condition.valueOf(rs.getString(3));
				InventoryItem foundItem = new InventoryItem(rs.getInt(0), rs.getInt(1), rs.getShort(2), mediaCondition, rs.getTimestamp(4));
				items.add(foundItem);
			} 
			rs.close();
		    pst.close();
			conn.connect.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Warning! "+e1.getMessage());
		} 		    	
    return items;
	}
}

