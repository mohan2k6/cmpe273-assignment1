package edu.sjsu.cmpe.library.api.resources;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorsDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.LinksDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewsDto;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
 private static int isbn = 1;
 private static int authorid=1;
 private static int reviewid=1;
 private static Map<Integer,Book> bookNum = new HashMap<Integer,Book>();
  
 public BookResource() {
    	
        
    }
 
 /*
  * 2.Create Book API
  */
 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(name = "create-book")
public Response createBook(Book book){
    	
    	BookDto bookResponse = new BookDto(book);
    	book.setIsbn(isbn);
    	bookNum.put(isbn, book);
    	isbn++;	
    	
    	for (int author=0;author<book.getAuthors().length;author++)
		{
			book.getAuthors()[author].id=authorid;
			authorid++;

		}

    	bookResponse.addLink(new LinkDto("view-book", "books/" + book.getIsbn(), "GET"));
    	bookResponse.addLink(new LinkDto("update-book", "books/" + book.getIsbn(), "PUT"));
    	bookResponse.addLink(new LinkDto("delete-book", "books/" + book.getIsbn(), "DELETE"));
    	bookResponse.addLink(new LinkDto("create-Review", "books/" + book.getIsbn() + "/reviews/", "POST"));
    	return Response.status(201).entity(bookResponse.getLinks()).build();
    	
   }  
    
    /*
     *  3.View Book API
     */
    
    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public BookDto getBookByIsbn(@PathParam("isbn") int isbn) {
	// FIXME - Dummy code
	Book book = bookNum.get(isbn);
	BookDto bookResponse = new BookDto(book);
	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(), "GET"));
	bookResponse.addLink(new LinkDto("update-book","/books/" + book.getIsbn(), "PUT"));
	bookResponse.addLink(new LinkDto("delete-book","/books/" + book.getIsbn(), "DELETE"));
	bookResponse.addLink(new LinkDto("create-review","/books/" + book.getIsbn() + "/reviews", "POST"));
	if (book.getReviews().size() !=0 ){
	bookResponse.addLink(new LinkDto("view-all-reviews","/books/" + book.getIsbn() + "/reviews", "GET"));
	}
	

	return bookResponse;
    
    }
    
    /*
     * 4.Delete Book API
     */
    
    @DELETE
    @Path("/{isbn}")
    @Timed(name = "delete-book")
    public Response getDeleteByIsbn(@PathParam("isbn") int isbn) {
	Book book = bookNum.remove(isbn);
	BookDto bookResponse = new BookDto(book);
	
	bookResponse.addLink(new LinkDto("delete-book", "/books/", "POST"));
	
	return Response.ok().entity(bookResponse.getLinks()).build();	
	
    }
    
    /*
     * 5.Update Book API
     */
    
    @PUT
    @Path("/{isbn}")
    @Timed(name = "update-book")
    public Response getUpdateByIsbn(@PathParam("isbn") int isbn, @QueryParam("status") String newStatus) {
	Book updateBook = bookNum.get(isbn);
	updateBook.setStatus(newStatus);		
	BookDto bookResponse = new BookDto(updateBook);
	bookResponse.addLink(new LinkDto("view-book", "/books/" + updateBook.getIsbn(), "GET"));
	bookResponse.addLink(new LinkDto("update-book", "/books/" + updateBook.getIsbn(), "PUT"));
	bookResponse.addLink(new LinkDto("delete-book", "/books/" + updateBook.getIsbn(), "DELETE"));
	bookResponse.addLink(new LinkDto("create-review", "/books/" + updateBook.getIsbn(), "POST"));
	if (updateBook.getReviews().size() !=0 ){
		bookResponse.addLink(new LinkDto("view-all-reviews","/books/" + updateBook.getIsbn() + "/reviews", "GET"));
		}
	
	return Response.ok().entity(bookResponse.getLinks()).build();
    }
    
    /*
     * 6.Create Book Review API
     */
    @POST
    @Path("/{isbn}/reviews")
    @Timed(name = "create-review")
    public Response createReview(@Valid Review reviews, @PathParam("isbn") int isbn) {

		Book createReview = bookNum.get(isbn);
		reviews.setId(reviewid);
		createReview.getReviews().add(reviews);
		reviewid++;
		ReviewDto review = new ReviewDto();
		review.addLink(new LinkDto("view-review", "/books/" + createReview.getIsbn() + "/reviews/" + reviews.getId(), "GET"));
	return Response.status(201).entity(review.getLinks()).build();
    }
    
    /*
     * 7.View Book Review API
     */
    
    @GET
    @Path("/{isbn}/reviews/{id}")
    @Timed(name = "view-review")
    public ReviewDto viewReview(@PathParam("isbn") int isbn, @PathParam("id") int id) {
		int i=0;
		Book book = bookNum.get(isbn);

		while (book.getbyReview(i).getId()!=id)
		{
			i++;
		}

		ReviewDto review = new ReviewDto(book.getbyReview(i));
		review.addLink(new LinkDto("view-review", "/books/" + book.getIsbn() + "/reviews/" + book.getbyReview(i).getId(), "GET"));

	return review;
    }
    
    /*
     * 8. View All Reviews API 
     */
    
    @GET
    @Path("/{isbn}/reviews")
    @Timed(name = "view-all-reviews")
    public ReviewsDto viewAllReviews(@PathParam("isbn") int isbn) {

		Book book = bookNum.get(isbn);
		ReviewsDto review = new ReviewsDto(book.getReviews());

	return review;
    }
    
    /*
     * 9. View Book Author API
     */
    
    @GET
    @Path("/{isbn}/authors/{id}")
    @Timed(name = "view-author")
    public Response viewAuthor(@PathParam("isbn") int isbn, @PathParam("id") int id) {
		int i=0;
		Book book = bookNum.get(isbn);
		while (book.getbyAuthors(i).getId()!=id)
		{
			i++;
		}
		AuthorDto authorResponse = new AuthorDto(book.getbyAuthors(i));
		authorResponse.addLink(new LinkDto("view-author", "/books/" + book.getIsbn() + "/authors/" + book.getbyAuthors((int)i).id, "GET"));

	return Response.ok(authorResponse).build();
    }
    
    
    /*
     *  10. View All Authors of the Book API
     */
    @GET
    @Path("/{isbn}/authors")
    @Timed(name = "view-all-authors")
    public AuthorsDto viewAllAuthors(@PathParam("isbn") int isbn) {

		Book book = bookNum.get(isbn);
		AuthorsDto author = new AuthorsDto(book.getAuthors());

	return author;
    }
}
    
    
    
    
    
    




