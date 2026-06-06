package com.csus.csc133;

import java.util.Vector;

import com.codename1.ui.CN;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;

import java.util.Observable;
import java.util.Random; 

//ISSUE: idk why .getsimplename runs a warning

public class GameModel extends Observable {
	
	//create variables
	private double height, width, gametime; 
	//create vectors to store game objects
	private GameObjectCollection s = new GameObjectCollection(); 
	//add student player
	private StudentPlayer studentPlayer = StudentPlayer.getInstance(); 
	//create instances of each facility
	private LectureHall lectureHall1;
	private LectureHall lectureHall2;
	private LectureHall lectureHall3;
	//to track lecturehalls
	private LectureHall currentLec; 
	
	private Restroom restroom;
	private WaterDispenser waterdispenser; 
	private Lecture lecture; 
	
	//variable to store game message
	private String gamemessage = "Game Start"; 
	
	//boolean to track if game is paused or not
	private boolean pause = false;
	
	//boolean to track positionmode 
	private boolean changemode = false; 
	
	public void init(){
		//initialize world size 
		//initialize gametime
		gametime = 0;
		//random variable
		Random rand = new Random(); 
		
		//add students to vector
		//angry
		int angryC = 1 + rand.nextInt(2); // 2 to 4
        for (int i = 0; i < angryC; i++) {
        	//add facilities to vector
            s.add(new StudentAngry());
        }
		//biking
        int bikingC = 1 + rand.nextInt(2); // 2 to 4
        for (int i = 0; i < bikingC; i++) {
        	//add facilities to vector
            s.add(new StudentBiking());
        }
		//car
        int carC = 1 + rand.nextInt(2); // 2 to 4
        for (int i = 0; i < carC; i++) {
        	//add facilities to vector
            s.add(new StudentCar());
        }
		//confused
        int confusedC = 1 + rand.nextInt(2); // 2 to 4
        for (int i = 0; i < confusedC; i++) {
        	//add facilities to vector
            s.add(new StudentConfused());
        }
		//friendly
        int friendlyC = 1 + rand.nextInt(2); // 2 to 4
        for (int i = 0; i < friendlyC; i++) {
        	//add facilities to vector
            s.add(new StudentFriendly());
        }
		//happy
        int happyC = 1 + rand.nextInt(2); // 2 to 4
        for (int i = 0; i < happyC; i++) {
        	//add facilities to vector
            s.add(new StudentHappy());
        }
		//nonstop
        int nonstopC = 1 + rand.nextInt(2); // 2 to 4
        for (int i = 0; i < nonstopC; i++) {
        	//add facilities to vector
            s.add(new StudentNonstop());
        } 
		//running
        int runningC = 1 + rand.nextInt(2); // 2 to 4
        for (int i = 0; i < runningC; i++) {
        	//add facilities to vector
            s.add(new StudentRunning());
        }
		//sleeping 
        int sleepingC = 1 + rand.nextInt(2); // 2 to 4
        for (int i = 0; i < sleepingC; i++) {
        	//add facilities to vector
            s.add(new StudentSleeping());
        }
		//only 1 instance of player
		s.add(getStudentPlayer()); 
		//strategies
		s.add(new StudentWithStrategy(new StrategyHorizontal(), this));
		s.add(new StudentWithStrategy(new StrategyVertical(), this));
		s.add(new StudentWithStrategy(new StrategyRandom(), this));
		
		//initialize facilities
		//lecture + lecture hall
		lecture = new Lecture(50);
		lectureHall1 = new LectureHall("No Class Now", lecture);
		lectureHall2 = new LectureHall("No Class Now", lecture);
		lectureHall3 = new LectureHall("No Class Now", lecture);	
		//setting size for facilities 
		lectureHall1.setSize(90);
		lectureHall2.setSize(90);
		lectureHall3.setSize(90);
		
		//add to vector
		s.add(lectureHall1);
		s.add(lectureHall2);
		s.add(lectureHall3);
		
		//lecture hall vector to store them
		LectureHall[] halls = {lectureHall1, lectureHall2, lectureHall3}; 
		
		//for lecturehalls
		int index = new Random().nextInt(halls.length); 
		
		//pick random lecturehall
		currentLec = halls[index]; 
		currentLec.setName("temp");
		lecture.setTimeleft(650);

		if(index == 0) {
			currentLec.setName("RVR 1017");
		}
		else if(index == 1) {
			currentLec.setName("Brighton 103");
		}
		else if(index == 2) {
			currentLec.setName("Tahoe 112");
		}
		lecture.setTimeleft(50);
		
		for (int i = 0; i < halls.length; i++) {
		    if (i != index) {
		        halls[i].setName("No class now");
		    }
		}
		
		System.out.println("Current hall with lecture is: " + currentLec);
		setMessage("Current hall with lecture is: " + currentLec); 
		
		//restrooms
		restroom = new Restroom(); 
		restroom.setSize(90); 
		//randomly set restroom facilities
		int restroomCount = 2 + rand.nextInt(3); // 2 to 4
        for (int i = 0; i < restroomCount; i++) {
        	//add facilities to vector
            s.add(new Restroom());
        }
        
        //water dispensers
        waterdispenser = new WaterDispenser(); 
        waterdispenser.setSize(40);
        // Add 2-4 Water Dispensers randomly
        int dispenserCount = 2 + rand.nextInt(3);
        for (int i = 0; i < dispenserCount; i++) {
        	//add facilities to vector
            s.add(new WaterDispenser());
        }
        
        
		
//		setChanged();
//		notifyObservers();
	}
	
