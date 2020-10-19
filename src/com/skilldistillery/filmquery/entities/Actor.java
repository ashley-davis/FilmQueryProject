package com.skilldistillery.filmquery.entities;

public class Actor {
int actorId;
int filmId;
String firstName;
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
String lastName;


public int getActorId() {
	return actorId;
}
public void setActorId(int actorId) {
	this.actorId = actorId;
}
public int getFilmId() {
	return filmId;
}
public void setFilmId(int filmId) {
	this.filmId = filmId;
}
@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
}
