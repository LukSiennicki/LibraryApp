package library.model;

import library.exception.PublicationAlreadyExistsException;
import library.exception.UserAlreadyExsistsException;

import java.io.Serializable;
import java.util.*;

public class Library implements Serializable {

    private Map<String,Publication> publications = new HashMap<>();
    private Map<String,LibraryUser> users = new HashMap<>();

    public Map<String, Publication> getPublications() {
        return publications;
    }

    public Collection<Publication> getSortedPublication(Comparator<Publication> comparator){
        List<Publication> list = new ArrayList<>(this.publications.values());
        list.sort(comparator);
        return list;
    }

    public Optional<Publication> findPublicationbyTitle(String title){
        return Optional.ofNullable(publications.get(title));
    }

    public Map<String, LibraryUser> getUsers() {
        return users;
    }

    public Collection<LibraryUser> getSortedUsers(Comparator<LibraryUser> comparator){
        List<LibraryUser> list = new ArrayList<>(users.values());
        list.sort(comparator);
        return list;
    }

    public void addPublications(Publication publication) {
        if (publications.containsKey(publication.getTitle())){
            throw new PublicationAlreadyExistsException("publikacja o takim tytule juz istnieje " + publication.getTitle());
        }
        publications.put(publication.getTitle(),publication);
    }

    public void addUser(LibraryUser libraryUser){
        if(users.containsKey(libraryUser.getPesel())){
            throw new UserAlreadyExsistsException("Uzytkownik ze wskazanym peselem juz istnieje " + libraryUser.getPesel());
        }
        users.put(libraryUser.getPesel(),libraryUser);
    }

    public boolean removePublication(Publication publication) {
      if (publications.containsValue(publication)){
          publications.remove(publication.getTitle());
          return true;
      } else {
          return false;
      }
    }
}
