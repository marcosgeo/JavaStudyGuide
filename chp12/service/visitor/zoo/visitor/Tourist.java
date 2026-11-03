package chp12.service.visitor.zoo.visitor;

import java.util.*;

import chp12.service.api.zoo.tours.api.*;
import chp12.service.reservation.zoo.tours.reservations.TourFinder;


public class Tourist {
  public static void main(String[] args) {
    Tour  tour = TourFinder.findSingleTour();
    System.out.println("Single tour: " + tour);

    List<Tour> tours = TourFinder.findAllTours();
    System.out.println("# tours: " + tours.size());
  }
}