	//game object collection iterator
	public class GameObjectCollection {
	    private Vector<GameObject> gameObjects;

	    public GameObjectCollection() {
	        gameObjects = new Vector<>();
	    }

	    public void add(GameObject obj) {
	        gameObjects.add(obj);
	    }

	    public void remove(GameObject obj) {
	        gameObjects.remove(obj);
	    }

	    public int size() {
	        return gameObjects.size();
	    }

	    public Iterator getIterator() {
	        return new Iterator();
	    }

	
	    public class Iterator {
	        private int currentIndex = 0;

	        public boolean hasNext() {
	            return currentIndex < gameObjects.size();
	        }

	        public GameObject getNext() {
	            return gameObjects.get(currentIndex++);
	        }
	    }
	}
		
	//methods for commands 4 and f (update methods)
	public void fo(int selection) {
		//randomly pick a non player student and pretend there is a collision
		//with student player
		Random rand = new Random(); 
		GameObjectCollection.Iterator robj = s.getIterator();
		//list to store other players
		Vector<Student> otherStudents = new Vector<>();
		//loop to add to vector
		 while (robj.hasNext()) {
		        GameObject obj = robj.getNext();
		        if (obj instanceof Student && !(obj instanceof StudentPlayer)) {
		            otherStudents.add((Student)obj);
		        }
		    }
		 
		//simulate collision based on selection
		if(selection == 0) { //pick based on selection
			Student select = otherStudents.get(selection); //to handle collide with selected student
			select.handleCollide(getStudentPlayer());
			System.out.println("Student collided with " + StudentAngry.class.getSimpleName()); 
			this.setGamemessage("Student collided with " + StudentAngry.class.getSimpleName());
		}
		if(selection == 1) { //pick based on selection
			Student select = otherStudents.get(selection); //to handle collide with selected student
			select.handleCollide(getStudentPlayer());
			System.out.println("Student collided with " + StudentBiking.class.getSimpleName()); 
			this.setGamemessage("Student collided with " + StudentBiking.class.getSimpleName());
		}
		if(selection == 2) { //pick based on selection
			Student select = otherStudents.get(selection); //to handle collide with selected student
			select.handleCollide(getStudentPlayer());
			System.out.println("Student collided with " + StudentCar.class.getSimpleName()); 
			this.setGamemessage("Student collided with " + StudentCar.class.getSimpleName());
		}
		if(selection == 3) { //pick based on selection
			Student select = otherStudents.get(selection); //to handle collide with selected student
			select.handleCollide(getStudentPlayer());
			System.out.println("Student collided with " + StudentConfused.class.getSimpleName()); 
			this.setGamemessage("Student collided with " + StudentConfused.class.getSimpleName());
		}
		if(selection == 4) { //pick based on selection
			Student select = otherStudents.get(selection); //to handle collide with selected student
			select.handleCollide(getStudentPlayer());
			System.out.println("Student collided with " + StudentFriendly.class.getSimpleName()); 
			this.setGamemessage("Student collided with " + StudentFriendly.class.getSimpleName());
		}
		if(selection == 5) { //pick based on selection
			Student select = otherStudents.get(selection); //to handle collide with selected student
			select.handleCollide(getStudentPlayer());
			System.out.println("Student collided with " + StudentHappy.class.getSimpleName()); 
			this.setGamemessage("Student collided with " + StudentHappy.class.getSimpleName());
		}
		if(selection == 6) { //pick based on selection
			Student select = otherStudents.get(selection); //to handle collide with selected student
			select.handleCollide(getStudentPlayer());
			System.out.println("Student collided with " + StudentNonstop.class.getSimpleName()); 
			this.setGamemessage("Student collided with " + StudentNonstop.class.getSimpleName());
		}
		if(selection == 7) { //pick based on selection
			Student select = otherStudents.get(selection); //to handle collide with selected student
			select.handleCollide(getStudentPlayer());
			System.out.println("Student collided with " + StudentRunning.class.getSimpleName()); 
			this.setGamemessage("Student collided with " + StudentRunning.class.getSimpleName());
		}
		if(selection == 8) { //pick based on selection
			Student select = otherStudents.get(selection); //to handle collide with selected student
			select.handleCollide(getStudentPlayer());
			System.out.println("Student collided with " + StudentSleeping.class.getSimpleName()); 
			this.setGamemessage("Student collided with " + StudentSleeping.class.getSimpleName());
		}
		if(selection == 9) { //pick based on selection
			Student select = otherStudents.get(selection); //to handle collide with selected student
			select.handleCollide(getStudentPlayer());
			System.out.println("Student collided with " + StudentWithStrategy.class.getSimpleName()); 
			this.setGamemessage("Student collided with " + StudentWithStrategy.class.getSimpleName());
		}
		
		
		setChanged();
		notifyObservers();
	}
	
