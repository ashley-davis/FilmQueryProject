package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Categ;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.InventoryItem;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();
	  public static void main(String[] args) {
		    FilmQueryApp app = new FilmQueryApp();
		    app.test();
		    app.launch();
		  }
		  
		  private void test() {
		  Film film = db.findFilmById(1);
		  System.out.println(film);
		  }
		  
		  private void launch() {
		    Scanner input = new Scanner(System.in);
		    startUserInterface(input);
		    input.close();
		  }
		  
		  private void startUserInterface(Scanner input) {
			String choice = "";
			while (!choice.equals("3")) {
			System.out.println("Please type your choice by number :");
			System.out.println("1. Look up a film by its id");
			System.out.println("2. Look up a film by a search keyword");
			System.out.println("3. Exit the application");
			choice = input.nextLine();
			switch (choice) {
				case "1" :
					int filmId = input.nextInt();
					Film film = db.findFilmById(filmId);
					if (film == null) 
						System.out.println("Film not found!");
					else {
						System.out.println("Film [Title: "+film.getTitle()+", Year: "+film.getReleaseYear()+
								", Rating: "+film.getRating().toString()+", Description: "+film.getDescription()+
								", Language: "+db.findLanuageById(film.getLanguageId()).getName()+"]");
						List<Actor> actors= db.findActorsByFilmId(film.getId());
						System.out.print("Film Actors : ");
						for (int i=0; i<actors.size(); i++) {
							if (i>0) System.out.print(", ");
							System.out.print(actors.get(i).getFirstName()+" "+actors.get(i).getLastName());
						}
						System.out.println("\nPlease choose an option by number :");
						System.out.println("1. Return to the main menu");
						System.out.println("2. View all film details");
						String subChoice = input.nextLine();
						if (subChoice.equals("2")) {
							System.out.print(film.toString());
							List<Categ> categories = db.findCategoriesByFilmId(film.getId());
							System.out.print("Film Categories : ");
							for (int i=0; i<categories.size(); i++) { 
								if (i>0) System.out.print(", ");
								System.out.print(categories.get(i).getName());
								}
							List<InventoryItem> items = db.findItemsByFilmId(film.getId());
							System.out.print("\nFilm Copies : ");
							for (int i=0; i<items.size(); i++) { 
								if (i>0) System.out.print(", ");
								System.out.print("(Item Id: "+items.get(i).getItemId()+
											", Condition: "+items.get(i).getMediaCondition().toString()+")");
								}
						}
					}
					break;
				case "2" : 
					String filmKeyword = input.nextLine();
					List<Film> films = db.findFilmByKeyword(filmKeyword);
					if (films.size()==0) 
						System.out.println("No matching films are found for this keyword!");
					else {
						for (int i=0; i<films.size(); i++) {
							System.out.println("[Title: "+films.get(i).getTitle()+", Year: "+films.get(i).getReleaseYear()+
								", Rating: "+films.get(i).getRating().toString()+", Description: "+films.get(i).getDescription()+"]");
						}
					}
					break;
				default : break;
			}
			}
		  }
		}

