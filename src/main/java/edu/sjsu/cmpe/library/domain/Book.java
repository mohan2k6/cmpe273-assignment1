package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.fasterxml.jackson.annotation.JsonProperty;
@JsonPropertyOrder({ "isbn", "title", "publication-date", "language", "num-pages", "status", "reviews", "authors"})

public class Book {
	
	@JsonProperty
    private long isbn;
	
	@JsonProperty
	private String title;
	
	@JsonProperty("publication-date")
    private String pubdate;
    
	@JsonProperty
    private String language;
	
	@JsonProperty("num-pages")
    private int npages;
    
	@JsonProperty
    private String status="available";
	
	@JsonProperty
    private List<Review> reviews = new ArrayList<Review>();
	
	@JsonProperty
    private Author[] authors;
    
    
    // add more fields here

    /**
     * @return the isbn
     */
    public long getIsbn() {
	return this.isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }
    
    public String getPubdate() {
    	return this.pubdate;
        }
    
    public void setPubdate(String pubdate) {
    	this.pubdate = pubdate;
        }
    
    public String getLanguage() {
    	return this.language;
        }
    
    public void setLanguage(String language) {
    	this.language = language;
        }
    
    public int getNpages(){
    	return this.npages;
    }
    
   public void setNpages(int npages){
	   this.npages=npages;
   }
   
   public String getStatus(){
	   return this.status;
   }
   
   public void setStatus(String status){
	   this.status=status;
   }
   
   public Author[] getAuthors() {
		return this.authors;
	}

   
   public void setAuthors(Author[] authors) {
		this.authors = authors;
	}

   
   public List<Review> getReviews() {
		return this.reviews;
	}
   
   
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	
	public Author getbyAuthors(int id) {
		return this.authors[id];	
	}

	
	public Review getbyReview(int id) {
		return this.reviews.get(id);
	}

   
   
  
  
}