	public void eff(double elapsedtime) {
		//next frame w 4 effects (check assignment requirements for them)
		//gametime increased
		gametime++; 
		
		int prevTime = lecture.getTimeleft();  
		
		//random lecture
		lecture.lessLec();
		
		if(prevTime > 0 && lecture.getTimeleft() == 0) {
			System.out.println("Lecture ended.");
			currentLec.setName("No class now");
			//if student didnt reach hall before lecture ends, absenceTime + 1
			if(getStudentPlayer().getTranslateForm().getTranslateX() != currentLec.getTranslateForm().getTranslateX() && getStudentPlayer().getTranslateForm().getTranslateY() != currentLec.getTranslateForm().getTranslateY() && lecture.getTimeleft() == 0) {
				getStudentPlayer().setAbsenceTime(getStudentPlayer().getAbsenceTime() + 1);
			}
			setMessage("Lecture ended"); 
		}
		
		Random chance = new Random(); 
		//if there is no lecture time left, with a 10% chance
		if(lecture.getTimeleft() <= 0 && chance.nextInt(10) == 0) {
			lecture.setTimeleft(650);
			LectureHall[] halls = {lectureHall1, lectureHall2, lectureHall3};
			int index = new Random().nextInt(halls.length);
			currentLec = halls[index];

			currentLec.setRandomPosition(width, height);
			
			if(index == 0) {
				currentLec.setName("RVR 1017");
			}
			else if(index == 1) {
				currentLec.setName("Brighton 103");
			}
			else if(index == 2) {
				currentLec.setName("Tahoe 112");
			}

			// Set others to "No class now"
			for (LectureHall lh : halls) {
			    if (lh != currentLec) {
			        lh.setName("No class now");
			    }
			}
			
			currentLec.setRandomPosition(width, height);
			System.out.println("Lecture has started.");
			currentLec.setName(halls[index].getName());
			setMessage("Lecture Started"); 
		}
		
		
		GameObjectCollection.Iterator it = s.getIterator();
		//call move() if object can move
		while (it.hasNext()) {
		    GameObject obj = it.getNext();
		    if (obj instanceof IMoveable) {
		        ((IMoveable)obj).move(elapsedtime, getWidth(), getHeight());
		    }
		}
		
		detectCollision(s); 
		
		//check if game has ended (too many absents, too much water, 0 hyd)
		if(getStudentPlayer().getAbsenceTime() > 5 || getStudentPlayer().getWaterIntake() > 200 || getStudentPlayer().getHydration() <= 0) {
			System.out.println("Gameover. Time: " + gametime); 
			setMessage("Game Over. Time: " + gametime); 
			gameover(); 
		}
		//output for confirmation
		System.out.println("Game frame advanced. Time: " + gametime); 
//		setMessage("Game frame advanced. Time: " + gametime); 
		//add these for like every function i think
		setChanged();
		notifyObservers();
	}
	
