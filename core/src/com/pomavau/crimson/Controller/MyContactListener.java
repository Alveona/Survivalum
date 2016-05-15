package com.pomavau.crimson.Controller;



import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.utils.Array;
import com.pomavau.crimson.Model.Bot;

import java.util.ArrayList;


public class MyContactListener implements ContactListener {
	World world;
	Array<Body> bodiesToDelete;
	CustomUserData customUserDataA;
	CustomUserData customUserDataB;
	public MyContactListener(World world) {
		super();
		this.world = world;
		bodiesToDelete = new Array<Body>();
		customUserDataA = new CustomUserData();
		customUserDataB = new CustomUserData();
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void beginContact(Contact contact) {

		//String x="", y="";
    	  /* String p ="";
    	  WorldManifold manifold = contact.getWorldManifold();
    	 
    	  if(contact.getFixtureA().getUserData() != null){
    		  x = contact.getFixtureA().getBody().getPosition().x;
    		  y = contact.getFixtureA().getBody().getPosition().y;
    	  }
    	  if(contact.getFixtureB().getUserData() != null){
    		  x = contact.getFixtureB().getBody().getPosition().x;
    		  y = contact.getFixtureB().getBody().getPosition().y;
    	  }*/
    	  /*for(int j = 0; j < manifold.getNumberOfContactPoints(); j++) {
    		   p+="["+Float.toString(manifold.getPoints()[j].x)+";"+Float.toString(manifold.getPoints()[j].y)+"]";
    		   
				
			
			}
    	  Log.e("contact",p);*/
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
    	 /* String p ="";
    	  WorldManifold manifold = contact.getWorldManifold();
    	  for(int j = 0; j < manifold.getNumberOfContactPoints(); j++) {
   		   p+="["+Float.toString(manifold.getPoints()[j].x)+";"+Float.toString(manifold.getPoints()[j].y)+"]";
   		   
				
			
			}
   	  Log.e("contact",p);*/
    	 /*
    	   WorldManifold manifold = contact.getWorldManifold();
    	  for(int j = 0; j < manifold.getNumberOfContactPoints(); j++){
    		  if(contact.getFixtureA().getUserData() != null && contact.getFixtureA().getUserData().equals("p"))
    			  contact.setEnabled(false);
    		  if(contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().equals("p"))
    			  contact.setEnabled(false);
    	  }
    	  */
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		//System.out.println("CONTACT!");

		Body body1 = null;
		Body body2 = null;


			if(contact.getFixtureA() != null)
		customUserDataA = (CustomUserData) contact.getFixtureA().getUserData();



		if (contact.getFixtureA() != null && contact.getFixtureA().getUserData() != null && customUserDataA.getString().equals("bot")) {
			body1 = contact.getFixtureA().getBody();
			//System.out.println("BODY 1");
		}

			if(contact.getFixtureB() != null)
		customUserDataB = (CustomUserData) contact.getFixtureB().getUserData();

		if (contact.getFixtureB() != null && contact.getFixtureB().getUserData() != null && customUserDataB.getString().equals("bullet"))
			body2 = contact.getFixtureB().getBody();

    	 /*
		  if(contact.getFixtureA() != null && contact.getFixtureA().getUserData() != null  && contact.getFixtureA().getUserData().equals("bot")
				 && contact.getFixtureB() != null && contact.getFixtureB().getUserData() != null  && contact.getFixtureB().getUserData().equals("bullet")
				 || contact.getFixtureA() != null && contact.getFixtureA().getUserData() != null  && contact.getFixtureA().getUserData().equals("bullet")
				  && contact.getFixtureB() != null && contact.getFixtureB().getUserData() != null  && contact.getFixtureB().getUserData().equals("bot"))
		  {
			  rem
		  }
    	 */

    	  if(body2 != null && body1 != null){
				//bodiesToDelete.add(body2);
    		 //body2.setActive(false);
    		  //world.destroyBody(body2);
			  			world.step(0, 0, 0);


			  //System.out.println(customUserDataA.getBot()==null);
			  if(customUserDataA.getBot()!=null)
			  {

				  if(customUserDataB.getBullet().getBulletType() == BulletType.ICEBALL)
				  {
					  customUserDataA.getBot().setCurrentState(ObjectState.FREEZED);
					  customUserDataA.getBot().setTimefreezed(0);

				  }
			  }
			  body2.setActive(false);
			  customUserDataB.getBullet().setVisible(false);
			  if(customUserDataA.getBot() != null) {
				  customUserDataA.getBot().setCurrentHP(customUserDataA.getBot().getCurrentHP() - 40);
				  if (customUserDataA.getBot().getCurrentHP() <= 0) {
					  customUserDataA.getBot().setVisible(false);
						body1.setActive(false);
					  	customUserDataA.getBot().setCurrentState(ObjectState.DISABLED);
					  //world.destroyBody(body1);
					  //body1 = null;
				  }
			  }

			  //world.destroyBody(body2);
			  //body2 = null;

			  //removeBodiesFromArray(bodiesToDelete);

			  			//removeBodySafely(body2);

			  //System.out.println(body2);
			//System.out.println("BODY destroyed");
    		  
    	  }


		/**
		 * Safe way to remove body from the world. Remember that you cannot have any
		 * references to this body after calling this
		 *
		 * @param body
		 *            that will be removed from the physic world
		 */
	}
	public void removeBodySafely(Body body) {
		//to prevent some obscure c assertion that happened randomly once in a blue moon
		final Array<JointEdge> list = body.getJointList();
		while (list.size > 0) {
			world.destroyJoint(list.get(0).joint);
		}
		// actual remove
		world.destroyBody(body);
	}

	public void removeBodiesFromArray(Array<Body> array)
	{
		for(int i = 0; i < array.size; i++)
		{
			world.destroyBody(array.get(i));
			array.removeIndex(i);
		}
	}

}

