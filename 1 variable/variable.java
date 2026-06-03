class variable {

    // Instance variable (each object has its own copy)
    int a = 10;

    // Static variable (shared by all objects)
    static int b = 20;

    // Instance method to print variables
    void emni() {
        System.out.println(a); // Access instance variable
        System.out.println(b); // Access static variable
    }

    public static void main(String[] noman ) {

        // Create first object
        variable obj1 = new variable();
		obj1.emni();

        // Initial values
        System.out.println(obj1.a); // 10
        System.out.println(obj1.b); // 20

        // Update values using obj1
        obj1.a = 100;   // Changes only obj1's instance variable
        obj1.b = 200;   // Changes the shared static variable

        // Updated values
        System.out.println(obj1.a); // 100
        System.out.println(obj1.b); // 200

        /*
         * Create second object.
         * Instance variable 'a' gets a fresh copy with default assigned value (10).
         * Static variable 'b' remains 200 because it is shared by all objects.
         */
        variable obj2 = new variable();

        // Values of obj2
        System.out.println(obj2.a); // 10
        System.out.println(obj2.b); // 200
	
    }
}