	//method for handling game over message
	private void gameover() {
		String cause = ""; 
		if (studentPlayer.getAbsenceTime() > 5) {
			cause = " too many absences."; 
		}
		else if (studentPlayer.getWaterIntake() > 200) {
			cause = " too much water."; 
		}
		else if (studentPlayer.getHydration() <= 0) {
			cause = " not enough water."; 
		}
		
		String output = "Game over due to " + cause + " \n Total Time: " + Math.round(getGametime());
		
		Dialog.show("Gameover", output, "Confirm", null); //gameover box
		CN.exitApplication();
	}
	
	//methods for handling collisions with facilities and update methods 
	public void colliderestroom() {
		GameObjectCollection.Iterator it = s.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext(); 
			if(obj instanceof Restroom && obj.collidesWith(studentPlayer)) {
				//uses collide method
	            obj.handleCollide(studentPlayer);
	            setMessage("Player used restroom"); 
	        }
		}
		
	}
	
	public void collidelecturehall() {
		GameObjectCollection.Iterator it = s.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext(); 
			if(obj instanceof LectureHall && obj.collidesWith(studentPlayer)) {
				obj.handleCollide(studentPlayer); //uses handle collide method
				setMessage("Player ran into lecture hall"); 
			}
		}
		
	}
	
	public void collidewaterdispenser() {
		GameObjectCollection.Iterator it = s.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext(); 
			if(obj instanceof WaterDispenser && obj.collidesWith(studentPlayer)) {
	            obj.handleCollide(studentPlayer);
	            setMessage("Player used water dispenser"); 
	        }
		}
		
	}
	
	//method for handling object collisions in A3
	public void detectCollision(GameObjectCollection collection) {
		//to store objects colliding
		GameObjectCollection.Iterator outer = s.getIterator(); //gameobject collection iterator
		
		while(outer.hasNext()) {
			GameObject obj1 = outer.getNext(); 
			GameObjectCollection.Iterator inner = s.getIterator(); 
			
			while(inner.hasNext()) {
				GameObject obj2 = inner.getNext(); 
				
				if (obj1 == obj2) {
					continue;
				}
				
				if(obj1.collidesWith(obj2)) {
					if(!obj1.getCollisions().contains(obj2)) {
						//first collision
						obj1.getCollisions().add(obj2);
						obj2.getCollisions().add(obj1); 
						
						//student and student
						if(obj1 instanceof Student && obj2 instanceof Student) {
							((Student) obj1).handleCollide((Student) obj2);
							setMessage(obj1.getClass().getSimpleName() + " collided with " + obj2.getClass().getSimpleName());
						}
						//student and facility
						else if(obj1 instanceof Student && obj2 instanceof Facility) {
							((Facility) obj2).handleCollide((Student) obj1);
							setMessage(obj1.getClass().getSimpleName() + " collided with " + obj2.getClass().getSimpleName());
						}
						//facility and student
						else if(obj1 instanceof Facility && obj2 instanceof Student) {
							((Facility) obj1).handleCollide((Student) obj2);
							setMessage(obj2.getClass().getSimpleName() + " collided with " + obj1.getClass().getSimpleName());
						}
					}
					else {
						if(obj1.getCollisions().contains(obj2)) {
							obj1.getCollisions().remove(obj2);
							obj2.getCollisions().remove(obj1); 
						}
					}
				}
			}
		}
	}
	
	
	
	//methods for handling student actions
	public void studentstartmove() {
		GameObjectCollection.Iterator it = s.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext(); 
			if(obj instanceof StudentPlayer) {
				studentPlayer.startMove();
				setMessage("Student started moving");
			}
		}
		
	}
	
	public void studentstopmove() {
		GameObjectCollection.Iterator it = s.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext(); 
			if(obj instanceof StudentPlayer) {
				studentPlayer.stopMove();
				setMessage("Student stopped moving");
			}
		}
		 
	}
	
	public void turnright() {
		GameObjectCollection.Iterator it = s.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext(); 
			if(obj instanceof StudentPlayer) {
				studentPlayer.right();
				setMessage("Student turned right");
			}
		}
 
	}
	
	public void turnleft() {
		GameObjectCollection.Iterator it = s.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext(); 
			if(obj instanceof StudentPlayer) {
				studentPlayer.left();
				setMessage("Student turned left");
			}
		}

	}
	
	//methods for changing the game message
	public void setMessage(String msg) {
		gamemessage = msg;
		setChanged(); 
		notifyObservers(); 
	}
	
	public String getMessage() {
		return gamemessage; 
	}
	
	//output info method for easier output handling
	public void outputInfo() {
		System.out.println("Game Time: " + gametime + " ====================");
		//for loop to go through every object in the vector
		GameObjectCollection.Iterator it = s.getIterator();
		while (it.hasNext()) {
			GameObject obj = it.getNext(); 
			System.out.println(obj.getClass().getSimpleName() + obj.toString()); 
		}

	}
	
	//for repositioning objects
	public void repositionObjects() {
	    GameObjectCollection.Iterator it = s.getIterator();
	    while (it.hasNext()) {
	        GameObject obj = it.getNext();
	        obj.setRandomPosition(getWidth(), getHeight());
	    }
	}
	
	//for setting game bounds
	public void setWorldBounds(WorldBound wb) {
		GameObjectCollection.Iterator it = s.getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext();
			if (obj instanceof Student) {
				((Student) obj).setWorldBounds(wb); 
			}
		}
	}
	
	
	//getters and setters

	public StudentPlayer getStudentPlayer() {
		return studentPlayer;
	}

	public void setStudentPlayer(StudentPlayer studentPlayer) {
		this.studentPlayer = studentPlayer;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getGametime() {
		return gametime;
	}

	public void setGametime(double gametime) {
		this.gametime = gametime;
	}

	public LectureHall getLectureHall() {
		return lectureHall1;
	}

	public void setLectureHall(LectureHall lectureHall) {
		this.lectureHall1 = lectureHall;
	}

	public LectureHall getLectureHall1() {
		return lectureHall1;
	}

	public void setLectureHall1(LectureHall lectureHall1) {
		this.lectureHall1 = lectureHall1;
	}

	public LectureHall getLectureHall2() {
		return lectureHall2;
	}

	public void setLectureHall2(LectureHall lectureHall2) {
		this.lectureHall2 = lectureHall2;
	}

	public LectureHall getLectureHall3() {
		return lectureHall3;
	}

	public void setLectureHall3(LectureHall lectureHall3) {
		this.lectureHall3 = lectureHall3;
	}

	public LectureHall getCurrentLec() {
		return currentLec;
	}

	public void setCurrentLec(LectureHall currentLec) {
		this.currentLec = currentLec;
	}

	public Restroom getRestroom() {
		return restroom;
	}

	public void setRestroom(Restroom restroom) {
		this.restroom = restroom;
	}

	public WaterDispenser getWaterdispenser() {
		return waterdispenser;
	}

	public void setWaterdispenser(WaterDispenser waterdispenser) {
		this.waterdispenser = waterdispenser;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}

	public GameObjectCollection getS() {
		return s;
	}

	public void setS(GameObjectCollection s) {
		this.s = s;
	}
	

	public String getGamemessage() {
		return gamemessage;
	}

	public void setGamemessage(String gamemessage) {
		this.gamemessage = gamemessage;
		setChanged();
	    notifyObservers();
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	public boolean isChangePositionMode() {
	    return changemode;
	}

	public void setChangePositionMode(boolean mode) {
	    this.changemode = mode;
	}
	
}
