package com.skilldistillery.filmquery.database;

import java.util.List;
import java.sql.Connection;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Categ;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.InventoryItem;
import com.skilldistillery.filmquery.entities.Language;

public interface DatabaseAccessor {
	  Film findFilmById(int paramInt);
	  Actor findActorById(int actorId);
	  List<Actor> findActorsByFilmId(int filmId);
	  List<Film> findFilmByKeyword(String filmKeyword);
	  Language findLanuageById(int LanguageId);
	  List<Categ> findCategoriesByFilmId(int filmId);
	  List<InventoryItem> findItemsByFilmId(int filmId);
	}

