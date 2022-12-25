package GraphicsObjects;

public class Vector4f {

	public float x=0;
	public float y=0;
	public float z=0;
	public float a=0;

	// default constructor
	public Vector4f() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		a = 0.0f;
	}

	//initializing constructor
	public Vector4f(float x, float y, float z,float a) {
		// assign x, y, z, a parameters to the vector object
		this.x = x;
		this.y = y;
		this.z = z;
		this.a = a;
	}
	
	//implement Vector plus a Vector
	public Vector4f PlusVector(Vector4f Additonal) {
		return new Vector4f(this.x+Additonal.x, this.y+Additonal.y, this.z+Additonal.z,this.a+Additonal.a);
	} 
	
	//implement Vector minus a Vector
	public Vector4f MinusVector(Vector4f Minus) {
		// vector object minus another vector and return the object
		Vector4f vector = new Vector4f();
		vector.x = x - Minus.x;
		vector.y = y - Minus.y;
		vector.z = z - Minus.z;
		vector.a = a - Minus.a;
		return vector;
	}
	
	//implement Vector plus a Point 
	public Point4f PlusPoint(Point4f Additonal) {
		// add point to the vector object and return the produced point
		Point4f point = new Point4f();
		point.x = x + Additonal.x;
		point.y = y + Additonal.y;
		point.z = z + Additonal.z;
		point.a = a + Additonal.a;
		return point;
	} 
	//Do not implement Vector minus a Point as it is undefined 
	
	//Implement a Vector * Scalar 
	public Vector4f byScalar(float scale) {
		// vector object multiplied by a scale and return the object
		Vector4f vector = new Vector4f();
		vector.x = x * scale;
		vector.y = x * scale;
		vector.z = z * scale;
		vector.a = a * scale;
		return vector;
	}
	
	//implement returning the negative of a  Vector  
	public Vector4f NegateVector() {
		// produce the reverse vector of the vector object
		Vector4f negativeVector = new Vector4f();
		negativeVector.x = x * -1;
		negativeVector.y = y * -1;
		negativeVector.z = z * -1;
		negativeVector.a = a * -1;
		return negativeVector;
	}
	
	//implement getting the length of a Vector  
	public float length() {
		// produce the length of vector by using the formula of norm and return the length as a float variable
	    return (float) Math.sqrt(x*x + y*y + z*z+ a*a);
	}
	
	//Just to avoid confusion here is getting the Normal of a Vector
	public Vector4f Normal() {
		float LengthOfTheVector = this.length();
		return this.byScalar(1.0f / LengthOfTheVector);
	} 
	
	//implement getting the dot product of Vector.Vector  

	public float dot(Vector4f v) {
		// produce the dot product of the two vector
		return ( this.x*v.x + this.y*v.y + this.z*v.z+ this.a*v.a);
	}
	
	// Implemented this for you to avoid confusion 
	// as we will not normally be using 4 float vector
	public Vector4f cross(Vector4f v) {
		float u0 = (this.y*v.z - z*v.y);
		float u1 = (z*v.x - x*v.z);
		float u2 = (x*v.y - y*v.x);
		float u3 = 0; //ignoring this for now
		return new Vector4f(u0,u1,u2,u3);
	}
}

/*

										MMMM                                        
										MMMMMM                                      
 										MM MMMM                                    
 										MMI  MMMM                                  
 										MMM    MMMM                                
 										MMM      MMMM                              
  										MM        MMMMM                           
  										MMM         MMMMM                         
  										MMM           OMMMM                       
   										MM             .MMMM                     
MMMMMMMMMMMMMMM                        MMM              .MMMM                   
MM   IMMMMMMMMMMMMMMMMMMMMMMMM         MMM                 MMMM                 
MM                  ~MMMMMMMMMMMMMMMMMMMMM                   MMMM               
MM                                  OMMMMM                     MMMMM            
MM                                                               MMMMM          
MM                                                                 MMMMM        
MM                                                                   ~MMMM      
MM                                                                     =MMMM    
MM                                                                        MMMM  
MM                                                                       MMMMMM 
MM                                                                     MMMMMMMM 
MM                                                                  :MMMMMMMM   
MM                                                                MMMMMMMMM     
MM                                                              MMMMMMMMM       
MM                             ,MMMMMMMMMM                    MMMMMMMMM         
MM              IMMMMMMMMMMMMMMMMMMMMMMMMM                  MMMMMMMM            
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM               ZMMMMMMMM              
MMMMMMMMMMMMMMMMMMMMMMMMMMMMM          MM$             MMMMMMMMM                
MMMMMMMMMMMMMM                       MMM            MMMMMMMMM                  
  									MMM          MMMMMMMM                     
  									MM~       IMMMMMMMM                       
  									MM      DMMMMMMMM                         
 								MMM    MMMMMMMMM                           
 								MMD  MMMMMMMM                              
								MMM MMMMMMMM                                
								MMMMMMMMMM                                  
								MMMMMMMM                                    
  								MMMM                                      
  								MM                                        
                             GlassGiant.com */