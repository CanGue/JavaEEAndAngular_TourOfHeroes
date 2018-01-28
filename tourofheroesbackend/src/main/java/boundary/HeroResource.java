package boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import entity.Hero;

@Path("heroes")
@Stateless
public class HeroResource {
	
	
	@PersistenceContext(name = "hero")
	private EntityManager entityManager;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveHero(Hero hero) {
		
		entityManager.persist(hero);
		return Response.status(Status.CREATED).entity(hero).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateHero(Hero hero) {
		
		Hero updateHero = entityManager.find(Hero.class, hero.getId());
	    updateHero = hero;
	    entityManager.merge(updateHero);
	
		return Response.status(201).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllHeroes() {

		TypedQuery<Hero> findAllHeroes = entityManager.createNamedQuery(Hero.FINDALLHEROES, Hero.class);
		List<Hero> heroes = findAllHeroes.getResultList();
		return Response.ok().entity(heroes).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getHeroById(@PathParam("id") String id) {

		Hero heroById = entityManager.find(Hero.class, Integer.valueOf(id));
		return Response.ok().entity(heroById).build();
	}

	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public void deleteHero(@PathParam("id") String id) {

		Hero heroToDelete = entityManager.find(Hero.class, Integer.valueOf(id));
		entityManager.remove(heroToDelete);
	}
	
	
}
